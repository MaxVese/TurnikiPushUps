package com.wallet.turnikipushups.ui.main

import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.wallet.turnikipushups.App
import com.wallet.turnikipushups.R
import com.wallet.turnikipushups.databinding.FragmentMainBinding
import com.wallet.turnikipushups.di.ViewModelFactory
import com.wallet.turnikipushups.ui.BaseFragment


class MainFragment : BaseFragment<FragmentMainBinding>() {
    override fun bind(inflater: LayoutInflater): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater)
    }

    private val viewModel:MainViewModel by viewModels {
        ViewModelFactory{
            getAppComponent(requireContext()).mainViewModel()
        }
    }


    override fun initView() {
        viewModel.statsPushUps.observe(viewLifecycleOwner) {
            Log.d("mylog",it.toString())
        }
        viewModel.getAllStats()
        withBinding {
            totalValue.text = viewModel.getSum().toString()
            bestValue.text = viewModel.getBestCount().toString()
            averageValue.text = viewModel.getAverageCount().toString()
            freestyle.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_freestyleFragment)
            }
            notificationButton.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_notificationFragment)
            }
            settingsButton.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_settingsFragment)
            }
            statistics.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_statisticsFragment)
            }
            trainBtn.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_startWorkoutFragment)
            }
        }
    }

}