package com.wallet.turnikipushups.ui.statistics

import android.view.LayoutInflater
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.wallet.turnikipushups.databinding.StatisticsFragmentBinding
import com.wallet.turnikipushups.di.ViewModelFactory
import com.wallet.turnikipushups.ui.BaseFragment

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
            binding?.viewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if(position == 0) {
                        binding?.arrowRight?.isInvisible = true
                    }else {
                        binding?.arrowRight?.isVisible = true
                    }
                    if(position+1 == binding?.viewPager?.adapter?.itemCount) {
                        binding?.arrowLeft?.isInvisible = true
                    }else{
                        binding?.arrowLeft?.isVisible = true
                    }
                    binding?.monthText?.text = getDateFormat(it[position])
                }
            })
        }
        viewModel.mapStatsPushUps.observe(viewLifecycleOwner){
            val staticsPagerAdapter = StatisticsPagerAdapter(
                parentFragmentManager,
                lifecycle,
                viewModel.listMonths.value!!,
                it,
            )
            binding?.viewPager?.adapter = staticsPagerAdapter
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