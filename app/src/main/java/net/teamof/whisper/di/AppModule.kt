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
import io.objectbox.kotlin.equal
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
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideGetUserToken(): String {
        val obKeyValueBox = ObjectBox.store.boxFor(OBKeyValue::class.java)
        val query = obKeyValueBox.query(OBKeyValue_.key equal "accessToken").build()
        val result = query.findFirst()

        query.close()
        return result?.value ?: ""
    }

    private val baseGatewayAddress = "http://10.0.2.2:3333"

    private val baseWhisperAddress = "http://10.0.2.2:3335"

    @Provides
    fun providesApplication(@ApplicationContext context: Context): Whisper {
        return context as Whisper
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(Date::class.java, DateMoshiAdapter().nullSafe())
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun provideHttpClient(keyValueRepository: KeyValueRepository): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(UserTokenInterceptor(keyValueRepository))
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(baseWhisperAddress)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    @Named("Gateway")
    fun provideGatewayRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(baseGatewayAddress)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideWhisperSocket(getUserToken: String): Socket {
        return Socket.Builder.with("ws://10.0.2.2:3335/ws/")
            .addHeader("Authorization", "Bearer $getUserToken")
            .build().connect()
    }

    @Singleton
    @Provides
    fun provideAuthAPI(@Named("Gateway") retrofit: Retrofit): AuthAPI =
        retrofit.create(AuthAPI::class.java)

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
    fun provideProfileAPI(retrofit: Retrofit): ProfileAPI = retrofit.create(ProfileAPI::class.java)

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
        moshi: Moshi,
        messageRepository: MessageRepository,
        conversationRepository: ConversationRepository
    ) = SocketBroadcastListener(
        application,
        moshi,
        messageRepository,
        conversationRepository
    )
}