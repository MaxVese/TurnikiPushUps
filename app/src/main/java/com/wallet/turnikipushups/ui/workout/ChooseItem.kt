package com.wallet.turnikipushups.ui.workout

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.ModelAbstractItem
import com.wallet.turnikipushups.R
import com.wallet.turnikipushups.models.WorkoutEntity

@SuppressLint("ResourceType")
class ChooseItem(model: WorkoutEntity) :
    ModelAbstractItem<WorkoutEntity, ChooseItem.ViewHolder>(model) {

    override val type: Int
        get() = layoutRes
    override val layoutRes: Int
        get() = R.layout.choose_level_item

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }


    class ViewHolder(itemView: View) :
        FastAdapter.ViewHolder<ChooseItem>(itemView) {

        val levelText = itemView.findViewById<TextView>(R.id.levelText)
        val setsText = itemView.findViewById<TextView>(R.id.setsText)

        override fun bindView(item: ChooseItem, payloads: List<Any>) {
            levelText.text = itemView.context.resources.getString(R.string.level) + " " + item.model.lvl + "-" + item.model.subLevel
            var textSets = ""
            item.model.listReps.forEach {
                textSets = "$textSets$it-"
            }
            setsText.text = textSets.dropLast(1)
            if (item.isSelected){
                itemView.setBackgroundColor(itemView.context.getColor(R.color.red_24))
            }else{
                itemView.setBackgroundColor(itemView.context.getColor(R.color.background))
            }
        }


        override fun unbindView(item: ChooseItem) {

        }
    }
}