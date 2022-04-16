package com.wallet.turnikipushups.ui.notification

import android.view.LayoutInflater
import com.wallet.turnikipushups.databinding.FragmentNotificationBinding
import com.wallet.turnikipushups.ui.BaseFragment

class NotificationFragment : BaseFragment<FragmentNotificationBinding>() {

    override fun bind(inflater: LayoutInflater): FragmentNotificationBinding {
        return FragmentNotificationBinding.inflate(inflater)
    }

    override fun initView() {
        withBinding {
            toolbar.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

}