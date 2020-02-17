package org.hib.socialcv.http

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.hib.socialcv.BuildConfig
import org.hib.socialcv.http.model.*
import org.hib.socialcv.ui.viewmodel.*
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
        callback: Callback<ResponseModel>
    ) {
        val call: Call<ResponseModel> = getService().register(request)
        call.enqueue(callback)
    }

    fun aboutYou(
        userId: String,
        request: RequestAboutYouModel,
        callback: Callback<ResponseModel>
    ) {
        val call: Call<ResponseModel> = getService().aboutYou(userId, request)
        call.enqueue(callback)
    }

    fun login(
        request: RequestLoginModel,
        callback: Callback<ResponseModel>
    ) {
        val call: Call<ResponseModel> = getService().login(request)
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

    fun getAtendeesByEventId(
        eventId: String,
        callback: Callback<List<MembersModel>>
    ) {
        val call: Call<List<MembersModel>> = getService().getAttendeesByEventId(eventId)
        call.enqueue(callback)
    }

    fun joinTeam(
        teamId: Int,
        request: RequestJoinTeamModel,
        callback: Callback<Unit>
    ) {

        val call: Call<Unit> = getService().joinTeam(teamId, request)
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

    fun updateAvatar(
        userId: String,
        request: RequestAvatarModel,
        callback: Callback<ResponseModel>
    ) {
        val call: Call<ResponseModel> = getService().updateAvatar(userId, request)
        call.enqueue(callback)
    }
}