package com.bogdanov.ocstestapp.domain

import androidx.core.text.HtmlCompat
import com.bogdanov.ocstestapp.data.MainRepository
import com.bogdanov.ocstestapp.data.api.model.VacancyResponse
import io.reactivex.Observable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainInteractor @Inject constructor(val mainRepository: MainRepository) {

    fun getData() = mainRepository.getData()
            .flatMap { list ->
                        Observable.fromIterable(list)
                                .map { mapToVacancy(it) }
                                .toList()
                    }

    private fun mapToVacancy(vr: VacancyResponse): Vacancy {
        val createdAt = getReadableDate(vr.createdAt)
        val description = parseHtmlText(vr.description)
        return Vacancy(
                id = vr.id,
                type = vr.type,
                url = vr.url,
                createdAt = createdAt,
                company = vr.company,
                location = vr.location,
                companyUrl = vr.companyUrl,
                title = vr.title,
                description = description,
                howToApply = vr.howToApply,
                companyLogoUrl = vr.companyLogoUrl)
    }

    private fun getReadableDate(inputString: String): String {
        //Fri Mar 08 08:08:47 UTC 2019
        return try {
            val oldFormat = "E MMM dd HH:mm:ss zzz yyyy"
            val newFormat = "d MMMM yyyy"
            val sdf = SimpleDateFormat(oldFormat, Locale.UK)
            val date = sdf.parse(inputString)
            sdf.applyPattern(newFormat)
            sdf.format(date)
        } catch (exception: ParseException) {
            inputString
        }
    }

    private fun parseHtmlText(htmlText: String): String {
        return HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    }
}