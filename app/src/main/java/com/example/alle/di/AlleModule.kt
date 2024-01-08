package com.example.alle.di

import androidx.activity.result.ActivityResultRegistry
import com.example.alle.helper.LoadingManager
import com.example.alle.helper.PermissionsHandler
import com.example.alle.helper.PhotoRetrievalHelper
import com.example.alle.ui.dialogs.CollectionCreateBottomSheet
import com.example.alle.ui.dialogs.LoadingDialog
import com.example.alle.ui.viewModel.CollectionBottomSheetViewModel
import com.example.alle.ui.viewModel.MainViewModel

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val alleModule = module {


    single { LoadingManager() }
    factory { PhotoRetrievalHelper(get()) }
    factory { (registry: ActivityResultRegistry) -> PermissionsHandler(registry,androidContext()) }
    viewModel{ MainViewModel(get(),get()) }
    viewModel{ CollectionBottomSheetViewModel()}
    factory { LoadingDialog() }
}