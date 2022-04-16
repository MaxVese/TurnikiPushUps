package com.wallet.turnikipushups.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import com.wallet.turnikipushups.App
import com.wallet.turnikipushups.di.component.AppComponent

abstract class BaseFragment<T : ViewBinding> : Fragment() {
    var binding: T? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bind(inflater)
        return binding!!.root
    }

    /**
     * TODO пахнет ликами, делай bind и unbind во фрагментах, чтобы не хранить вьюъи из других фрагментов
     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    abstract fun bind(inflater: LayoutInflater): T

    abstract fun initView()

    open fun findNavController(): NavController {
        return Navigation.findNavController(requireView())
    }

    fun getAppComponent(context: Context):AppComponent{
        return (context.applicationContext as App).appComponent
    }

    inline fun withBinding(block: T.() -> Unit) {
        block(binding!!)
    }
}