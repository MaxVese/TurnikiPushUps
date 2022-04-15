package com.wallet.turnikipushups.ui.freestyle

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wallet.turnikipushups.R
import com.wallet.turnikipushups.databinding.FragmentBottomSheetCorrectBinding

class BottomSheetCorrectFragment(val startValue:MutableLiveData<Int>,val update:(Int) -> Unit) : BottomSheetDialogFragment() {

    lateinit var binding:FragmentBottomSheetCorrectBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetCorrectBinding.bind(inflater.inflate(R.layout.fragment_bottom_sheet_correct,container))
        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }

    override fun onStart() {
        super.onStart()
        startValue.observe(viewLifecycleOwner){
            if(it != binding.slider.value.toInt()){
                binding.slider.value = it.toFloat()
            }
        }
        binding.minusBtn.setOnClickListener {
            changeValueSlider(-1)
        }
        binding.plusBtn.setOnClickListener {
            changeValueSlider(+1)
        }
        binding.textMinus10.setOnClickListener {
            changeValueSlider(-10)
        }
        binding.textPlus10.setOnClickListener {
            changeValueSlider(+10)
        }
        binding.textMinus50.setOnClickListener {
            changeValueSlider(-50)
        }
        binding.textPlus50.setOnClickListener {
            changeValueSlider(+50)
        }
        binding.finishBtn.setOnClickListener {
            dismiss()
        }
        binding.slider.addOnChangeListener { _, value, _ ->
            update(value.toInt())
        }
    }

    private fun changeValueSlider(value:Int){
        if ((binding.slider.value.toInt()+value) !in 0..300) return
        binding.slider.value = binding.slider.value + value
    }

}