package com.wallet.turnikipushups.ui.freestyle

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.wallet.turnikipushups.R
import com.wallet.turnikipushups.databinding.FragmentFreestyleBinding
import com.wallet.turnikipushups.di.ViewModelFactory
import com.wallet.turnikipushups.service.CounterPullUpsService
import com.wallet.turnikipushups.ui.BaseFragment
import com.wallet.turnikipushups.ui.PopUps
import com.wallet.turnikipushups.ui.main.MainViewModel

class FreestyleFragment : BaseFragment<FragmentFreestyleBinding>() {
    override fun bind(inflater: LayoutInflater): FragmentFreestyleBinding {
        return FragmentFreestyleBinding.inflate(inflater)
    }

    val r = MainFragmentReceiver()


    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory{
            getAppComponent(requireContext()).mainViewModel()
        }
    }
    override fun initView() {
        r.register()
        withBinding {
            PopUps().infoPullUpPopUp(requireActivity())
            editBtn.setOnClickListener {
                BottomSheetCorrectFragment().show(requireActivity().supportFragmentManager,"tag")
            }
            viewModel.count.observe(viewLifecycleOwner){
                repsValue.text = it.toString()
            }
            startBtn.setOnClickListener {
                startBtn.text = getString(R.string.Stop)
                requireContext().startService(Intent(requireContext(),CounterPullUpsService::class.java))
            }
            homeBtn.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }


    inner class MainFragmentReceiver : BroadcastReceiver() {
        override fun onReceive(c: Context, i: Intent) {
            viewModel.count.value = viewModel.count.value?.plus(1)
            Log.d("mylog","da blead")
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

    override fun onDestroyView() {
        requireContext().stopService(Intent(requireContext(),CounterPullUpsService::class.java))
        r.unregister()
        super.onDestroyView()
    }

}