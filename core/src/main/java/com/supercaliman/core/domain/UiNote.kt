package com.supercaliman.core.domain

import java.io.Serializable

data class UiNote(
    var uuid: String? = null,
    var title: String = "",
    var description: String = "",
    var date: String = ""
) : Serializable