package com.bandymoot.fingerprint.app.ui.screen.enroll_user

import app.cash.turbine.test
import com.bandymoot.fingerprint.app.data.repository.RepositoryResult
import com.bandymoot.fingerprint.app.data.socket.SocketEnrollmentStep
import com.bandymoot.fingerprint.app.data.socket.SocketEvent
import com.bandymoot.fingerprint.app.data.socket.SocketManager
import com.bandymoot.fingerprint.app.data.socket.SocketTopicEnroll
import com.bandymoot.fingerprint.app.domain.repository.DeviceRepository
import com.bandymoot.fingerprint.app.domain.usecase.EnrollUserUseCase
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.event.UserEnrollScreenEvent
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.state.EnrollmentScreenState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserEnrollmentViewModelTest {

    // Set the Main dispatcher to a test dispatcher

    private val testDispatcher = UnconfinedTestDispatcher()

    // Mock Dependencies
    private val enrollUserUseCase: EnrollUserUseCase = mockk()
    private val deviceRepository: DeviceRepository = mockk()

    // ViewModel instance
    private lateinit var viewModel: UserEnrollmentViewModel

    // Mock Socket Event Flow
    private val socketEventsFlow = MutableSharedFlow<SocketEvent>()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // Mock Static SocketManager
        mockkObject(SocketManager)
        every { SocketManager.socketEvent } returns socketEventsFlow
        every { SocketManager.hasActiveConnection()} returns true

        // Default Mock for Device Repository (Initial Init block)
        coEvery { deviceRepository.observeDeviceByCurrentManager() } returns flowOf(
            RepositoryResult.Success(emptyList())
        )

        viewModel = UserEnrollmentViewModel(enrollUserUseCase, deviceRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `successful enrollment flow transitions correctly`() = runTest {
        viewModel.uiState.test {

            // 1.Initial State
            var state = awaitItem()
            println("\nInitial State: SOCKET::${state.enrollmentSocketState} \nScreenState::${state.enrollmentScreenState}")
            assert(state.enrollmentScreenState is EnrollmentScreenState.IDLE)

            // 2. Start Enrollment Event
            viewModel.onEvent(UserEnrollScreenEvent.StartEnrollment)

            // Show move to Connecting -> then automatically to UserInput (since socket is connected)
            state = expectMostRecentItem()
            println("\nInitial State: SOCKET::${state.enrollmentSocketState} \nScreenState::${state.enrollmentScreenState}")
            assert(state.enrollmentScreenState is EnrollmentScreenState.UserInfoInput)

            // 3. Submit User Info
            coEvery { enrollUserUseCase(any()) } returns RepositoryResult.Success(data = true)
            viewModel.onEvent(UserEnrollScreenEvent.ValidateUserInfoAndStartBiometric)
            state = expectMostRecentItem()
            println("\nInitial State: SOCKET::${state.enrollmentSocketState} \nScreenState::${state.enrollmentScreenState}")
            assert(state.enrollmentScreenState is EnrollmentScreenState.WaitForDevice)

            // 4. Simulate Socket Event
            socketEventsFlow.emit(SocketEvent.EnrollProgress(
                SocketTopicEnroll("dev_1", SocketEnrollmentStep.Start, "Started")
            ))
            assert(awaitItem().enrollmentScreenState is EnrollmentScreenState.EnrollmentStarted)

            socketEventsFlow.emit(SocketEvent.EnrollProgress(
                SocketTopicEnroll("dev_1", SocketEnrollmentStep.CheckDuplicateFinger, "Check Duplicate")
            ))
            assert(awaitItem().enrollmentScreenState is EnrollmentScreenState.CheckDuplicateFinger)
        }
    }
}