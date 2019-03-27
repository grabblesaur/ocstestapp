package com.bogdanov.ocstestapp.data.api.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Vacancy(
    val id: String,
    val type: String,
    val url: String,
    @SerializedName("created_at")
    val createdAt: String,
    val company: String,
    @SerializedName("company_url")
    val companyUrl: String,
    val location: String,
    val title: String,
    val description: String,
    @SerializedName("how_to_apply")
    val howToApply: String,
    @SerializedName("company_logo")
    val companyLogoUrl: String
) : Serializable