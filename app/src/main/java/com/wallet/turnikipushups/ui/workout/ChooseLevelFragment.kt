package com.wallet.turnikipushups.ui.workout

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.mikepenz.fastadapter.ClickListener
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.wallet.turnikipushups.databinding.FragmentChooseLevelBinding
import com.wallet.turnikipushups.di.ViewModelFactory
import com.wallet.turnikipushups.ui.BaseFragment
import com.wallet.turnikipushups.ui.PopUps

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
        viewModel.getPurchaseList()
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
            onClickListener = { view, iAdapter, chooseItem, i ->
                 if(viewModel.isHavePro.value == true) {
                     clickListener(view,iAdapter,chooseItem,i)
                 }else{
                     PopUps().purchasePopUp(requireActivity()){
                         viewModel.buyPro(requireActivity())
                     }
                 }
                true
            }
        }
        binding?.proRec?.adapter = proFastAdapter.apply {
            onClickListener = { view, iAdapter, chooseItem, i ->
                if(viewModel.isHavePro.value == true) {
                    clickListener(view,iAdapter,chooseItem,i)
                }else{
                    PopUps().purchasePopUp(requireActivity()){
                        viewModel.buyPro(requireActivity())
                    }
                }
                true
            }
        }


        val viewClickListener:View.OnClickListener = View.OnClickListener{
            if(viewModel.isHavePro.value == false) {
                PopUps().purchasePopUp(requireActivity()){
                    viewModel.buyPro(requireActivity())
                }
            }
        }

        binding?.mediumText?.setOnClickListener(viewClickListener)
        binding?.proText?.setOnClickListener(viewClickListener)
        binding?.profiText?.setOnClickListener(viewClickListener)
        binding?.proText2?.setOnClickListener(viewClickListener)

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
        viewModel.isHavePro.observe(viewLifecycleOwner){
            if(!it){
                binding?.mediumRec?.alpha  = 0.5f
                binding?.proRec?.alpha  = 0.5f
                binding?.mediumText?.alpha  = 0.5f
                binding?.proText?.visibility  = View.VISIBLE
                binding?.mediumRec?.visibility = View.GONE
                binding?.profiText?.alpha  = 0.5f
                binding?.proText2?.visibility  = View.VISIBLE
                binding?.proRec?.visibility = View.GONE
            }else{
                binding?.mediumRec?.alpha  = 1f
                binding?.proRec?.alpha  = 1f
                binding?.mediumText?.alpha  = 1f
                binding?.proText?.visibility  = View.GONE
                binding?.mediumRec?.visibility = View.VISIBLE
                binding?.profiText?.alpha  = 1f
                binding?.proText2?.visibility  = View.GONE
                binding?.proRec?.visibility = View.VISIBLE
            }
            Log.d("mylog", it.toString())
        }
        viewModel.errorMessage.observe(viewLifecycleOwner){
            if(it.isNotEmpty())Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show()
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