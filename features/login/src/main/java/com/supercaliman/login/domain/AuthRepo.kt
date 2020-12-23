package com.supercaliman.login.domain

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

interface AuthRepo {

    suspend fun signIn(email: String, password: String): FirebaseUser?

    /**
     * if user is already log retrive in memory user, thanks Firebase
     */
    fun alreadyLogin(): FirebaseUser?

    fun logout()

    suspend fun newUser(username: String, email: String, password: String): FirebaseUser?

    suspend fun updateProfile(profileUpdate: UserProfileChangeRequest): FirebaseUser?

    suspend fun sendVerificationEmail(): Boolean
}