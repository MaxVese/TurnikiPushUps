package com.wallet.turnikipushups.di

import android.text.Editable
import androidx.fragment.app.Fragment
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelFactory<T: ViewModel>(private val create: () -> T) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return create.invoke() as T
    }
}