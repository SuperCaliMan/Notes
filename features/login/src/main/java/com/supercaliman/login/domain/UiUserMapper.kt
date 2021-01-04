package com.supercaliman.login.domain

import android.net.Uri
import com.supercaliman.core.domain.dto.User
import javax.inject.Inject

class UiUserMapper @Inject constructor() {

    fun toUiModel(data: User): UiUser {
        return UiUser(
            uuid = data.uuid,
            providerId = data.providerId,
            displayName = data.displayName,
            photoUrl = Uri.parse(data.photoUrl),
            isEmailVerified = data.isEmailVerified,
            email = data.email
        )

    }


    fun toDTOModel(data: UiUser): User {
        return User(
            uuid = data.uuid,
            providerId = data.providerId,
            displayName = data.displayName,
            photoUrl = data.photoUrl.toString(),
            isEmailVerified = data.isEmailVerified,
            email = data.email
        )
    }
}