package org.rohk.humanityinbusiness.http

import org.rohk.humanityinbusiness.http.model.RequestLoginModel
import org.rohk.humanityinbusiness.http.model.RequestRegisterModel
import org.rohk.humanityinbusiness.http.model.RequestSDGModel
import org.rohk.humanityinbusiness.http.model.ResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APIServiceInterface {

    @POST("register")
    abstract fun register(@Body request: RequestRegisterModel): Call<ResponseModel>

    @POST("login")
    abstract fun login(@Body request: RequestLoginModel): Call<ResponseModel>


    @POST("sdg")
    abstract fun sdg(@Body request: RequestSDGModel): Call<ResponseModel>

}