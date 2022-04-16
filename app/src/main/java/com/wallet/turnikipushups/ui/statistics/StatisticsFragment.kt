package com.wallet.turnikipushups.ui.statistics

import android.graphics.DashPathEffect
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.wallet.turnikipushups.databinding.StatisticsFragmentBinding
import com.wallet.turnikipushups.di.ViewModelFactory
import com.wallet.turnikipushups.ui.BaseFragment
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class StatisticsFragment : BaseFragment<StatisticsFragmentBinding>() {

    override fun bind(inflater: LayoutInflater): StatisticsFragmentBinding {
        return StatisticsFragmentBinding.inflate(inflater)
    }

    private val viewModel: StatisticsViewModel by viewModels {
        ViewModelFactory{
            getAppComponent(requireContext()).statisticsViewModel()
        }
    }


    override fun initView() {
        viewModel.getAllStats()

        viewModel.listMonths.observe(viewLifecycleOwner){
            val staticsPagerAdapter = StatisticsPagerAdapter(
                parentFragmentManager,
                lifecycle,
                it
            )
            binding.viewPager.adapter = staticsPagerAdapter
            binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.monthText.text = getDateFormat(it[position])
                }
            })
        }
        viewModel.statsPushUps.observe(viewLifecycleOwner){
            viewModel.mapStatsPushUps.value = it.groupBy { Pair(it.dateWorkout?.year!!,it.dateWorkout?.monthValue!!) }
        }
        withBinding {
            arrowLeft.setOnClickListener {
                viewPager.currentItem++
            }
            arrowRight.setOnClickListener {
                viewPager.currentItem--
            }
            homeBtn.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    fun getDateFormat(pair:Pair<Int,Int>):String{
        return "${ if (pair.second.toString().length == 1) "0${pair.second}" else "${pair.second}" }/${pair.first}"
    }

}