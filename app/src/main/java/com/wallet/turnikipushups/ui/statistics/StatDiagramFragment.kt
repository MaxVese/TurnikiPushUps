package com.wallet.turnikipushups.ui.statistics

import android.graphics.DashPathEffect
import android.view.LayoutInflater
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.wallet.turnikipushups.R
import com.wallet.turnikipushups.databinding.FragmentStatDiagramBinding
import com.wallet.turnikipushups.di.ViewModelFactory
import com.wallet.turnikipushups.models.StatPushUps
import com.wallet.turnikipushups.ui.BaseFragment

class StatDiagramFragment(val pair:Pair<Int,Int>) : BaseFragment<FragmentStatDiagramBinding>() {
    override fun bind(inflater: LayoutInflater): FragmentStatDiagramBinding {
        return FragmentStatDiagramBinding.inflate(inflater)
    }

    private val viewModel: StatisticsViewModel by viewModels {
        ViewModelFactory{
            getAppComponent(requireContext()).statisticsViewModel()
        }
    }

    override fun initView() {
        setChartSettings()
        val list = viewModel.mapStatsPushUps.value
            ?.getOrDefault(pair,null)

        for(i in 1..31){
            setData(Pair(i,list?.filter { it.dateWorkout?.dayOfMonth == i }?.sumOf { it.count } ?: 0))
        }
    }



    private fun setData(count: Pair<Int,Int>) {
        if (binding.chart.data != null && binding.chart.data.dataSetCount > 0) {
            addValueDataSet(count)
        } else {
            createDataSet(count)
        }
    }

    private fun addValueDataSet(count: Pair<Int,Int>) {
        val set1 = binding.chart.data.getDataSetByIndex(0) as BarDataSet
        set1.addEntry(BarEntry(count.first.toFloat(), count.second.toFloat()))
        set1.notifyDataSetChanged()
        binding.chart.data.notifyDataChanged()
        binding.chart.notifyDataSetChanged()
        binding.chart.invalidate()
    }

    private fun createDataSet(count: Pair<Int,Int>) {
        val values = ArrayList<BarEntry>()
        values.add(BarEntry(count.first.toFloat(), count.second.toFloat()))
        val set = BarDataSet(values, "")
        set.setGradientColor(resources.getColor(R.color.blue_60,null),resources.getColor(R.color.blue,null))
        set.barBorderColor = resources.getColor(android.R.color.transparent,null)
        set.barBorderWidth = 20f
        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(set)
        val data = BarData(dataSets)
        binding.chart.data = data.apply {
            barWidth = 0.5f
        }
    }

    private fun setChartSettings() {
        withBinding {
            chart.description.isEnabled = false
            chart.setTouchEnabled(false)
            chart.legend.form = Legend.LegendForm.NONE
            chart.axisRight.setDrawLabels(false)
                ResourcesCompat.getFont(requireActivity(), R.font.inter_medium)
            chart.axisRight.setDrawAxisLine(false)
            chart.axisRight.setDrawGridLines(false)
            chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            chart.xAxis.setDrawGridLines(false)
            chart.xAxis.textColor = resources.getColor(R.color.white,null)
            chart.xAxis.axisMinimum = 1f
            chart.xAxis.axisMaximum = 31f
            chart.xAxis.setDrawAxisLine(false)
            chart.xAxis.setLabelCount(15, true)
            chart.axisLeft.setGridDashedLine(DashPathEffect(FloatArray(2){ 30f },0f))
            chart.axisLeft.gridLineWidth = 1f
            chart.axisLeft.textColor = resources.getColor(R.color.yellow,null)
            chart.axisLeft.typeface =
                ResourcesCompat.getFont(requireActivity(), R.font.inter_medium)
            chart.axisLeft.xOffset = 20f
            chart.axisLeft.setDrawAxisLine(false)
            chart.axisLeft.axisMinimum = 0f
            chart.axisLeft.setLabelCount(7, true)
        }
    }

}