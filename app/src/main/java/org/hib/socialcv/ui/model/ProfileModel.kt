package org.hib.socialcv.ui.model

data class ProfileModel(
    val id: Int,
    val full_name: String,
    val title: String,
    val image_url: String,
    val why_join_hib: String,
    val yearly_days_pledged: String,
    val yearly_donations_pledge: Int,
    val contributed_hours: Int,
    val projects: List<ProjectModel>,
    val communities: List<CommunityModel>,
    val company: Company
)

data class Company(
    val id: Int,
    val name: String,
    val url: String,
    val image_url: String
)