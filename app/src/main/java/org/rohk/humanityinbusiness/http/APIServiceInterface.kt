package org.rohk.humanityinbusiness.http

import org.rohk.humanityinbusiness.http.model.*
import org.rohk.humanityinbusiness.ui.viewmodel.*
import retrofit2.Call
import retrofit2.http.*

interface APIServiceInterface {

    @POST("register")
    fun register(@Body request: RequestRegisterModel): Call<RegisterResponseModel> // TODO add skill

    @POST("login")
    fun login(@Body request: RequestLoginModel): Call<RegisterResponseModel>

    // Companies
    @GET("company")
    fun getAllCompanies(): Call<List<CompanyModel>>

    @GET("company/{companyId}")
    fun getCompanyById(@Path("companyId") companyId: String): Call<CompanyModel>

    @Headers("Content-Type: application/json")
    @POST("company")
    fun createNewCompany(@Body request: RequestCompanyModel): Call<RegisterResponseModel> // TODO where is this going to be used

    @GET("leaderboard/company/{companyId}")
    fun getCompanyLeaderBoard(@Path("companyId") companyId: String): Call<List<LeaderBoardModel>>

    @GET("badges/company/{companyId}")
    fun getCompanyBadges(@Path("id") id: String): Call<List<BadgeModel>>

    @GET("company/{companyId}/sdg")
    fun getCompanySDGs(@Path("companyId") companyId: String): Call<List<GoalSelectionModel>>

    // Profile Page
    @GET("profile/{userId}")
    fun getProfile(@Path("userId") userId: String): Call<ProfileModel>

    // SDG Endpoints

    @GET("sdg/")
    fun getAllSDG(): Call<List<GoalSelectionModel>>

    @GET("sdg/{sdgId}")
    fun getSDG(@Path("sdgId") sdgId: String): Call<List<GoalSelectionModel>>

    @Headers("Content-Type: application/json")
    @POST("/user/{userId}/sdg")
    fun addSDG(@Path("userId") userId: String, @Body request: RequestSDGModel): Call<ResponseModel>

    // Challenge Endpoint

    @GET("challenges/upcoming")
    fun getUpcomingChallenges(): Call<List<ChallengeModel>>

    @POST("/addChallengeToUser")
    fun addChallengeToUser(@Body request: RequestAddChallengeModel): Call<RegisterResponseModel>

    @GET("event/{challengeId}")
    fun getChallenge(@Path("challengeId") challengeId: Int): Call<ChallengeModel>

    @GET("challenges/upcoming/{userId}")
    fun getUpcomingChallengesByUser(@Path("userId") userId: String): Call<List<ChallengeModel>>

    @GET("challenges/completed/{userId}")
    fun getCompletedChallenges(@Path("userId") userId: String): Call<List<ChallengeModel>>

    // Skills Endpoint

    @GET("skills")
    fun getAllSkills(): Call<ResponseModel> // TODO add in register screen

    // Communuties Endpoint

    @GET("communities")
    fun getAllCommunuties(): Call<List<CommunityModel>>

    @GET("communities/{communityId}")
    fun getCommunityById(@Path("communityId") communityId: Int): Call<CommunityModel>


    // Events Endpoint

    @GET("communities/{communityId}/events")
    fun getAllEvents(@Path("communityId") communityId: Int): Call<List<EventModel>>

    @GET("events/{eventId}/teams")
    fun getTeamsByEventId(@Path("eventId") communityId: Int): Call<List<TeamModel>>

    @POST("/teams/{teamId}/members ")
    fun joinTeam(@Path("teamId") teamId: Int, @Body request: RequestJoinTeamModel): Call<RegisterResponseModel>


}