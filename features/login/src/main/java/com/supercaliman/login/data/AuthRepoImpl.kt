package com.supercaliman.login.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.supercaliman.core.domain.dto.User
import com.supercaliman.login.domain.AuthRepo
import com.supercaliman.login.domain.UserMapper
import javax.security.auth.login.LoginException
import kotlin.coroutines.suspendCoroutine

class AuthRepoImpl constructor(
    private val auth: FirebaseAuth,
    private val mapper: UserMapper
) : AuthRepo {


    override fun alreadyLogin(): User? {
        return if (auth.currentUser != null) {
            mapper.map(auth.currentUser!!)
        } else {
            null
        }
    }

    override suspend fun signIn(email: String, password: String): User? {
        if (auth.currentUser != null) {
            return mapper.map(auth.currentUser!!)
        } else {
            return suspendCoroutine { cont ->
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            cont.resumeWith(Result.success(mapper.map(user!!)))
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

    override suspend fun newUser(username: String, email: String, password: String): User? {
        return suspendCoroutine { cont ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        auth.currentUser!!.sendEmailVerification()
                        logout() //logout user because i want test if user's email is verified
                        cont.resumeWith(Result.success(mapper.map(user!!)))
                    } else {
                        cont.resumeWith(Result.failure(task.exception!!))
                    }

                }
        }
    }

    override suspend fun updateProfile(profileUpdate: UserProfileChangeRequest): User? {
        return suspendCoroutine {
            auth.currentUser!!.updateProfile(profileUpdate)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        it.resumeWith(Result.failure(Exception("failed to set user profile name")))
                    } else {
                        it.resumeWith(Result.success(mapper.map(auth.currentUser)))
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