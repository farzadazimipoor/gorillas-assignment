package io.gorillas.assignment.common.helpers

import io.gorillas.assignment.common.enums.LoadingStatus

data class Resource<out T>(val status: LoadingStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(LoadingStatus.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(LoadingStatus.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LoadingStatus.LOADING, data, null)
        }

        fun <T> unAuthorized(): Resource<T> {
            return Resource(LoadingStatus.UNAUTHORIZED, null, null)
        }
    }
}