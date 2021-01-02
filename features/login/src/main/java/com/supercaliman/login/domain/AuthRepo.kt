package com.supercaliman.login.domain

import com.google.firebase.auth.UserProfileChangeRequest
import com.supercaliman.core.domain.dto.User

interface AuthRepo {

    suspend fun signIn(email: String, password: String): User?

    /**
     * if user is already log retrive in memory user, thanks Firebase
     */
    fun alreadyLogin(): User?

    fun logout()

    suspend fun newUser(username: String, email: String, password: String): User?

    suspend fun updateProfile(profileUpdate: UserProfileChangeRequest): User?

    suspend fun sendVerificationEmail(): Boolean
}