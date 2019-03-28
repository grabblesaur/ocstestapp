package com.bogdanov.ocstestapp.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bogdanov.ocstestapp.R
import com.bogdanov.ocstestapp.domain.Vacancy
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_candidate.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_VACANCY = "extra-vacancy"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setToolbarWithBackButton(toolbar)
        initViews()
    }

    private fun setToolbarWithBackButton(toolbar: Toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""
        toolbar.setNavigationOnClickListener { v -> onBackPressed() }
    }

    private fun initViews() {
        val vacancy = intent.getSerializableExtra(EXTRA_VACANCY)
        if (vacancy is Vacancy) {
            if (vacancy.companyLogoUrl != null) {
                Glide.with(this)
                        .load(vacancy.companyLogoUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(companyLogoImageView)
            }

            companyNameTextView.text = vacancy.company
            createdAtTextView.text = vacancy.createdAt
            titleTextView.text = vacancy.title
            locationTextView.text = vacancy.location
            typeTextView.text = vacancy.type
            descriptionTextView.text = vacancy.description
        }
    }
}