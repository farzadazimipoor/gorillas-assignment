package io.gorillas.assignment.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.gorillas.assignment.GetPostQuery
import io.gorillas.assignment.common.helpers.Resource
import io.gorillas.assignment.domain.use_case.post.GetPostUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase
) : ViewModel() {

    private val _postResult: MutableLiveData<Resource<GetPostQuery.Data?>> = MutableLiveData()

    var postResult: LiveData<Resource<GetPostQuery.Data?>> = _postResult

    fun getPost(id: String) = viewModelScope.launch {
        getPostUseCase.invoke(id).collect { value ->
            _postResult.value = value
        }
    }
}