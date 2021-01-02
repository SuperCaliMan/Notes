package com.supercaliman.core.domain.dto
import java.io.Serializable
import java.util.*

data class Note(
    var uuid: String? = null,
    var title: String = "",
    var description: String = "",
    var date: Date = Date(),
    var author: String,
    var visibility: Visibility = Visibility.PRIVATE
) : Serializable

enum class Visibility : Serializable {
    PRIVATE,
    PUBLIC
}