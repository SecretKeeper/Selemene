package net.teamof.whisper.di

import android.app.Application
import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.teamof.whisper.ObjectBox
import net.teamof.whisper.Whisper
import net.teamof.whisper.api.*
import net.teamof.whisper.models.OBKeyValue
import net.teamof.whisper.models.OBKeyValue_
import net.teamof.whisper.repositories.ConversationRepository
import net.teamof.whisper.repositories.KeyValueRepository
import net.teamof.whisper.repositories.MessageRepository
import net.teamof.whisper.sockets.Socket
import net.teamof.whisper.sockets.SocketBroadcastListener
import net.teamof.whisper.utils.DateMoshiAdapter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideGetUserID(): String {
        val obKeyValueBox = ObjectBox.store.boxFor(OBKeyValue::class.java)
        return obKeyValueBox.query().run {
            equal(OBKeyValue_.key, "user_id")
            build()
        }.use { it.findFirst() }?.value ?: "0"
    }

    private val baseGateWayAddress = "http://10.0.2.2:3333"

    @Provides
    fun providesApplication(@ApplicationContext context: Context): Whisper {
        return context as Whisper
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(Date::class.java, DateMoshiAdapter())
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun provideHttpClient(keyValueRepository: KeyValueRepository): OkHttpClient {

        return OkHttpClient.Builder().addInterceptor(UserTokenInterceptor(keyValueRepository))
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(baseGateWayAddress)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideWhisperSocket(getUserID: String): Socket {
        return Socket.Builder.with("ws://10.0.2.2:3335/ws")
            .addHeader("user-id", getUserID)
            .build().connect()
    }

    @Singleton
    @Provides
    fun provideAuthAPI(retrofit: Retrofit): AuthAPI = retrofit.create(AuthAPI::class.java)

    @Singleton
    @Provides
    fun provideSearchAPI(retrofit: Retrofit): SearchAPI = retrofit.create(SearchAPI::class.java)

    @Singleton
    @Provides
    fun provideUsersAPI(retrofit: Retrofit): UsersAPI = retrofit.create(UsersAPI::class.java)

    @Singleton
    @Provides
    fun provideAccountAPI(retrofit: Retrofit): AccountAPI = retrofit.create(AccountAPI::class.java)

    @Singleton
    @Provides
    fun provideMessageRepository() = MessageRepository()

    @Singleton
    @Provides
    fun provideKeyValueRepository() = KeyValueRepository()

    @Singleton
    @Provides
    fun provideConversationRepository(usersAPI: UsersAPI) = ConversationRepository(usersAPI)

    @Singleton
    @Provides
    fun provideSocketBroadcastListener(
        application: Application,
        whisperSocket: Socket,
        moshi: Moshi,
        messageRepository: MessageRepository,
        conversationRepository: ConversationRepository,
        keyValueRepository: KeyValueRepository
    ) = SocketBroadcastListener(
        application,
        whisperSocket,
        moshi,
        messageRepository,
        conversationRepository,
        keyValueRepository
    )
}