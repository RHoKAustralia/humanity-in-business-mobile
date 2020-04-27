package org.hib.socialcv.http

import org.hib.socialcv.http.model.*
import org.hib.socialcv.ui.model.*
import retrofit2.Call
import retrofit2.http.*

interface APIServiceInterface {

    @POST("register")
    fun register(@Body request: RequestRegisterModel): Call<ResponseModel>

    @POST("users/{userId}/hib-details")
    fun aboutYou(@Path("userId") userId: String, @Body request: RequestAboutYouModel): Call<ResponseModel>

    @POST("login")
    fun login(@Body request: RequestLoginModel): Call<ResponseModel>

    // Profile Page
    @GET("users/{userId}/profile")
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

    @GET("events/teams/{teamId}") // TODO
    fun getTeamById(@Path("teamId") teamId: String): Call<TeamModel>

    @GET("events/{eventId}/members")
    fun getAttendeesByEventId(@Path("eventId") eventId: String): Call<List<MembersModel>>


    @POST("/teams/{teamId}/members")
    fun joinTeam(@Path("teamId") teamId: Int, @Body request: RequestJoinTeamModel): Call<Unit>

    @GET("communities/{communityId}/leaderboard")
    fun getLeaderboard(@Path("communityId") communityId: String): Call<List<UserLeaderBoardModel>>

    @PATCH("/users/{userId}")
    fun updateAvatar(@Path("userId") userId: String, @Body request: RequestAvatarModel): Call<ResponseModel>

}