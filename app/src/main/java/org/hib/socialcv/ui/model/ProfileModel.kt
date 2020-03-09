package org.hib.socialcv.ui.model

class ProfileModel(
    val id: Int,
    val full_name: String,
    val title: String,
    val image_url: String,
    val why_join_hib: String,
    val yearly_days_pledged: String,
    val contributed_hours: Int,
    val projects: List<ProjectModel>,
    val communities: List<CommunityModel>
)