package com.maxidev.newnews.core_remote.remote.model_remote

import kotlinx.serialization.Serializable

@Serializable
data class Fields(
    val headline: String? = "",
    val standfirst: String? = "",
    val trailText: String? = "",
    val byline: String? = "",
    val main: String? = "",
    val body: String? = "",
    val newspaperPageNumber: String? = "",
    val wordcount: String? = "",
    val commentCloseDate: String? = "",
    val commentable: String? = "",
    val firstPublicationDate: String? = "",
    val isInappropriateForSponsorship: String? = "",
    val isPremoderated: String? = "",
    val lastModified: String? = "",
    val newspaperEditionDate: String? = "",
    val productionOffice: String? = "",
    val publication: String? = "",
    val shortUrl: String? = "",
    val shouldHideAdverts: String? = "",
    val showInRelatedContent: String? = "",
    val thumbnail: String? = "",
    val legallySensitive: String? = "",
    val lang: String? = "",
    val isLive: String? = "",
    val bodyText: String? = "",
    val charCount: String? = "",
    val shouldHideReaderRevenue: String? = "",
    val showAffiliateLinks: String? = "",
    val bylineHtml: String? = ""
)