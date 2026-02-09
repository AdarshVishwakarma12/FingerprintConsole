package com.bandymoot.fingerprint.app.data.remote.api

import com.bandymoot.fingerprint.app.data.dto.LoginRequest
import com.bandymoot.fingerprint.app.data.dto.LoginResponseDto
import com.bandymoot.fingerprint.app.data.dto.UserResponseDto
import com.bandymoot.fingerprint.app.data.dto.UsersResponseDto
import com.bandymoot.fingerprint.app.utils.AppConstant
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServices {

    @POST(AppConstant.LOGIN_USER_API)
    suspend fun loginUser(
        @Body request: LoginRequest
    ): Response<LoginResponseDto>

    @GET(AppConstant.GET_ALL_USERS_API)
    suspend fun getAllUsers(): Response<UsersResponseDto>

    @GET(AppConstant.GET_USER_BY_ID_API + "/{id}")
    suspend fun getUserById(
        @Path("id") userId: Int
    ): Response<UserResponseDto>

}