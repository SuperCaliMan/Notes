package com.supercaliman.domain

import java.sql.Blob
import java.sql.Time
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*


data class Note(
    var uuid: String?,
    var title:String = "",
    var description:String = "",
    var date:Date= Date()
)