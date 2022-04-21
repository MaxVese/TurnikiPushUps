package com.wallet.turnikipushups.ui.workout

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.CountDownTimer
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.wallet.turnikipushups.R
import com.wallet.turnikipushups.databinding.WorkoutFragmentBinding
import com.wallet.turnikipushups.db.converters.LevelOfTrainingConverter
import com.wallet.turnikipushups.di.ViewModelFactory
import com.wallet.turnikipushups.models.LevelOfTraining
import com.wallet.turnikipushups.service.CounterPullUpsService
import com.wallet.turnikipushups.ui.BaseFragment

class WorkoutFragment : BaseFragment<WorkoutFragmentBinding>() {
    override fun bind(inflater: LayoutInflater): WorkoutFragmentBinding {
        return WorkoutFragmentBinding.inflate(inflater)
    }

    private val viewModel: WorkoutViewModel by viewModels {
        ViewModelFactory{
            getAppComponent(requireContext()).workoutViewModel()
        }
    }

    val r = WorkoutRepsReceiver()
    val setsTextViews : ArrayList<TextView> = arrayListOf()
    val countDownTimer:CountDownTimer by lazy {
        val restTime:Long = (viewModel.workout.value?.restTime?:60).toLong() * 1000
        object: CountDownTimer(restTime, 1000){
            override fun onTick(p0: Long) {
                val timerValue = p0.div(1000).toInt()
                viewModel.countReps.value = Pair(timerValue,true)
            }
            override fun onFinish() {
                //add your code here
            }
        }
    }

    override fun initView() {
        r.register()
        requireContext().startService(Intent(requireContext(),CounterPullUpsService::class.java))
        val id = arguments?.getInt("id") ?: 1
        viewModel.workout.observe(viewLifecycleOwner){
            it.listReps.forEach {
                val textView = TextView(requireContext(),null,0,R.style.text14px)
                textView.layoutParams = ViewGroup.MarginLayoutParams(
                    resources.getDimension(com.intuit.sdp.R.dimen._20sdp).toInt(),
                    resources.getDimension(com.intuit.sdp.R.dimen._20sdp).toInt()).apply {
                    leftMargin = resources.getDimension(com.intuit.sdp.R.dimen._4sdp).toInt()
                    rightMargin = resources.getDimension(com.intuit.sdp.R.dimen._4sdp).toInt()
                }
                textView.background = ResourcesCompat.getDrawable(resources,R.drawable.circle_with_stroke_small,null)
                textView.gravity = Gravity.CENTER
                textView.text = it.toString()
                setsTextViews.add(textView)
            }
            setsTextViews.forEach {
                binding?.setLayout?.addView(it)
            }
            viewModel.currentSet.value = 0
        }
        viewModel.currentSet.observe(viewLifecycleOwner){
            setsTextViews.forEachIndexed { index, textView ->
                if(index != it) {
                    textView.alpha = 0.24f
                }else{
                    textView.alpha = 1f
                }
            }
            viewModel.workout.value?.let { workout -> viewModel.countReps.value =
                Pair(workout.listReps[it],false)
            }
        }
        viewModel.countReps.observe(viewLifecycleOwner){
            binding?.repsValue?.text = it.first.toString()
            if(it.second){
                binding?.proceedBtn?.isVisible = true
                binding?.toDoText?.text = getString(R.string.relax)
            }else{
                binding?.proceedBtn?.isInvisible = true
                binding?.toDoText?.text = getToDoText(it.first)
            }
            if(it.first == 0){
                viewModel.currentSet.value?.let {currentSet ->
                    if(viewModel.workout.value?.listReps?.size?.minus(1)?:0 <= currentSet){
                        viewModel.saveWorkout()
                    }
                }
                if(it.second){
                    viewModel.currentSet.value = viewModel.currentSet.value?.plus(1)
                }else{
                    countDownTimer.start()
                }
            }
        }
        viewModel.isFinish.observe(viewLifecycleOwner){
            if(it == true) findNavController().navigate(R.id.action_workoutFragment_to_finalWorkoutFragment,
                bundleOf("lvlOfTrain" to LevelOfTrainingConverter().fromLevelOfTraining(viewModel.workout.value?.lvlOfTraining?:LevelOfTraining.BEGINNER)))
        }
        binding?.proceedBtn?.setOnClickListener {
            countDownTimer.cancel()
            viewModel.currentSet.value = viewModel.currentSet.value?.plus(1)
        }
        binding?.homeBtn?.setOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.getWorkout(id)
    }


    private fun getToDoText(reps:Int):String{
        return when(reps){
            1 -> getString(R.string.do_text) + " " + reps + " " + getString(R.string.pull_up_1)
            in 2..4 -> getString(R.string.do_text) + " " + reps + " " + getString(R.string.pull_up_2_4)
            else -> getString(R.string.do_text) + " " + reps + " " + getString(R.string.pull_up_5)
        }
    }

    inner class WorkoutRepsReceiver : BroadcastReceiver() {
        override fun onReceive(c: Context, i: Intent) {
            viewModel.countReps.value?.let {
                if(!it.second){
                    viewModel.countReps.value = Pair(it.first-1,false)
                }
            }
        }

        fun register() {
            val i = IntentFilter()
            i.addAction(CounterPullUpsService.ADD_COUNT)
            requireActivity().registerReceiver(this, i)
        }

        fun unregister() {
            requireActivity().unregisterReceiver(this)
        }
    }

    override fun onDestroyView() {
        requireContext().stopService(Intent(requireContext(),CounterPullUpsService::class.java))
        r.unregister()
        countDownTimer.cancel()
        super.onDestroyView()
    }

}