package org.rohk.humanityinbusiness.http

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.rohk.humanityinbusiness.http.model.*
import org.rohk.humanityinbusiness.ui.viewmodel.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ServiceAPI {

    lateinit var serviceInterface: APIServiceInterface

    fun getService(): APIServiceInterface {
        // if (serviceInterface == null) {
        serviceInterface = getClient().create(APIServiceInterface::class.java)
        // }
        return serviceInterface
    }

    fun getClient(): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl("https://humanity-in-business.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))

        val okHttp = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttp.addInterceptor(interceptor)

        return builder.client(okHttp.build()).build()
    }

    fun getAllCompanies(
        callback: Callback<List<CompanyModel>>
    ) {
        val call: Call<List<CompanyModel>> = getService().getAllCompanies()
        call.enqueue(callback)
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

    fun getAllSDGGoals(callback: Callback<List<GoalSelectionModel>>) {
        val call: Call<List<GoalSelectionModel>> = getService().getAllSDG()
        call.enqueue(callback)
    }

    fun getSDGById(sdgId: String, callback: Callback<List<GoalSelectionModel>>) {
        val call: Call<List<GoalSelectionModel>> = getService().getSDG(sdgId)
        call.enqueue(callback)
    }

    fun sendGoalsSelection(
        userId: String,
        request: RequestSDGModel,
        callback: Callback<ResponseModel>
    ) {
        val call: Call<ResponseModel> = getService().addSDG(userId, request)
        call.enqueue(callback)
    }

    fun getProfile(userId: String, callback: Callback<ProfileModel>) {
        val call: Call<ProfileModel> = getService().getProfile(userId)
        call.enqueue(callback)
    }


    fun getUpcomingChallenges(
        callback: Callback<List<ChallengeModel>>
    ) {
        val call: Call<List<ChallengeModel>> = getService().getUpcomingChallenges()
        call.enqueue(callback)
    }

    fun getChallenge(
        challengeId: Int,
        callback: Callback<ChallengeModel>
    ) {
        val call: Call<ChallengeModel> = getService().getChallenge(challengeId)
        call.enqueue(callback)
    }

    fun getUpcomingChallengesForUser(
        userId: String,
        callback: Callback<List<ChallengeModel>>
    ) {
        val call: Call<List<ChallengeModel>> = getService().getUpcomingChallengesByUser(userId)
        call.enqueue(callback)
    }

    fun getCompletedChallenges(
        userId: String,
        callback: Callback<List<ChallengeModel>>
    ) {
        val call: Call<List<ChallengeModel>> = getService().getCompletedChallenges(userId)
        call.enqueue(callback)
    }

    fun addChallenge(
        request: RequestAddChallengeModel,
        callback: Callback<RegisterResponseModel>
    ) {
        val call: Call<RegisterResponseModel> = getService().addChallengeToUser(request)
        call.enqueue(callback)
    }

    fun getCompanyProfile(companyId: String, callback: Callback<CompanyModel>) {
        val call: Call<CompanyModel> = getService().getCompanyById(companyId)
        call.enqueue(callback)
    }

    fun getLeaderBoard(companyId: String, callback: Callback<List<LeaderBoardModel>>) {
        val call: Call<List<LeaderBoardModel>> = getService().getCompanyLeaderBoard(companyId)
        call.enqueue(callback)
    }

    fun getSDGsForCompany(companyId: String, callback: Callback<List<GoalSelectionModel>>) {
        val call: Call<List<GoalSelectionModel>> = getService().getCompanySDGs(companyId)
        call.enqueue(callback)
    }

    fun getAllEvents(
        communityId: Int,
        callback: Callback<List<EventModel>>
    ) {
        val call: Call<List<EventModel>> = getService().getAllEvents(communityId)
        call.enqueue(callback)
    }

    fun getCommunityProfile(communityId: Int, callback: Callback<CommunityModel>) {
        val call: Call<CommunityModel> = getService().getCommunityById(communityId)
        call.enqueue(callback)
    }
}