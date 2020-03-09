package org.hib.socialcv.ui.model

class EventModel(
    val id: Int,
    val community_id: Int,
    val name: String,
    val hours: Int,
    val description: String,
    val date: String,
    val image_url: String,
    var attendeesList: List<MembersModel> = listOf(),
    val location: String
)