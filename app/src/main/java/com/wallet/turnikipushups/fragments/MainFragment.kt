package com.wallet.turnikipushups.fragments

import android.view.LayoutInflater
import com.wallet.turnikipushups.databinding.FragmentMainBinding


class MainFragment : BaseFragment<FragmentMainBinding>() {
    override fun bind(inflater: LayoutInflater): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater)
    }

    override fun initView() {
        withBinding {

        }
    }

}