package com.bogdanov.ocstestapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bogdanov.ocstestapp.R
import com.bogdanov.ocstestapp.base.AbstractBaseActivity
import com.bogdanov.ocstestapp.domain.Vacancy
import com.bogdanov.ocstestapp.ui.detail.DetailActivity
import com.bogdanov.ocstestapp.ui.main.adapter.VacanciesAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AbstractBaseActivity<MainView, MainPresenter>(), MainView {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var mAdapter: VacanciesAdapter
    private lateinit var mLayoutManager: LinearLayoutManager

    private var previousTotal = 0
    private var loading = true
    private val visibleThreshold = 3
    private var firstVisibleItem: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var page = 0

    private var mHandler: Handler? = null
    private var mQuery: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        mHandler = Handler()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.i(TAG, "onQueryTextSubmit $query")
                mHandler?.removeCallbacksAndMessages(null)
                if (query != null) {
                    if (!query.isEmpty()) {
                        mAdapter.setNew()

                        mQuery = query
                        mHandler?.postDelayed({ presenter.getData(mQuery, 0) }, 1000)
                    }
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        mAdapter = VacanciesAdapter(list = ArrayList(), listener = object : VacanciesAdapter.CandidatesAdapterListener {
            override fun onItemPress(vacancy: Vacancy) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_VACANCY, vacancy)
                startActivity(intent)
            }
        })
        mLayoutManager = LinearLayoutManager(this)
        recylcerView.layoutManager = mLayoutManager
        recylcerView.adapter = mAdapter
        recylcerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                visibleItemCount = recyclerView.childCount
                totalItemCount = mLayoutManager.itemCount
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition()

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false
                        previousTotal = totalItemCount
                    }
                }

                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    page++
                    presenter.getData(mQuery, page)
                    loading = true
                }
            }
        })
    }

    override fun showCandidates(vacancyList: MutableList<Vacancy>) {
        mAdapter.add(vacancyList)
    }
}
