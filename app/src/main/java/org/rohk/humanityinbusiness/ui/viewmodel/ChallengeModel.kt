package org.rohk.humanityinbusiness.ui.viewmodel

class ChallengeModel(
    val id: Int,
    val title: String,
    val description: String,
    val location: String,
    val challenge_date: String,
    val points: Int,
    val image_url: String,
    val badges: List<BadgeModel>
)