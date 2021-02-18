package com.supercaliman.core.domain

import com.supercaliman.core.domain.dto.User

interface LocalRepository {


    suspend fun saveUser(user: User)

    suspend fun getSavedUser(): User?

    suspend fun deleteUser()
}