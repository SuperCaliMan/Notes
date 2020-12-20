package com.supercaliman.login.domain

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

interface AuthRepo {

    suspend fun signIn(email: String, password: String): FirebaseUser?

    fun alreadyLogin(): FirebaseUser?

    fun logout()

    suspend fun newUser(username: String, email: String, password: String): FirebaseUser?

    suspend fun updateProfile(profileUpdate: UserProfileChangeRequest): Unit

    suspend fun sendVerificationEmail(): Boolean
}