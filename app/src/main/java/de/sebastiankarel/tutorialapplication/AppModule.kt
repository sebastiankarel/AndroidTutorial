package de.sebastiankarel.tutorialapplication

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import de.sebastiankarel.tutorialapplication.model.RemoteUserService
import de.sebastiankarel.tutorialapplication.model.Repository
import de.sebastiankarel.tutorialapplication.model.RepositoryImpl
import de.sebastiankarel.tutorialapplication.model.persistence.AppDatabase
import de.sebastiankarel.tutorialapplication.model.persistence.PhotoDao
import de.sebastiankarel.tutorialapplication.model.persistence.UserDao
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
    single { getRemoteUserService() }
    single { RepositoryImpl(get(), get(), get()) as Repository }
    single { ImageLoaderImpl(get()) as ImageLoader }
    viewModel { ListViewModel(get()) }
    viewModel { CreateUserViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
}

fun getRemoteUserService(): RemoteUserService {
    val client = OkHttpClient().newBuilder().build()
    val gson = GsonBuilder().setLenient().create()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://randomuser.me/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    return retrofit.create(RemoteUserService::class.java)
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