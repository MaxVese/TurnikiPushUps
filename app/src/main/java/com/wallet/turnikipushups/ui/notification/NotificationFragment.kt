package com.wallet.turnikipushups.ui.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wallet.turnikipushups.R
import com.wallet.turnikipushups.databinding.FragmentNotificationBinding
import com.wallet.turnikipushups.ui.BaseFragment

class NotificationFragment : BaseFragment<FragmentNotificationBinding>() {
    override fun bind(inflater: LayoutInflater): FragmentNotificationBinding {
        return FragmentNotificationBinding.inflate(inflater)
    }

    override fun initView() {

    }

}