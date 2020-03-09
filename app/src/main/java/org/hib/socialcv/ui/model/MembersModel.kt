package org.hib.socialcv.ui.model

data class MembersModel(
    val user_Id: Int,
    val full_name: String,
    val title: String,
    val company_name: String,
    val image_url: String,
    val team_id: Int,
    val project_id: Int,
    val project_name: String,
    val project_image_url: String,
    val project_owner: String,
    val project_description: String
)