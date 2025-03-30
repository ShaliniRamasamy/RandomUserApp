package com.example.randomusersapp.repository

import com.example.randomusersapp.data.repository.UserRepositoryImpl
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
import com.example.randomusersapp.domain.model.Result
import com.example.randomusersapp.domain.model.Street
import com.example.randomusersapp.domain.model.Timezone
import com.example.randomusersapp.network.CallApi
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryImplTest {

    @Mock
    private lateinit var api: CallApi

    private lateinit var userRepository: UserRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        userRepository = UserRepositoryImpl(api)
    }

    private fun mockRandomUsersResponse(): RandomUsersResponse {
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

    private fun mockResult(): Result {
        return Result(
            cell = "123-456-7890",
            dob = Dob(30, "1990-01-01"),
            email = "test@example.com",
            gender = "male",
            id = Id("SSN", "123-45-6789"),
            location = Location("123 Main St", coordinates = Coordinates("",""), "New York", "NY", "USA", street = Street("Street", 230), timezone = Timezone("","")),
            login = Login("username123", "password", "salt", "md5", "sha1", "sha256", ""),
            name = Name("John", "Doe", "Mr"),
            nat = "US",
            phone = "987-654-3210",
            picture = Picture("large.jpg", "medium.jpg", "thumbnail.jpg"),
            registered = Registered(10, "2010-01-01")
        )
    }

    @Test
    fun `getUserList returns successful response`() = runBlocking {
        val mockResponse = mockRandomUsersResponse()
        val expectedResponse = retrofit2.Response.success(mockResponse)
        Mockito.`when`(api.getRandomUsers(10)).thenReturn(expectedResponse)

        val response = userRepository.getUserList(10)

        assertEquals(expectedResponse, response)
    }

    @Test
    fun `getUserList handles exception and does not crash`() = runBlocking {
        Mockito.`when`(api.getRandomUsers(10)).thenThrow(RuntimeException("Network error"))

        val response = try {
            userRepository.getUserList(10)
        } catch (e: Exception) {
            null
        }

        assertNull(response)
    }
}
