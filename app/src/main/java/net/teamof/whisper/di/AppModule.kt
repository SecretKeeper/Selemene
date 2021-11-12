package net.teamof.whisper.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.teamof.whisper.Whisper
import net.teamof.whisper.api.AuthAPI
import net.teamof.whisper.api.SearchAPI
import net.teamof.whisper.api.UsersAPI
import net.teamof.whisper.repositories.MessageRepository
import net.teamof.whisper.utils.DateMoshiAdapter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private val baseWebSocketAddress = "ws://10.0.2.2:3335/ws"

    private val baseGateWayAddress = "http://10.0.2.2:3334"

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

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(baseGateWayAddress)
        .client(okHttpClient)
        .build()

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
    fun provideMessageRepository() = MessageRepository()
}