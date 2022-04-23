package com.wallet.turnikipushups.ui.freestyle

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.view.LayoutInflater
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import com.wallet.turnikipushups.R
import com.wallet.turnikipushups.databinding.FragmentFreestyleBinding
import com.wallet.turnikipushups.di.ViewModelFactory
import com.wallet.turnikipushups.service.CounterPullUpsService
import com.wallet.turnikipushups.ui.BaseFragment
import com.wallet.turnikipushups.ui.PopUps
import com.wallet.turnikipushups.ui.main.MainViewModel
import com.wallet.turnikipushups.utils.SoundPooler

class FreestyleFragment : BaseFragment<FragmentFreestyleBinding>() {
    override fun bind(inflater: LayoutInflater): FragmentFreestyleBinding {
        return FragmentFreestyleBinding.inflate(inflater)
    }

    val r = MainFragmentReceiver()
    val soundPooler = SoundPooler();


    private val viewModel: FreestyleViewModel by viewModels {
        ViewModelFactory{
            getAppComponent(requireContext()).freestyleViewModel()
        }
    }
    override fun initView() {
        soundPooler.initSound(requireContext())
        val isTest = arguments?.getBoolean("is_test",false)?:false
        r.register()
        withBinding {
            PopUps().infoPullUpPopUp(requireActivity())
            viewModel.isFinish.observe(viewLifecycleOwner){
                if(it){
                    if(isTest){
                        findNavController().navigate(R.id.action_freestyleFragment_to_lvlStartFragment)
                    }else{
                        PopUps().finishFreestylePopUp(requireActivity(),viewModel.count.value?:0){
                            findNavController().popBackStack()
                        }
                    }
                }
            }
            viewModel.count.observe(viewLifecycleOwner){
                repsValue.text = it.toString()
            }
            viewModel.isVolumeEnable.observe(viewLifecycleOwner){
                binding?.volumeBtn?.setImageDrawable(
                    ResourcesCompat.getDrawable(resources,if(it){
                    R.drawable.ic_volume
                }else{
                     R.drawable.ic_volume_disable
                     },null))
            }
            editBtn.setOnClickListener {
                BottomSheetCorrectFragment().show(requireActivity().supportFragmentManager,"tag")
            }
            startBtn.setOnClickListener {
                if (viewModel.isServiceStart){
                    viewModel.saveWorkout(isTest)
                }else{
                    viewModel.isServiceStart = true
                    startBtn.text = getString(R.string.Stop)
                    requireContext().startForegroundService(Intent(requireContext(),CounterPullUpsService::class.java))
                }
            }
            homeBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            volumeBtn.setOnClickListener {
                viewModel.changeVolume()
            }
        }
    }


    inner class MainFragmentReceiver : BroadcastReceiver() {
        override fun onReceive(c: Context, i: Intent) {
            viewModel.count.value = viewModel.count.value?.plus(1)
            if(viewModel.isVolumeEnable.value == true) soundPooler.tickSound()
        }

        fun register() {
            val i = IntentFilter()
            i.addAction(CounterPullUpsService.ADD_COUNT)
            requireActivity().registerReceiver(this, i)
        }

        fun unregister() {
            requireActivity().unregisterReceiver(this)
        }
    }


    override fun onDestroy() {
        requireContext().stopService(Intent(requireContext(),CounterPullUpsService::class.java))
        r.unregister()
        viewModel.count.value = 0
        viewModel.isFinish.value = false
        viewModel.isServiceStart = false
        super.onDestroy()
    }

}