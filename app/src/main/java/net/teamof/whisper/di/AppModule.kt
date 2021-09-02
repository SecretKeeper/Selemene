package net.teamof.whisper.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.teamof.whisper.Whisper
import net.teamof.whisper.sockets.FlowStreamAdapter
import net.teamof.whisper.utils.DateMoshiAdapter
import net.teamof.whisper.utils.ScarletMessagingService
import okhttp3.OkHttpClient
import java.util.*

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun providesApplication(@ApplicationContext context: Context): Whisper {
        return context as Whisper
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(Date::class.java, DateMoshiAdapter())
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun provideHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    fun provideScarlet(
        application: Whisper,
        client: OkHttpClient,
        moshi: Moshi
    ): Scarlet {
        return Scarlet.Builder()
            .webSocketFactory(client.newWebSocketFactory("ws://10.0.2.2:3334/ws"))
            .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi))
            .addStreamAdapterFactory(FlowStreamAdapter.Factory())
//            .lifecycle(AndroidLifecycle.ofApplicationForeground(application)) // when add this it does not work!
            .build()
    }

    @Provides
    fun provideScarletMessagingService(scarlet: Scarlet): ScarletMessagingService {
        return scarlet.create()
    }
}