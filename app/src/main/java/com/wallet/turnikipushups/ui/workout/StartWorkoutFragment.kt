package com.wallet.turnikipushups.ui.workout

import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.wallet.turnikipushups.R
import com.wallet.turnikipushups.databinding.FragmentStartWorkoutBinding
import com.wallet.turnikipushups.di.ViewModelFactory
import com.wallet.turnikipushups.ui.BaseFragment

class StartWorkoutFragment : BaseFragment<FragmentStartWorkoutBinding>() {
    override fun bind(inflater: LayoutInflater): FragmentStartWorkoutBinding {
        return FragmentStartWorkoutBinding.inflate(inflater)
    }

    private val viewModel: StartWorkoutViewModel by viewModels {
        ViewModelFactory{
            getAppComponent(requireContext()).startWorkoutViewModel()
        }
    }

    override fun initView() {
        if(viewModel.getLvl() != 0){
            findNavController().navigate(R.id.action_startWorkoutFragment_to_lvlStartFragment)
        }
        withBinding {
            homeBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            dismissBtn.setOnClickListener {
                findNavController().navigate(R.id.action_startWorkoutFragment_to_freestyleFragment,
                    bundleOf("is_test" to true))
            }
        }
    }

}