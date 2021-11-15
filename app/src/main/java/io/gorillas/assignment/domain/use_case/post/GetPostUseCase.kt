package io.gorillas.assignment.domain.use_case.post

import io.gorillas.assignment.common.helpers.Resource
import io.gorillas.assignment.domain.model.PostDetailModel
import io.gorillas.assignment.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    operator fun invoke(id: String): Flow<Resource<PostDetailModel>> = flow {
        try {
            emit(Resource.loading(data = null))
            val data = postRepository.getPost(id)
            emit(Resource.success(data = data))
        } catch (e: Exception) {
            emit(Resource.error(msg = e.localizedMessage ?: "", data = null))
        }
    }
}