package com.supercaliman.login.domain

import android.net.Uri
import java.io.Serializable

data class UiUser(
    val uuid: String = "",
    val providerId: String = "",
    val displayName: String? = null,
    val photoUrl: Uri? = null,
    val email: String? = null,
    val isEmailVerified: Boolean = false
) : Serializable