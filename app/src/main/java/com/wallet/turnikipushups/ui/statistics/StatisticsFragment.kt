package com.wallet.turnikipushups.ui.statistics

import android.graphics.DashPathEffect
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.wallet.turnikipushups.R
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
        setChartSettings()
        for(i in 1..31){
            setData(Pair(i,i+1/2))
        }
    }


    private fun setData(count: Pair<Int,Int>) {
        if (binding!!.chart.data != null && binding!!.chart.data.dataSetCount > 0) {
            addValueDataSet(count)
        } else {
            createDataSet(count)
        }
    }

    private fun addValueDataSet(count: Pair<Int,Int>) {
        val set1 = binding!!.chart.data.getDataSetByIndex(0) as BarDataSet
        set1.addEntry(BarEntry(count.first.toFloat(), count.second.toFloat()))
        set1.notifyDataSetChanged()
        binding!!.chart.data.notifyDataChanged()
        binding!!.chart.notifyDataSetChanged()
        binding!!.chart.invalidate()
    }

    private fun createDataSet(count: Pair<Int,Int>) {
        val values = ArrayList<BarEntry>()
        values.add(BarEntry(count.first.toFloat(), count.second.toFloat()))
        val set = BarDataSet(values, "")
        set.setGradientColor(resources.getColor(R.color.blue_60),resources.getColor(R.color.blue))
        set.barBorderColor = resources.getColor(android.R.color.transparent)
        set.barBorderWidth = 20f
//        setLineDataSetSettings(set)
        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(set)
        val data = BarData(dataSets)
        binding!!.chart.data = data.apply {
            barWidth = 0.5f
        }
    }

    private fun setChartSettings() {
        withBinding {
            chart.description.isEnabled = false
            chart.setTouchEnabled(false)
            chart.legend.form = Legend.LegendForm.NONE
            chart.axisRight.setDrawLabels(false)
            chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            chart.axisLeft.setGridDashedLine(DashPathEffect(FloatArray(2){ 20f },0f))
            chart.axisLeft.gridLineWidth = 2f
            chart.axisRight.setGridDashedLine(DashPathEffect(FloatArray(2){ 20f },0f))
            chart.axisRight.gridLineWidth = 2f
            chart.xAxis.setDrawGridLines(false)
            chart.xAxis.textColor = resources.getColor(R.color.white)
            chart.axisLeft.textColor = resources.getColor(R.color.white)
            chart.xAxis.setDrawAxisLine(false)
            chart.axisRight.setDrawAxisLine(false)
            chart.axisRight.setDrawGridLines(false)
            chart.axisLeft.xOffset = 20f
            chart.axisLeft.setDrawAxisLine(false)
            chart.xAxis.axisMinimum = 1f
            chart.xAxis.axisMaximum = 31f
            chart.xAxis.setLabelCount(15, true)
            chart.axisLeft.axisMinimum = 0f
            chart.axisLeft.setLabelCount(7, true)
        }
    }

}