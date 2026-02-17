package com.bandymoot.fingerprint.app.data.remote.api

import com.bandymoot.fingerprint.app.data.dto.DeviceListResponseDto
import com.bandymoot.fingerprint.app.data.dto.EnrollNewDeviceRequest
import com.bandymoot.fingerprint.app.data.dto.EnrollNewDeviceResponse
import com.bandymoot.fingerprint.app.data.dto.FetchAttendanceResponseDto
import com.bandymoot.fingerprint.app.data.dto.FetchUsersResponseDto
import com.bandymoot.fingerprint.app.data.dto.LoginRequest
import com.bandymoot.fingerprint.app.data.dto.LoginResponseDto
import com.bandymoot.fingerprint.app.data.dto.ManagerDetailsResponseDto
import com.bandymoot.fingerprint.app.data.dto.UserEnrollRequestDto
import com.bandymoot.fingerprint.app.data.dto.UserEnrollResponseDto
import com.bandymoot.fingerprint.app.data.dto.UserResponseDto
import com.bandymoot.fingerprint.app.utils.AppConstant
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServices {

    @POST(AppConstant.LOGIN_USER_API)
    suspend fun loginUser(
        @Body request: LoginRequest
    ): Response<LoginResponseDto>

    @GET(AppConstant.GET_ALL_ORGANISATION_API)
    suspend fun getOrganizationDetail(
        @Header("Authorization") token: String
    ): Response<Unit>

    @GET(AppConstant.GET_ALL_MANAGER_API)
    suspend fun getManagerDetail(
        @Header("Authorization") token: String
    ): Response<ManagerDetailsResponseDto>

    @POST(AppConstant.START_USER_ENROLLMENT)
    suspend fun startEnrollment(
        @Header("Authorization") token: String,
        @Body requestBody: UserEnrollRequestDto
    ): Response<UserEnrollResponseDto>

    @GET(AppConstant.GET_ALL_USERS_API)
    suspend fun getAllUsers(
        @Header("Authorization") token: String
    ): Response<FetchUsersResponseDto>

    @GET(AppConstant.GET_USER_BY_ID_API + "/{id}")
    suspend fun getUserById(
        @Path("id") userId: Int
    ): Response<UserResponseDto>

    @GET(AppConstant.GET_ALL_DEVICES_API)
    suspend fun getAllDevices(
        @Header("Authorization") token: String
    ): Response<DeviceListResponseDto>

    @GET(AppConstant.GET_ALL_ATTENDANCE_API + "/{startDate}/{endDate}")
    suspend fun getAttendanceDataByDate(
        @Path("startDate") startDate: String,
        @Path("endDate") endDate: String
    ): Response<FetchAttendanceResponseDto>

    @POST(AppConstant.ENROLL_NEW_DEVICE_API)
    suspend fun enrollNewDevice(
        @Header("Authorization") token: String,
        @Body requestBody: EnrollNewDeviceRequest
    ): Response<EnrollNewDeviceResponse>
}