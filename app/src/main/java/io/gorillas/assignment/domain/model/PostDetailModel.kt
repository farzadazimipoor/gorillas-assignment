package io.gorillas.assignment.domain.model

data class PostDetailModel(
    val id: String,
    val title: String,
    val body: String,
    val user: UserModel
)