package com.wallet.turnikipushups.ui.main

import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.wallet.turnikipushups.App
import com.wallet.turnikipushups.databinding.FragmentMainBinding
import com.wallet.turnikipushups.di.ViewModelFactory
import com.wallet.turnikipushups.ui.BaseFragment


class MainFragment : BaseFragment<FragmentMainBinding>() {
    override fun bind(inflater: LayoutInflater): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater)
    }

    private val viewModel:MainViewModel by viewModels {
        ViewModelFactory{
            (requireContext().applicationContext as App).appComponent.mainViewModel()
        }
    }


    override fun initView() {
        viewModel.statsPushUps.observe(viewLifecycleOwner) {
            Log.d("mylog",it.toString())
        }
        viewModel.getAllStats()
    }

}