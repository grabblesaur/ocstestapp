package com.bogdanov.ocstestapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bogdanov.ocstestapp.R
import com.bogdanov.ocstestapp.data.api.model.Vacancy
import kotlinx.android.synthetic.main.item_candidate.view.*

class VacanciesAdapter : RecyclerView.Adapter<VacanciesAdapter.CandidatesViewHolder> {

    interface CandidatesAdapterListener {
        fun onItemPress(vacancy: Vacancy)
    }

    private var mList: ArrayList<Vacancy>
    private var mListener: CandidatesAdapterListener?

    constructor(list: ArrayList<Vacancy> = ArrayList(),
                listener: CandidatesAdapterListener? = null) : super() {
        this.mList = list
        this.mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidatesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_candidate, parent, false)
        return CandidatesViewHolder(view)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: CandidatesViewHolder, position: Int) {
        holder.onBind(mList[position])
    }

    fun replace(vacancies: ArrayList<Vacancy>) {
        mList = vacancies
        notifyDataSetChanged()
    }


    inner class CandidatesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(vacancy: Vacancy) {
            itemView.itemLayout.setOnClickListener {
                mListener?.onItemPress(vacancy)
            }
            itemView.name.text = vacancy.title
        }
    }

}