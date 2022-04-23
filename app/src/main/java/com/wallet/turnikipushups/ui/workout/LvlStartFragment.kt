package com.wallet.turnikipushups.ui.workout

import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import com.wallet.turnikipushups.R
import com.wallet.turnikipushups.databinding.FragmentLvlStartBinding
import com.wallet.turnikipushups.di.ViewModelFactory
import com.wallet.turnikipushups.ui.BaseFragment
import java.util.*
import kotlin.collections.ArrayList

class LvlStartFragment : BaseFragment<FragmentLvlStartBinding>() {
    override fun bind(inflater: LayoutInflater): FragmentLvlStartBinding {
        return FragmentLvlStartBinding.inflate(inflater)
    }

    private val viewModel: StartWorkoutViewModel by viewModels {
        ViewModelFactory{
            getAppComponent(requireContext()).startWorkoutViewModel()
        }
    }

    override fun initView() {
        val id = viewModel.getLvl()
        viewModel.workout.observe(viewLifecycleOwner){
            binding?.lvlText?.text = getString(R.string.level) + " " + it.lvl + "." + it.subLevel
            var textSets = ""
            var sum = 0
            it.listReps.forEach {
                textSets = "$textSets$it-"
                sum += it
            }
            binding?.setsValue?.text = textSets.dropLast(1)
            binding?.sumRepsValue?.text = sum.toString()
            viewModel.getCountInLvl(it.lvlOfTraining,it.lvl)
            viewModel.getCountLevels(it.lvlOfTraining)
        }
        viewModel.countInThisLvl.observe(viewLifecycleOwner){
            val progressValue:Float = (viewModel.workout.value?.subLevel?.minus(1))?.div(it+0f)?:0f
            val prograssLayoutWidth: Float = (binding?.progressLayout?.width)?.toFloat()?:1f
            binding?.progress?.updateLayoutParams {
                width = (prograssLayoutWidth * progressValue).toInt()
            }
        }
        viewModel.countLevels.observe(viewLifecycleOwner){
            val textViews:ArrayList<TextView> = arrayListOf()
            for (i in 1 until it){
                textViews.add(
                    TextView(requireContext(),null,0,R.style.text16pxBlack).apply {
                        setId(Random().nextInt(Integer.MAX_VALUE) + 1)
                        background = ResourcesCompat.getDrawable(resources,if(i == viewModel.workout.value?.lvl) R.drawable.cirle_lvl_blue else R.drawable.cirle_lvl,null)
                        if(i == viewModel.workout.value?.lvl) text = i.toString()
                        gravity = Gravity.CENTER
                    })
            }
            binding?.constraintLayout?.removeAllViews()
            textViews.forEachIndexed { index, textView ->
                textView.layoutParams = ConstraintLayout.LayoutParams(
                    resources.getDimension(com.intuit.sdp.R.dimen._32sdp).toInt(),
                    resources.getDimension(com.intuit.sdp.R.dimen._32sdp).toInt()).apply {
                    if(index == 0){
                        startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                    }else{
                        startToEnd = textViews[index-1].id
                    }
                    if(index+1 >= textViews.size){
                        endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                    }else{
                        endToStart = textViews[index+1].id
                    }
                    topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                    bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                    horizontalChainStyle = ConstraintLayout.LayoutParams.CHAIN_SPREAD_INSIDE
                }
                binding?.constraintLayout?.addView(textView)
            }
            Log.d("mylog",it.toString())
        }
        viewModel.getWorkout(id)
        withBinding {
            beginBtn.setOnClickListener {
                findNavController().navigate(R.id.action_lvlStartFragment_to_workoutFragment,
                    bundleOf("id" to id))
            }
            homeBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            chooseLvlBtn.setOnClickListener {
                findNavController().navigate(R.id.action_lvlStartFragment_to_chooseLevelFragment)
            }
        }
    }

}