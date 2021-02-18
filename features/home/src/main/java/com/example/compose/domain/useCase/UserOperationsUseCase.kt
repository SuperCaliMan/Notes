package com.example.compose.domain.useCase


import com.supercaliman.core.domain.LocalRepository
import com.supercaliman.core.domain.dto.User
import javax.inject.Inject

class UserOperationsUseCase @Inject constructor(
    private var localDataSource: LocalRepository,
) {

    suspend fun getUser(): User? = localDataSource.getSavedUser()
}