package com.supercaliman.login.domain

import com.google.firebase.auth.FirebaseUser
import com.supercaliman.core.domain.Mapper
import com.supercaliman.core.domain.dto.User
import javax.inject.Inject

class UserMapper @Inject constructor() : Mapper<FirebaseUser?, User?> {

    override fun map(data: FirebaseUser?): User? {
        return if (data != null) {
            User(
                uuid = data.uid,
                providerId = data.providerId,
                displayName = data.displayName,
                photoUrl = data.photoUrl.toString(),
                isEmailVerified = data.isEmailVerified,
                email = data.email
            )
        } else {
            null
        }
    }
}