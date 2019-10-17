package org.rohk.humanityinbusiness.http

import org.rohk.humanityinbusiness.http.model.RegisterResponseModel
import org.rohk.humanityinbusiness.http.model.RequestJoinTeamModel
import org.rohk.humanityinbusiness.http.model.RequestLoginModel
import org.rohk.humanityinbusiness.http.model.RequestRegisterModel
import org.rohk.humanityinbusiness.ui.viewmodel.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIServiceInterface {

    @POST("register")
    fun register(@Body request: RequestRegisterModel): Call<RegisterResponseModel>

    @POST("login")
    fun login(@Body request: RequestLoginModel): Call<RegisterResponseModel>

    // Profile Page
    @GET("profile/{userId}")
    fun getProfile(@Path("userId") userId: String): Call<ProfileModel>

    // Communuties Endpoint

    @GET("communities")
    fun getAllCommunuties(): Call<List<CommunityModel>>

    @GET("communities/{communityId}")
    fun getCommunityById(@Path("communityId") communityId: Int): Call<CommunityModel>


    // Events Endpoint

    @GET("communities/{communityId}/events")
    fun getAllEvents(@Path("communityId") communityId: String): Call<List<EventModel>>

    @GET("events/{eventId}/teams")
    fun getTeamsByEventId(@Path("eventId") eventId: String): Call<List<TeamModel>>

    @POST("/teams/{teamId}/members")
    fun joinTeam(@Path("teamId") teamId: Int, @Body request: RequestJoinTeamModel): Call<RegisterResponseModel>

    @GET("communities/{communityId}/leaderboard")
    fun getLeaderboard(@Path("communityId") communityId: String): Call<List<UserLeaderBoardModel>>
}