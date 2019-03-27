package com.bogdanov.ocstestapp.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.text.HtmlCompat
import com.bogdanov.ocstestapp.R
import com.bogdanov.ocstestapp.data.api.model.Vacancy
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.SimpleDateFormat
import java.util.*

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
            supportActionBar?.title = vacancy.company
            titleTextView.text = vacancy.title
            createdOnTextView.text = getReadableDate(vacancy.createdAt)
            locationTextView.text = vacancy.location
            typeTextView.text = vacancy.type
            descriptionTextView.text = parseHtmlText(vacancy.description)

            Glide.with(this)
                .load(vacancy.companyLogoUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(companyLogoImageView)
        }
    }


    private fun getReadableDate(inputString: String): String {
        //Fri Mar 08 08:08:47 UTC 2019
        val oldFormat = "E MMM dd HH:mm:ss zzz yyyy"
        val newFormat = "d MMMM yyyy"
        val sdf = SimpleDateFormat(oldFormat, Locale.UK)
        val date = sdf.parse(inputString)
        sdf.applyPattern(newFormat)
        return sdf.format(date)
    }

    private fun parseHtmlText(htmlText: String): String {
        return HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    }

}