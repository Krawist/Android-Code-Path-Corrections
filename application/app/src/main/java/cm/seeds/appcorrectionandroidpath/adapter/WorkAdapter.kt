package cm.seeds.appcorrectionandroidpath.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cm.seeds.appcorrectionandroidpath.databinding.ItemWorkBinding
import cm.seeds.appcorrectionandroidpath.helper.ToDoOnActions
import cm.seeds.appcorrectionandroidpath.helper.gone
import cm.seeds.appcorrectionandroidpath.helper.show
import cm.seeds.appcorrectionandroidpath.modeles.Work

class WorkAdapter(private val toDoOnActions: ToDoOnActions) : ListAdapter<Work, WorkAdapter.WorkViewHolder>(object : DiffUtil.ItemCallback<Work>(){
    override fun areItemsTheSame(oldItem: Work, newItem: Work): Boolean {
        return oldItem.workId == newItem.workId
    }

    override fun areContentsTheSame(oldItem: Work, newItem: Work): Boolean {
        return oldItem == newItem
    }
}){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkViewHolder {
        return WorkViewHolder(ItemWorkBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: WorkViewHolder, position: Int) {
        holder.bindData(getItem(position),position)
    }

    inner class WorkViewHolder(private val dataBinding: ItemWorkBinding) : RecyclerView.ViewHolder(dataBinding.root){

        fun bindData(work: Work, position : Int){
            dataBinding.root.setOnClickListener {
                toDoOnActions.onClick(work,position,it)
            }

            dataBinding.workName.text = work.workName

            dataBinding.imagePreview.setImageResource(work.realisationImageId)

            if(work.evaluate){
                dataBinding.indicatorEvaluate.show()
            }else{
                dataBinding.indicatorEvaluate.gone()
            }
        }

    }
}