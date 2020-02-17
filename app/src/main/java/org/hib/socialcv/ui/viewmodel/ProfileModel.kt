package org.hib.socialcv.ui.viewmodel

class ProfileModel(
    val id: Int,
    val full_name: String,
    val title: String,
    val image_url: String,
    val hours: Int,
    val communities: List<CommunityModel>
)