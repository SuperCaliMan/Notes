package com.supercaliman.core.domain
import java.io.Serializable
import java.util.*

data class Note(
    var uuid: String? = null,
    var title: String = "",
    var description: String = "",
    var date: Date = Date()
) : Serializable