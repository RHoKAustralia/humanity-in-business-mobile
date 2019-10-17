package org.rohk.humanityinbusiness.http

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.rohk.humanityinbusiness.BuildConfig
import org.rohk.humanityinbusiness.http.model.RegisterResponseModel
import org.rohk.humanityinbusiness.http.model.RequestJoinTeamModel
import org.rohk.humanityinbusiness.http.model.RequestLoginModel
import org.rohk.humanityinbusiness.http.model.RequestRegisterModel
import org.rohk.humanityinbusiness.ui.viewmodel.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ServiceAPI {

    lateinit var serviceInterface: APIServiceInterface

    fun getService(): APIServiceInterface {
        serviceInterface = getClient().create(APIServiceInterface::class.java)
        return serviceInterface
    }

    fun getClient(): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl(BuildConfig.ROOT_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))

        val okHttp = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttp.addInterceptor(interceptor)

        return builder.client(okHttp.build()).build()
    }

    fun register(
        request: RequestRegisterModel,
        callback: Callback<RegisterResponseModel>
    ) {
        val call: Call<RegisterResponseModel> = getService().register(request)
        call.enqueue(callback)
    }

    fun login(
        request: RequestLoginModel,
        callback: Callback<RegisterResponseModel>
    ) {
        val call: Call<RegisterResponseModel> = getService().login(request)
        call.enqueue(callback)
    }

    fun getProfile(userId: String, callback: Callback<ProfileModel>) {
        val call: Call<ProfileModel> = getService().getProfile(userId)
        call.enqueue(callback)
    }

    fun getAllEvents(
        communityId: String,
        callback: Callback<List<EventModel>>
    ) {
        val call: Call<List<EventModel>> = getService().getAllEvents(communityId)
        call.enqueue(callback)
    }

    fun getCommunityProfile(communityId: Int, callback: Callback<CommunityModel>) {
        val call: Call<CommunityModel> = getService().getCommunityById(communityId)
        call.enqueue(callback)
    }

    fun getTeamsByEventId(
        eventId: String,
        callback: Callback<List<TeamModel>>
    ) {
        val call: Call<List<TeamModel>> = getService().getTeamsByEventId(eventId)
        call.enqueue(callback)
    }

    fun joinTeam(
        teamId: Int,
        request: RequestJoinTeamModel,
        callback: Callback<RegisterResponseModel>
    ) {
        val call: Call<RegisterResponseModel> = getService().joinTeam(teamId, request)
        call.enqueue(callback)
    }

    fun getAllCommunities(
        callback: Callback<List<CommunityModel>>
    ) {
        val call: Call<List<CommunityModel>> = getService().getAllCommunuties()
        call.enqueue(callback)
    }

    fun getLeaderboard(
        communityId: String,
        callback: Callback<List<UserLeaderBoardModel>>
    ) {
        val call: Call<List<UserLeaderBoardModel>> = getService().getLeaderboard(communityId)
        call.enqueue(callback)
    }
}