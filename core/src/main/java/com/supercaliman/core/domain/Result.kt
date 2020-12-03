package com.supercaliman.core.domain

/**
 * A generic class to encapsulate data status
 * @param T any Data
 * @author Alberto Caliman
 */
sealed class Result<out T> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when(this){
            is Success -> "Success: ${data.toString()}"
            is Error -> "Error: ${exception.message}"
            is Loading -> "Loading"
        }
    }
}