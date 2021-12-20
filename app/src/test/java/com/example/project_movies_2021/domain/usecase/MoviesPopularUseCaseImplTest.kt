
package com.example.project_movies_2021.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.project_movies_2021.commons.ApiResponse
import com.example.project_movies_2021.data.datasource.MoviesAPI
import com.example.project_movies_2021.data.remote.MoviesPopularResponse
import com.example.project_movies_2021.data.remote.Result
import com.example.project_movies_2021.presentation.ui.viewmodel.MainViewModel
import com.nhaarman.mockitokotlin2.whenever
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class MoviesPopularUseCaseImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    private val observer = mockk<Observer<ApiResponse<MoviesPopularResponse>>>()
    private val slot = slot<ApiResponse<MoviesPopularResponse>>()
    private val state = arrayListOf<ApiResponse<MoviesPopularResponse>>()


    @Mock
    lateinit var moviesUseCase: MoviesPopularUseCase
    private val uiScheduler = Schedulers.trampoline()
    private val ioScheduler = Schedulers.trampoline()

    private lateinit var response: MoviesPopularResponse

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(moviesUseCase, uiScheduler, ioScheduler)
        viewModel.popularMovies.observeForever(observer)

        response = MoviesPopularResponse(
            1,
            listOf(
                Result(
                    true,
                    "",
                    listOf(1,2,3),
                    580489,
                    "en",
                    "Venom: Let There Be Carnage",
                    "After finding a host body in investigative reporter Eddie Brock, the alien symbiote must face a new enemy, Carnage, the alter ego of serial killer Cletus Kasady",
                    8774.196,
                    "/rjkmN1dniUHVYAtwuV3Tji7FsDO.jpg",
                    "2021-09-30",
                    "Venom: Let There Be Carnage",
                    false,
                    7.2,
                    4596
                )
            ),
            10,
            20)
    }

    @Test
    fun `verify success status of the api`(){

        val successObservable = Observable.just(response)
        whenever(moviesUseCase.invoke(MoviesAPI.API_KEY)).thenReturn(successObservable)

        every { observer.onChanged(capture(slot)) } answers {
            state.add(slot.captured)
        }

        viewModel.getPopularMovies()

        assertEquals(2, state.size)
        assertEquals(ApiResponse.Loading, state[0])
        assertEquals(ApiResponse.Success(response), state[1])

        assertEquals(ApiResponse.Success(response), slot.captured)

        val response = viewModel.popularMovies.value
        if(response is ApiResponse.Success){
            assertEquals(response.data.page, 1)
            assertEquals(response.data.results.first().video, false)
            assertEquals(response.data.results.first().adult, true)
            assertEquals(response.data.results.first().id, 580489)
            assertEquals(response.data.results.first().original_language, "en")
            assertEquals(response.data.results.first().original_title, "Venom: Let There Be Carnage")
            assertEquals(response.data.results.first().release_date, "2021-09-30")
            assertEquals(response.data.results.first().vote_count, 4596)
            assertEquals(response.data.total_pages, 10)
            assertEquals(response.data.total_results, 20)

        }

    }

    @Test(expected = NullPointerException::class)
    fun showErrorNullPointerException(){
        val exception = NullPointerException()
        whenever(moviesUseCase.invoke(MoviesAPI.API_KEY)).thenThrow(exception)
        every { observer.onChanged(capture(slot)) } answers {
            state.add(slot.captured)
        }
        viewModel.getPopularMovies()
    }

    @Test(expected = IllegalArgumentException::class)
    fun showErrorIllegalArgumentException(){
        val exception = IllegalArgumentException()
        whenever(moviesUseCase.invoke(MoviesAPI.API_KEY)).thenThrow(exception)
        every { observer.onChanged(capture(slot)) } answers {
            state.add(slot.captured)
        }
        viewModel.getPopularMovies()
    }

}