package io.gorillas.assignment.presentation.ui.main.posts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import io.gorillas.assignment.domain.repository.PostRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PostsViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {
    private var _query: MutableLiveData<String> = MutableLiveData()

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val challenges = flowOf(clearListCh.receiveAsFlow().map { PagingData.empty() }, _query
        .asFlow()
        .flatMapLatest { postRepository.getAllPosts(it, 10) }
        .cachedIn(viewModelScope)
    ).flattenMerge(2)

    private fun shouldShowPosts(userId: String) = _query.value != userId

    fun getPosts(query: String) {
        if (!shouldShowPosts(query)) return
        clearListCh.trySend(Unit).isSuccess
        _query.value = query
    }
}