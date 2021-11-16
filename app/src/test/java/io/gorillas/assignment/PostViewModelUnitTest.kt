package io.gorillas.assignment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.gorillas.assignment.common.helpers.Resource
import io.gorillas.assignment.domain.model.PostDetailModel
import io.gorillas.assignment.domain.model.UserModel
import io.gorillas.assignment.domain.repository.PostRepository
import io.gorillas.assignment.domain.use_case.post.GetPostUseCase
import io.gorillas.assignment.presentation.ui.main.postdetail.PostDetailViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PostViewModelUnitTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var postRepository: PostRepository

    @Mock
    private lateinit var postDetailObserver: Observer<Resource<PostDetailModel>>

    private lateinit var postUseCase: GetPostUseCase

    private lateinit var viewModel: PostDetailViewModel

    private val testPostId: String = "1"

    @Before
    fun setUp() {
        postUseCase = GetPostUseCase(postRepository)
        viewModel = PostDetailViewModel(postUseCase)
    }

    @Test
    fun `when fetching results ok then return a post model successfully`() {
        val fakeResult = PostDetailModel(
            id = "0",
            title = "Test Title",
            body = "Test Body",
            user = UserModel(
                name = "Test Name",
                username = "Test Username"
            )
        )
        testCoroutineRule.runBlockingTest {
            viewModel.postResult.observeForever(postDetailObserver)
            whenever(postRepository.getPost(testPostId)).thenAnswer {
                fakeResult
            }
            viewModel.getPost(testPostId)
            assertNotNull(viewModel.postResult.value)
            assertEquals(Resource.success(data = fakeResult), viewModel.postResult.value)
            assertEquals(Resource.success(data = fakeResult).data, viewModel.postResult.value?.data)
        }
    }

    @Test
    fun `when calling for results then return loading`() {
        testCoroutineRule.runBlockingTest {
            viewModel.postResult.observeForever(postDetailObserver)
            viewModel.getPost(testPostId)
            verify(postDetailObserver).onChanged(Resource.loading(data = null))
        }
    }

    @Test
    fun `when fetching results fails then return an error`() {
        val exception = mock(Exception::class.java)
        testCoroutineRule.runBlockingTest {
            viewModel.postResult.observeForever(postDetailObserver)
            whenever(postRepository.getPost(testPostId)).thenAnswer {
                exception
            }
            viewModel.getPost(testPostId)
            assertNotNull(viewModel.postResult.value)
            assertNotNull(viewModel.postResult.value?.message)
            assertEquals(null, viewModel.postResult.value?.data)
        }
    }

    @After
    fun tearDown() {
        viewModel.postResult.removeObserver(postDetailObserver)
    }
}