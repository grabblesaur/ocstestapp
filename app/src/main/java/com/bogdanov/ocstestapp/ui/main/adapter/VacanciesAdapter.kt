package com.bogdanov.ocstestapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bogdanov.ocstestapp.R
import com.bogdanov.ocstestapp.domain.Vacancy
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.item_candidate.view.*

class VacanciesAdapter : RecyclerView.Adapter<VacanciesAdapter.CandidatesViewHolder> {

    interface CandidatesAdapterListener {
        fun onItemPress(vacancy: Vacancy)
    }

    private var mList: MutableList<Vacancy>
    private var mListener: CandidatesAdapterListener?

    constructor(list: MutableList<Vacancy> = ArrayList(),
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

    fun replace(vacancyList: MutableList<Vacancy>) {
        mList = vacancyList
        notifyDataSetChanged()
    }

    inner class CandidatesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(vacancy: Vacancy) {
            itemView.itemLayout.setOnClickListener {
                mListener?.onItemPress(vacancy)
            }
            itemView.companyNameTextView.text = vacancy.company
            itemView.createdAtTextView.text = vacancy.createdAt
            itemView.titleTextView.text = vacancy.title
            itemView.locationTextView.text = vacancy.location
            itemView.typeTextView.text = vacancy.type

            if (vacancy.companyLogoUrl != null) {
                Glide.with(itemView)
                        .load(vacancy.companyLogoUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(itemView.companyLogoImageView)
            }
        }
    }

}