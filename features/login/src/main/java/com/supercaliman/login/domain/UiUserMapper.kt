package com.supercaliman.login.domain

import com.supercaliman.core.domain.dto.User
import javax.inject.Inject

class UiUserMapper @Inject constructor() {

    fun toUiModel(data: User): UiUser {
        return UiUser(
            uuid = data.uuid,
            providerId = data.providerId,
            displayName = data.displayName,
            photoUrl = data.photoUrl,
            isEmailVerified = data.isEmailVerified,
            email = data.email
        )

    }


    fun toDTOModel(data: UiUser): User {
        return User(
            uuid = data.uuid,
            providerId = data.providerId,
            displayName = data.displayName,
            photoUrl = data.photoUrl,
            isEmailVerified = data.isEmailVerified,
            email = data.email
        )
    }
}