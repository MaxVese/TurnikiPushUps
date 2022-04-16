package com.wallet.turnikipushups.ui.settings

import android.view.LayoutInflater
import com.wallet.turnikipushups.databinding.FragmentSettingsBinding
import com.wallet.turnikipushups.ui.BaseFragment

class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    override fun bind(inflater: LayoutInflater): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(inflater)
    }

    override fun initView() {
        withBinding {
            toolbar.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

}