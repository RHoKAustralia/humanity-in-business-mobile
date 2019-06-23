package org.rohk.humanityinbusiness.http

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.rohk.humanityinbusiness.http.model.RequestLoginModel
import org.rohk.humanityinbusiness.http.model.RequestRegisterModel
import org.rohk.humanityinbusiness.http.model.RequestSDGModel
import org.rohk.humanityinbusiness.http.model.ResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
            .baseUrl("http://ec2-18-191-57-240.us-east-2.compute.amazonaws.com:8080/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        return builder.client(OkHttpClient.Builder().build()).build()
    }

    fun register(
        request: RequestRegisterModel,
        callback: Callback<ResponseModel>
    ) {
        val call: Call<ResponseModel> = getService().register(request)
        call.enqueue(callback)
    }

    fun login(
        request: RequestLoginModel,
        callback: Callback<ResponseModel>
    ) {
        val call: Call<ResponseModel> = getService().login(request)
        call.enqueue(callback)
    }

    fun sendGoalsSelection(
        request: RequestSDGModel,
        callback: Callback<ResponseModel>
    ) {
        val call: Call<ResponseModel> = getService().sdg(request)
        call.enqueue(callback)
    }
}