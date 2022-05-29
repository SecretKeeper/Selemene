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
import net.teamof.whisper.Whisper
import net.teamof.whisper.api.UserTokenInterceptor
import net.teamof.whisper.data.ConversationRepository
import net.teamof.whisper.repositories.MessageRepository
import net.teamof.whisper.sharedprefrences.SharedPreferencesManagerImpl
import net.teamof.whisper.sockets.Socket
import net.teamof.whisper.sockets.SocketBroadcastListener
import net.teamof.whisper.utils.DateMoshiAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

	@Singleton
	@Provides
	fun provideGetUserToken(sharedPreferencesManagerImpl: SharedPreferencesManagerImpl): String =
		sharedPreferencesManagerImpl.getString("accessToken", "")

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
	fun provideHttpClient(sharedPreferencesManagerImpl: SharedPreferencesManagerImpl): OkHttpClient {
		val logging = HttpLoggingInterceptor()
		logging.setLevel(HttpLoggingInterceptor.Level.BODY)

		return OkHttpClient.Builder()
			.addInterceptor(logging)
			.addInterceptor(UserTokenInterceptor(sharedPreferencesManagerImpl))
			.connectTimeout(120, TimeUnit.SECONDS)
			.readTimeout(120, TimeUnit.SECONDS)
			.writeTimeout(120, TimeUnit.SECONDS)
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
	fun provideWhisperSocket(getUserToken: String): Socket {
		return Socket.Builder.with("ws://10.0.2.2:3335/ws/")
			.addHeader("Authorization", "Bearer $getUserToken")
			.build().connect()
	}

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