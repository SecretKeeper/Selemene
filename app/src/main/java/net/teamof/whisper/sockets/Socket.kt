package net.teamof.whisper.sockets

import android.os.Handler
import android.os.Looper
import net.teamof.whisper.repositories.KeyValueRepository
import okhttp3.*
import okhttp3.internal.ws.RealWebSocket
import okio.ByteString
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * Websocket class based on OkHttp3 with {event->data} message format to make your life easier.
 *
 * @author Ali Yusuf
 * @since 3/13/17
 */
class Socket private constructor(request: Request) {
    /**
     * Main socket states
     */
    enum class State {
        CLOSED, CLOSING, CONNECT_ERROR, RECONNECT_ATTEMPT, RECONNECTING, OPENING, OPEN
    }

    class Builder private constructor(private val request: Request.Builder) {
        fun setPingInterval(interval: Long, unit: TimeUnit): Builder {
            httpClient!!.pingInterval(interval, unit)
            return this
        }

        fun addHeader(name: String, value: String): Builder {
            request.addHeader(name, value)
            return this
        }

        fun build(): Socket {
            return Socket(request.build())
        }

        companion object {
            fun with(url: String): Builder {
                // Silently replace web socket URLs with HTTP URLs.
                require(
                    !(!url.regionMatches(
                        0,
                        "ws:",
                        0,
                        3,
                        ignoreCase = true
                    ) && !url.regionMatches(0, "wss:", 0, 4, ignoreCase = true))
                ) { "web socket url must start with ws or wss, passed url is $url" }
                return Builder(Request.Builder().url(url))
            }
        }
    }

    /**
     * Start socket connection if i's not already started
     */
    fun connect(): Socket {
        checkNotNull(httpClient) { "Make sure to use Socket.Builder before using Socket#connect." }
        if (realWebSocket == null) {
            realWebSocket =
                request?.let {
                    httpClient.build().newWebSocket(it, webSocketListener)
                } as RealWebSocket
            changeState(State.OPENING)
        } else if (Companion.state == State.CLOSED) {
            realWebSocket!!.connect(httpClient.build())
            changeState(State.OPENING)
        }
        return this
    }

    /**
     * Set listener which fired every time message received with contained data.
     *
     * @param listener message on arrive listener
     */
    fun onEvent(event: String, listener: OnEventListener): Socket {
        eventListener[event] = listener
        return this
    }

    /**
     * Set listener which fired every time message received with contained data.
     *
     * @param listener message on arrive listener
     */
    fun onEventResponse(event: String, listener: OnEventResponseListener): Socket {
        eventResponseListener[event] = listener
        return this
    }

    /**
     * Send message in {event->data} format
     *
     * @param event event name that you want sent message to
     * @param data  message data in JSON format
     * @return true if the message send/on socket send quest; false otherwise
     */
    fun send(event: String, data: String): Boolean {
        try {
            val text = JSONObject()
            text.put("event", event)
            text.put("data", JSONObject(data))
            Timber.d("Try to send data $text")
            return realWebSocket!!.send(text.toString())
        } catch (e: JSONException) {
            Timber.d("Try to send data with wrong JSON format, data: $data")
        }
        return false
    }

    /**
     * Set state listener which fired every time [Socket.state] changed.
     *
     * @param listener state change listener
     */
    fun setOnChangeStateListener(listener: OnStateChangeListener): Socket {
        onChangeStateListener = listener
        return this
    }

    /**
     * Message listener will be called in any message received even if it's not
     * in a {event -> data} format.
     *
     * @param listener message listener
     */
    fun setMessageListener(listener: OnMessageListener): Socket {
        messageListener = listener
        return this
    }

    fun removeEventListener(event: String) {
        eventListener.remove(event)
        onOpenMessageQueue.remove(event)
    }

    /**
     * Clear all socket listeners in one line
     */
    fun clearListeners() {
        eventListener.clear()
        messageListener = null
        onChangeStateListener = null
    }

    /**
     * Send normal close request to the host
     */
    fun close() {
        if (realWebSocket != null) {
            realWebSocket!!.close(1000, CLOSE_REASON)
        }
    }

    /**
     * Send close request to the host
     */
    fun close(code: Int, reason: String) {
        if (realWebSocket != null) {
            realWebSocket!!.close(code, reason)
        }
    }

    /**
     * Terminate the socket connection permanently
     */
    fun terminate() {
        skipOnFailure = true // skip onFailure callback
        if (realWebSocket != null) {
            realWebSocket!!.cancel() // close connection
            realWebSocket = null // clear socket object
        }
    }

    /**
     * Add message in a queue if the socket not open and send them
     * if the socket opened
     *
     * @param event event name that you want sent message to
     * @param data  message data in JSON format
     */
    fun sendOnOpen(event: String, data: String) {
        if (Companion.state != State.OPEN) onOpenMessageQueue[event] =
            data else send(event, data)
    }

    /**
     * Retrieve current socket connection state [State]
     */
    val state: State
        get() = Companion.state

    /**
     * Change current state and call listener method with new state
     * [OnStateChangeListener.onChange]
     *
     * @param newState new state
     */
    private fun changeState(newState: State) {
        Companion.state = newState
        if (onChangeStateListener != null) {
            onChangeStateListener!!.onChange(this@Socket, Companion.state)
        }
    }

    /**
     * Try to reconnect to the websocket after delay time using *Exponential backoff* method.
     *
     * @see [](https://en.wikipedia.org/wiki/Exponential_backoff)
     */
    private fun reconnect() {
        if (Companion.state != State.CONNECT_ERROR) // connection not closed !!
            return
        changeState(State.RECONNECT_ATTEMPT)
        if (realWebSocket != null) {
            // Cancel websocket connection
            realWebSocket!!.cancel()
            // Clear websocket object
            realWebSocket = null
        }
        if (eventListener[EVENT_RECONNECT_ATTEMPT] != null) {
            eventListener[EVENT_RECONNECT_ATTEMPT]!!
                .onMessage(this@Socket, EVENT_RECONNECT_ATTEMPT)
        }

        // Calculate delay time
        val collision =
            if (reconnectionAttempts > MAX_COLLISION) MAX_COLLISION else reconnectionAttempts
        val delayTime = Math.round((Math.pow(2.0, collision.toDouble()) - 1) / 2) * 1000

        // Remove any pending posts of callbacks
        delayedReconnection.removeCallbacksAndMessages(null)
        // Start new post delay
        delayedReconnection.postDelayed({
            changeState(State.RECONNECTING)
            reconnectionAttempts++ // Increment connections attempts
            connect() // Establish new connection
        }, delayTime)
    }

    private val webSocketListener: WebSocketListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            Timber.d("Socket has been opened successfully.")
            // reset connections attempts counter
            reconnectionAttempts = 0

            // fire open event listener
            if (eventListener[EVENT_OPEN] != null) {
                eventListener[EVENT_OPEN]!!
                    .onMessage(this@Socket, EVENT_OPEN)
            }

            // Send data in queue
            for (event in onOpenMessageQueue.keys) {
                send(
                    event,
                    onOpenMessageQueue[event]!!
                )
            }
            // clear queue
            onOpenMessageQueue.clear()
            changeState(State.OPEN)
        }

        /**
         * Accept only Json data with format:
         * ** {"event":"event name","data":{some data ...}} **
         */
        override fun onMessage(webSocket: WebSocket, text: String) {
            // print received message in log
            Timber.e("New Message received $text")

            // call message listener
            if (messageListener != null) messageListener!!.onMessage(this@Socket, text)
            try {
                // Parse message text
                val response = JSONObject(text)
                val event = response.getString("event")
                val data = response.getJSONObject("data")

                // call event listener with received data
                if (eventResponseListener[event] != null) {
                    eventResponseListener[event]!!
                        .onMessage(this@Socket, event, data)
                }
                // call event listener
                if (eventListener[event] != null) {
                    eventListener[event]!!
                        .onMessage(this@Socket, event)
                }
            } catch (e: JSONException) {
                // Message text not in JSON format or don't have {event}|{data} object
                Timber.d("Unknown message format.")
            }
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            // TODO: some action
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            Timber.d("Close request from server with reason $reason")
            changeState(State.CLOSING)
            webSocket.close(1000, reason)
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            Timber.d("Socket connection closed with reason $reason")
            changeState(State.CLOSED)
            if (eventListener[EVENT_CLOSED] != null) {
                eventListener[EVENT_CLOSED]!!
                    .onMessage(this@Socket, EVENT_CLOSED)
            }
        }

        /**
         * This method call if:
         * - Fail to verify websocket GET request  => Throwable [ProtocolException]
         * - Can't establish websocket connection after upgrade GET request => response null, Throwable [Exception]
         * - First GET request had been failed => response null, Throwable [java.io.IOException]
         * - Fail to send Ping => response null, Throwable [java.io.IOException]
         * - Fail to send data frame => response null, Throwable [java.io.IOException]
         * - Fail to read data frame => response null, Throwable [java.io.IOException]
         */
        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            if (!skipOnFailure) {
                skipOnFailure = false // reset flag
                Timber.d("Socket connection fail, try to reconnect $reconnectionAttempts")
                changeState(State.CONNECT_ERROR)
                reconnect()
            }
        }
    }

    abstract class OnMessageListener {
        abstract fun onMessage(data: String?)

        /**
         * Method called from socket to execute listener implemented in
         * [.onMessage] on main thread
         *
         * @param socket Socket that receive the message
         * @param data   Data string received
         */
        fun onMessage(socket: Socket, data: String) {
            Handler(Looper.getMainLooper()).post { onMessage(data) }
        }
    }

    abstract class OnEventListener {
        abstract fun onMessage(event: String?)
        fun onMessage(socket: Socket, event: String) {
            Handler(Looper.getMainLooper()).post { onMessage(event) }
        }
    }

    abstract class OnEventResponseListener : OnEventListener() {
        /**
         * Method need to override in listener usage
         */
        abstract fun onMessage(event: String?, data: String?)

        /**
         * Just override the inherited method
         */
        override fun onMessage(event: String?) {}

        /**
         * Method called from socket to execute listener implemented in
         * [.onMessage] on main thread
         *
         * @param socket Socket that receive the message
         * @param event  Message received event
         * @param data   Data received in the message
         */
        fun onMessage(socket: Socket, event: String, data: JSONObject) {
            Handler(Looper.getMainLooper()).post {
                onMessage(event, data.toString())
                onMessage(event)
            }
        }
    }

    abstract class OnStateChangeListener {
        /**
         * Method need to override in listener usage
         */
        abstract fun onChange(status: State?)

        /**
         * Method called from socket to execute listener implemented in
         * [.onChange] on main thread
         *
         * @param socket Socket that receive the message
         * @param status new status
         */
        fun onChange(socket: Socket, status: State) {
            Handler(Looper.getMainLooper()).post { onChange(status) }
        }
    }

    companion object {
        private val TAG = Socket::class.java.simpleName
        private const val CLOSE_REASON = "End of session"
        private const val MAX_COLLISION = 7
        const val EVENT_OPEN = "open"
        const val EVENT_RECONNECT_ATTEMPT = "reconnecting"
        const val EVENT_CLOSED = "closed"
        private val httpClient: OkHttpClient.Builder? = OkHttpClient.Builder()
            .addInterceptor(SocketUserTokenInterceptor(KeyValueRepository()))

        /**
         * Websocket state
         */
        private lateinit var state: State

        /**
         * Websocket main request
         */
        private var request: Request? = null

        /**
         * Websocket connection
         */
        private var realWebSocket: RealWebSocket? = null

        /**
         * Reconnection post delayed handler
         */
        private lateinit var delayedReconnection: Handler

        /**
         * Websocket events listeners
         */
        private lateinit var eventListener: MutableMap<String, OnEventListener?>

        /**
         * Websocket events new message listeners
         */
        private lateinit var eventResponseListener: MutableMap<String, OnEventResponseListener?>

        /**
         * Message list tobe send onEvent open [State.OPEN] connection state
         */
        private val onOpenMessageQueue: MutableMap<String, String> = HashMap()

        /**
         * Websocket state change listener
         */
        private var onChangeStateListener: OnStateChangeListener? = null

        /**
         * Websocket new message listener
         */
        private var messageListener: OnMessageListener? = null

        /**
         * Number of reconnection attempts
         */
        private var reconnectionAttempts = 0
        private var skipOnFailure: Boolean = false
    }

    init {
        Companion.request = request
        Companion.state = State.CLOSED
        eventListener = HashMap()
        eventResponseListener = HashMap()
        delayedReconnection = Handler(Looper.getMainLooper())
        skipOnFailure = false
    }
}