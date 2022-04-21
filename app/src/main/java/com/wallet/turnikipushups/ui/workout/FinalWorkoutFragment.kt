package com.wallet.turnikipushups.ui.workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.wallet.turnikipushups.R
import com.wallet.turnikipushups.databinding.FragmentFinallWorkoutBinding
import com.wallet.turnikipushups.db.converters.LevelOfTrainingConverter
import com.wallet.turnikipushups.di.ViewModelFactory
import com.wallet.turnikipushups.models.LevelOfTraining
import com.wallet.turnikipushups.ui.BaseFragment


class FinalWorkoutFragment : BaseFragment<FragmentFinallWorkoutBinding>() {
    override fun bind(inflater: LayoutInflater): FragmentFinallWorkoutBinding {
        return FragmentFinallWorkoutBinding.inflate(inflater)
    }

    private val viewModel: FinallWorkoutViewModel by viewModels {
        ViewModelFactory{
            getAppComponent(requireContext()).finallWorkoutViewModel()
        }
    }

    override fun initView() {
        val lvlOfTrain =
            LevelOfTrainingConverter().toLevelOfTraining(
                arguments?.getString("lvlOfTrain")?:
                LevelOfTrainingConverter().fromLevelOfTraining(LevelOfTraining.BEGINNER))
        viewModel.getMinMax(lvlOfTrain)
        viewModel.goBack.observe(viewLifecycleOwner){
            if(it) findNavController().popBackStack()
        }
        binding?.easyBtn?.setOnClickListener {
            viewModel.changeLvl(1)
        }
        binding?.rightBtn?.setOnClickListener {
            viewModel.changeLvl(0)
        }
        binding?.hardBtn?.setOnClickListener {
            viewModel.changeLvl(-1)
        }
        binding?.homeBtn?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}