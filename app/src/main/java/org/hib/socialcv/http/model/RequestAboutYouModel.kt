package org.hib.socialcv.http.model

data class RequestAboutYouModel(
    val why_join_hib: String,
    val yearly_days_pledged: String,
    val yearly_donations_pledge: Int
)