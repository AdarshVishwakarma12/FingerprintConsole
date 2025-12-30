package com.example.figerprintconsole.app.data.remote.api

import com.example.figerprintconsole.app.data.dto.UserResponseDto
import com.example.figerprintconsole.app.data.dto.UsersResponseDto
import com.example.figerprintconsole.app.utils.AppConstant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

    @GET(AppConstant.GET_ALL_USERS_API)
    suspend fun getAllUsers(): Response<UsersResponseDto>

    @GET(AppConstant.GET_USER_BY_ID_API + "/{id}")
    suspend fun getUserById(
        @Path("id") userId: Int
    ): Response<UserResponseDto>

}