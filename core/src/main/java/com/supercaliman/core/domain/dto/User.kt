package com.supercaliman.core.domain.dto

import android.net.Uri
import java.io.Serializable

data class User(
    val uuid: String = "",
    val providerId: String = "",
    val displayName: String? = null,
    val photoUrl: Uri? = null,
    val email: String? = null,
    val isEmailVerified: Boolean = false
) : Serializable