package com.example.randomusersapp.view

import com.example.randomusersapp.domain.model.Coordinates
import com.example.randomusersapp.domain.model.Dob
import com.example.randomusersapp.domain.model.Id
import com.example.randomusersapp.domain.model.Info
import com.example.randomusersapp.domain.model.Location
import com.example.randomusersapp.domain.model.Login
import com.example.randomusersapp.domain.model.Name
import com.example.randomusersapp.domain.model.Picture
import com.example.randomusersapp.domain.model.RandomUsersResponse
import com.example.randomusersapp.domain.model.Registered
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.example.randomusersapp.domain.model.Result
import com.example.randomusersapp.domain.model.Street
import com.example.randomusersapp.domain.model.Timezone
import com.example.randomusersapp.domain.repository.UserRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class UserListViewModelTest {

    private val userRepository: UserRepository = mock()
    private lateinit var viewModel: UserListViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = UserListViewModel(userRepository)
    }

    fun mockRandomUsersResponse(): RandomUsersResponse {
        return RandomUsersResponse(
            info = Info(
                seed = "abc123",
                results = 10,
                page = 1,
                version = "1.0"
            ),
            results = listOf(mockResult())
        )
    }

    fun mockResult(): Result {
        return Result(
            cell = "123-456-7890",
            dob = Dob(30, "1990-01-01"),
            email = "test@example.com",
            gender = "male",
            id = Id("SSN", "123-45-6789"),
            location = Location("123 Main St", coordinates = Coordinates("",""), "New York", "NY", "USA", street = Street("xxxxxx", 230), timezone = Timezone("","")),
            login = Login("username123", "password", "salt", "md5", "sha1", "sha256", ""),
            name = Name("John", "Doe", "Mr"),
            nat = "US",
            phone = "987-654-3210",
            picture = Picture("large.jpg", "medium.jpg", "thumbnail.jpg"),
            registered = Registered(10, "2010-01-01")
        )
    }

    @Test
    fun `getUsers should update uiState with userList on success`() = runTest {
        val mockResponse = mockRandomUsersResponse()
        whenever(userRepository.getUserList(any())).thenReturn(retrofit2.Response.success(mockResponse))

        viewModel.getUsers(1)
        testDispatcher.scheduler.advanceUntilIdle() // Ensure coroutine execution

        assertEquals(mockResponse.results, viewModel.uiState.value.userList)
    }

    @Test
    fun `getUsers should update uiState with errorMessage on failure`() = runTest {

        whenever(userRepository.getUserList(any())).thenThrow(RuntimeException("Network Error"))

        viewModel.getUsers(1)

        assertNull(viewModel.uiState.value.userList)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}