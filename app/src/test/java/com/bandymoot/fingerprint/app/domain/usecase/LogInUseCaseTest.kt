package com.bandymoot.fingerprint.app.domain.usecase

import com.bandymoot.fingerprint.app.domain.model.AppError
import com.bandymoot.fingerprint.app.domain.model.LoginResult
import com.bandymoot.fingerprint.app.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class LogInUseCaseTest {

    // Create Fake Repository
    private val repository: AuthRepository = mockk()

    // Inject it manually into the UseCase
    private val useCase = LogInUseCase(repository)

    // we use 'runtest' and 'coEvery' which allow to run the suspend function
    @Test
    fun `when invoke is called, It should fetch data from repository`() = runTest {

        // Arrange - Success Result
        val mockLoginResult = LoginResult.Success(isAuthenticated = true)
        coEvery { repository.loginUser(userEmail = "afnan@gmail.com", password = "12345678") } returns mockLoginResult

        // Arrange - Failed Result
        val mockLoginFailedResult = LoginResult.Failed(AppError.Unknown("Something went wrong!?"))
        coEvery { repository.loginUser(userEmail = "afnan@gmail.com", password = "87654321") } returns mockLoginFailedResult

        // Act
        val result1 = useCase.invoke(email = "afnan@gmail.com", password = "12345678")
        val result2 = useCase.invoke(email = "afnan@gmail.com", password = "87654321")

        // ASSERT
        assertEquals(true, (result1 as LoginResult.Success).isAuthenticated)
        assertEquals(AppError.Unknown("Something went wrong!?").message, (result2 as LoginResult.Failed).error.message)

        // Provide Wrong Email and Wrong password
        val result3 = useCase.invoke(email = "afnan", password = "12345678")
        val result4 = useCase.invoke(email = "afnan@gmail.com", password = "1234")
        val result5 = useCase.invoke(email = "afnan", password = "1234")

        assertTrue(result3 is LoginResult.Failed)
        assertTrue(result4 is LoginResult.Failed)
        assertTrue(result5 is LoginResult.Failed)

        // ++ Verify the repository was actually called exactly once
        // coVerify(exactly = 1) { repository.loginUser(any(), any()) }
    }
}