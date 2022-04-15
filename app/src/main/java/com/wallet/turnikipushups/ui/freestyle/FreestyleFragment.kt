package com.wallet.turnikipushups.ui.freestyle

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.wallet.turnikipushups.databinding.FragmentFreestyleBinding
import com.wallet.turnikipushups.di.ViewModelFactory
import com.wallet.turnikipushups.ui.BaseFragment
import com.wallet.turnikipushups.ui.PopUps
import com.wallet.turnikipushups.ui.main.MainViewModel

class FreestyleFragment : BaseFragment<FragmentFreestyleBinding>() {
    override fun bind(inflater: LayoutInflater): FragmentFreestyleBinding {
        return FragmentFreestyleBinding.inflate(inflater)
    }

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory{
            getAppComponent(requireContext()).mainViewModel()
        }
    }

    val count:MutableLiveData<Int> = MutableLiveData(0)

    override fun initView() {
        withBinding {
            PopUps().infoPullUpPopUp(requireActivity())
            editBtn.setOnClickListener {
                BottomSheetCorrectFragment(count){
                    if(it != count.value) count.value = it
                }
                    .show(requireActivity().supportFragmentManager,"tag")
            }
            count.observe(viewLifecycleOwner){
                repsValue.text = it.toString()
            }
        }
    }

}