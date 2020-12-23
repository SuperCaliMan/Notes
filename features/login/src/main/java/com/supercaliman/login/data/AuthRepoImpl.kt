package com.supercaliman.login.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.supercaliman.login.domain.AuthRepo
import javax.security.auth.login.LoginException
import kotlin.coroutines.suspendCoroutine

class AuthRepoImpl constructor(private val auth: FirebaseAuth) : AuthRepo {

    override fun alreadyLogin(): FirebaseUser? {
        return if (auth.currentUser != null) {
            auth.currentUser!!
        } else {
            null
        }
    }

    override suspend fun signIn(email: String, password: String): FirebaseUser? {
        if (auth.currentUser != null) {
            return auth.currentUser!!
        } else {
            return suspendCoroutine { cont ->
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            cont.resumeWith(Result.success(user!!))
                        }
                        if (!task.isSuccessful) {
                            cont.resumeWith(Result.failure(task.exception!!))
                        }
                    }
            }
        }
    }

    override fun logout() {
        auth.signOut()
    }

    override suspend fun newUser(username: String, email: String, password: String): FirebaseUser? {
        return suspendCoroutine { cont ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        auth.currentUser!!.sendEmailVerification()
                        logout() //logout user because i want test if user's email is verified
                        cont.resumeWith(Result.success(user!!))
                    } else {
                        cont.resumeWith(Result.failure(task.exception!!))
                    }

                }
        }
    }

    override suspend fun updateProfile(profileUpdate: UserProfileChangeRequest): FirebaseUser? {
        return suspendCoroutine {
            auth.currentUser!!.updateProfile(profileUpdate)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        it.resumeWith(Result.failure(Exception("failed to set user profile name")))
                    } else {
                        it.resumeWith(Result.success(auth.currentUser))
                    }

                }
        }
    }

    override suspend fun sendVerificationEmail(): Boolean {
        return suspendCoroutine { cont ->
            auth.currentUser?.let { user ->
                user.sendEmailVerification()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            cont.resumeWith(Result.success(true))
                        } else {
                            cont.resumeWith(Result.failure(LoginException("Ops i don't send verification email")))
                        }
                    }
            }
        }
    }
}