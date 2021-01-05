package com.example.compose.domain.useCase


import com.supercaliman.core.domain.Repository
import com.supercaliman.core.domain.dto.User
import javax.inject.Inject

class UserOperationsUseCase @Inject constructor(
    private var repo: Repository,
) {

    fun logout() {
        repo.logout()
    }

    fun getUser(): User? = repo.getUser()


}