package com.bandymoot.fingerprint

import com.bandymoot.fingerprint.app.data.remote.api.ApiServices
import com.bandymoot.fingerprint.app.data.repository.UserRepositoryImpl
import com.bandymoot.fingerprint.app.domain.repository.UserRepository
import com.bandymoot.fingerprint.app.utils.AppConstant
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.fail

class UserRepositoryIntegrationTest {

    private lateinit var apiServices: ApiServices
    private lateinit var repository: UserRepository

    @Before
    fun setup() {
        val retrofit = Retrofit.Builder()
            .baseUrl(AppConstant.BASE_URL) // REAL BASE URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiServices = retrofit.create(ApiServices::class.java)
        repository = UserRepositoryImpl(apiServices)
    }

    @Test
    fun getAllUsers_fromRealApi() = runBlocking {
        val result = repository.getAllUsers()

        if (result.isSuccess) {
            val users = result.getOrNull()
            println("Users: $users")
            println("Users count: ${users?.size}")
            assertTrue(!users.isNullOrEmpty())
        } else {
            val error = result.exceptionOrNull()
            fail("API failed with error: ${error?.message}")
        }
    }
}
