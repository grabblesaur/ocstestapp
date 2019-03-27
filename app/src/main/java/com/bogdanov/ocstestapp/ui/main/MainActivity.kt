package com.bogdanov.ocstestapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bogdanov.ocstestapp.R
import com.bogdanov.ocstestapp.base.AbstractBaseActivity
import com.bogdanov.ocstestapp.data.api.model.Vacancy
import com.bogdanov.ocstestapp.ui.detail.DetailActivity
import com.bogdanov.ocstestapp.ui.main.adapter.VacanciesAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AbstractBaseActivity<MainView, MainPresenter>(), MainView {

    private lateinit var mAdapter: VacanciesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        presenter.getData()
    }

    private fun initViews() {
        mAdapter = VacanciesAdapter(listener = object : VacanciesAdapter.CandidatesAdapterListener {
            override fun onItemPress(vacancy: Vacancy) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_VACANCY, vacancy)
                startActivity(intent)
            }
        })
        recylcerView.layoutManager = LinearLayoutManager(this)
        recylcerView.adapter = mAdapter
    }

    override fun showCandidates(vacancies: ArrayList<Vacancy>) {
        mAdapter.replace(vacancies)
    }

    override fun setProgressBar(flag: Boolean) {
        if (flag) {
            progressBar.visibility = View.VISIBLE
            recylcerView.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
            recylcerView.visibility = View.VISIBLE
        }
    }
}
