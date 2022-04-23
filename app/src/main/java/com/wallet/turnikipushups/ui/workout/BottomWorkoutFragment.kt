package com.wallet.turnikipushups.ui.workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wallet.turnikipushups.R
import com.wallet.turnikipushups.databinding.FragmentBottomSheetCorrectBinding
import com.wallet.turnikipushups.databinding.FragmentBottomWorkoutBinding

class BottomWorkoutFragment(val full:()->Unit,val independently:()->Unit,val exit:()->Unit) : BottomSheetDialogFragment() {

    var binding:FragmentBottomWorkoutBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomWorkoutBinding.bind(inflater.inflate(R.layout.fragment_bottom_workout,container))
        return binding!!.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.exitBtn?.setOnClickListener {
            exit()
            dismiss()
        }
        binding?.independentlyBtn?.setOnClickListener {
            independently()
            dismiss()
        }
        binding?.nextSetBtn?.setOnClickListener {
            full()
            dismiss()
        }
    }

}