package com.supercaliman.core.domain

import com.supercaliman.core.domain.dto.User

interface LocalRepository {


    fun saveUser(user: User): Boolean

    fun retriveSavedUser(): User?

    fun deleteUser()
}