package com.wallet.turnikipushups.ui.statistics

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.wallet.turnikipushups.models.StatPushUps

class StatisticsPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle,val list:List<Pair<Int,Int>>,val map:Map<Pair<Int,Int>,List<StatPushUps>>):FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return StatDiagramFragment(map.getOrDefault(list[position],null))
    }
}