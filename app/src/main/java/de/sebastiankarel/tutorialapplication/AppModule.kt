package de.sebastiankarel.tutorialapplication

import com.google.gson.GsonBuilder
import de.sebastiankarel.tutorialapplication.model.RemoteUserService
import de.sebastiankarel.tutorialapplication.model.Repository
import de.sebastiankarel.tutorialapplication.viewmodel.ListViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { getRemoteUserService() }
    single { Repository(get()) }
    viewModel { ListViewModel(get()) }
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