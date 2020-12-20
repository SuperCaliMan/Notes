package com.supercaliman.login.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.supercaliman.login.domain.AuthRepo
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.security.auth.login.LoginException

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
            return suspendCancellableCoroutine { cont ->
                // [START sign_in_with_email]
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Timber.d("signInWithEmail:success")
                            val user = auth.currentUser
                            cont.resumeWith(kotlin.Result.success(user!!))
                        } else {
                            // If sign in fails, display a message to the user.
                            Timber.w("signInWithEmail:failure ${task.exception}")
                            cont.cancel(task.exception!!)
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful) {
                            cont.cancel(task.exception!!)
                        }
                    }
            }
        }
        // [END sign_in_with_email]
    }

    override fun logout() {
        auth.signOut()
    }

    override suspend fun newUser(username: String, email: String, password: String): FirebaseUser? {
        val user: FirebaseUser? = suspendCancellableCoroutine { cont ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Timber.d("createUserWithEmail:success")
                        val user = auth.currentUser
                        auth.currentUser!!.sendEmailVerification()
                        cont.resumeWith(kotlin.Result.success(user!!))
                    } else {
                        // If sign in fails, display a message to the user.
                        Timber.w("createUserWithEmail:failure ${task.exception}")
                        cont.cancel(task.exception!!)
                    }

                }
        }
        updateProfile(userProfileChangeRequest {
            displayName = username
        })
        return user
    }

    override suspend fun updateProfile(profileUpdate: UserProfileChangeRequest) {
        return suspendCancellableCoroutine {
            auth.currentUser!!.updateProfile(profileUpdate)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        it.cancel(Exception("failed to set user profile name"))
                    }

                }
        }
    }

    override suspend fun sendVerificationEmail(): Boolean {
        return suspendCancellableCoroutine { cont ->
            auth.currentUser?.let { user ->
                user.sendEmailVerification()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            cont.resumeWith(kotlin.Result.success(true))
                        } else {
                            cont.cancel(LoginException("Ops i don't send verification email"))
                        }
                    }
            }
        }
    }
}