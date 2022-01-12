package de.sebastiankarel.tutorialapplication

import de.sebastiankarel.tutorialapplication.model.Repository
import de.sebastiankarel.tutorialapplication.viewmodel.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { Repository() }
    viewModel { ListViewModel(get()) }
}