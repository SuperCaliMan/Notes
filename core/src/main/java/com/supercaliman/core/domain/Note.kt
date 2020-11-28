package com.supercaliman.core.domain
import java.util.*


data class Note(
    var uuid: String?,
    var title:String = "",
    var description:String = "",
    var date:Date= Date()
)