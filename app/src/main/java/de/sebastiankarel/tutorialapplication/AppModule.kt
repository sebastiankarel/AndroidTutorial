package de.sebastiankarel.tutorialapplication

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import de.sebastiankarel.tutorialapplication.model.*
import de.sebastiankarel.tutorialapplication.model.persistence.*
import de.sebastiankarel.tutorialapplication.util.ImageLoader
import de.sebastiankarel.tutorialapplication.util.ImageLoaderImpl
import de.sebastiankarel.tutorialapplication.viewmodel.*
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { getDatabase(androidContext()) }
    single { getUserDao(get()) }
    single { getPhotoDao(get()) }
    single { AppPreferencesImpl(androidContext()) as AppPreferences }
    single { AuthInterceptor(get()) }
    single { getRetrofit(get()) }
    single { getRemoteUserService(get()) }
    single { getRemoteAuthService(get()) }
    single { RepositoryImpl(get(), get(), get()) as Repository }
    single { ImageLoaderImpl(get()) as ImageLoader }
    single { AuthRepositoryImpl(get(), get()) as AuthRepository }
    viewModel { ListViewModel(get(), get(), get()) }
    viewModel { CreateUserViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { MainViewModel(get()) }
}

fun getRetrofit(authInterceptor: AuthInterceptor): Retrofit {
    val client = OkHttpClient()
        .newBuilder()
        .addInterceptor(authInterceptor)
        .build()
    val gson = GsonBuilder().setLenient().create()
    return Retrofit.Builder()
        .baseUrl("http://restapi.adequateshop.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}

fun getRemoteUserService(retrofit: Retrofit): RemoteUserService {
    return retrofit.create(RemoteUserService::class.java)
}

fun getRemoteAuthService(retrofit: Retrofit): RemoteAuthService {
    return retrofit.create(RemoteAuthService::class.java)
}

fun getDatabase(appContext: Context): AppDatabase {
    return Room.databaseBuilder(
        appContext,
        AppDatabase::class.java,
        "app_database"
    ).build()
}

fun getUserDao(db: AppDatabase): UserDao = db.userDao()

fun getPhotoDao(db: AppDatabase): PhotoDao = db.getPhotoDao()