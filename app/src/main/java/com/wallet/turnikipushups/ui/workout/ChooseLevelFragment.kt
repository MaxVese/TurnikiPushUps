package com.wallet.turnikipushups.ui.workout

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.mikepenz.fastadapter.ClickListener
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.wallet.turnikipushups.databinding.FragmentChooseLevelBinding
import com.wallet.turnikipushups.di.ViewModelFactory
import com.wallet.turnikipushups.ui.BaseFragment

class ChooseLevelFragment : BaseFragment<FragmentChooseLevelBinding>() {
    override fun bind(inflater: LayoutInflater): FragmentChooseLevelBinding {
        return FragmentChooseLevelBinding.inflate(inflater)
    }

    private val viewModel: ChooseLevelViewModel by viewModels {
        ViewModelFactory{
            getAppComponent(requireContext()).chooseLevelViewModel()
        }
    }

    var beginnerAdapter: ItemAdapter<ChooseItem> = ItemAdapter<ChooseItem>()
    var mediumAdapter: ItemAdapter<ChooseItem> = ItemAdapter<ChooseItem>()
    var proAdapter: ItemAdapter<ChooseItem> = ItemAdapter<ChooseItem>()

    var beginnerFastAdapter = FastAdapter.with(beginnerAdapter)
    var mediumFastAdapter = FastAdapter.with(mediumAdapter)
    var proFastAdapter = FastAdapter.with(proAdapter)


    override fun initView() {
        val clickListener:ClickListener<ChooseItem> = { view, iAdapter, chooseItem, i ->
            beginnerAdapter.adapterItems.forEachIndexed { index, chooseItem ->
                if(chooseItem.isSelected){
                    chooseItem.isSelected = false
                    beginnerFastAdapter.notifyItemChanged(index)
                }
            }
            mediumAdapter.adapterItems.forEachIndexed { index, chooseItem ->
                if(chooseItem.isSelected){
                    chooseItem.isSelected = false
                    mediumFastAdapter.notifyItemChanged(index)
                }
            }
            proAdapter.adapterItems.forEachIndexed { index, chooseItem ->
                if(chooseItem.isSelected){
                    chooseItem.isSelected = false
                    proFastAdapter.notifyItemChanged(index)
                }
            }
            chooseItem.isSelected = true
            iAdapter.fastAdapter?.notifyItemChanged(i)
            viewModel.newLvl = chooseItem.model.id
            true
        }
        binding?.beginnerRec?.adapter = beginnerFastAdapter.apply {
            onClickListener = clickListener
        }
        binding?.mediumRec?.adapter = mediumFastAdapter.apply {
            onClickListener = clickListener
        }
        binding?.proRec?.adapter = proFastAdapter.apply {
            onClickListener = clickListener
        }


        viewModel.beginnerList.observe(viewLifecycleOwner){
            it.forEach {
                beginnerAdapter.add(ChooseItem(it).apply { if(it.id == viewModel.newLvl) isSelected = true })
            }
        }
        viewModel.mediumList.observe(viewLifecycleOwner){
            it.forEach {
                mediumAdapter.add(ChooseItem(it).apply { if(it.id == viewModel.newLvl) isSelected = true })
            }
        }
        viewModel.proList.observe(viewLifecycleOwner){
            it.forEach {
                proAdapter.add(ChooseItem(it).apply { if(it.id == viewModel.newLvl) isSelected = true })
            }
        }
        viewModel.goBack.observe(viewLifecycleOwner){
            if(it) findNavController().popBackStack()
        }
        viewModel.getLists()

        binding?.toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding?.okText?.setOnClickListener {
            viewModel.changeLvl()
        }
    }

}