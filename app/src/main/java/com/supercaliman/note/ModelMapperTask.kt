package com.supercaliman.note

import java.text.SimpleDateFormat
import java.util.*

class ModelMapperTask :
    com.supercaliman.core.domain.Mapper<com.supercaliman.core.domain.Note, com.supercaliman.core.domain.UiNote> {

    private var formatter = SimpleDateFormat("EEE, d MMM yyyy ", Locale.getDefault())

    override fun map(data: com.supercaliman.core.domain.Note): com.supercaliman.core.domain.UiNote {
        return com.supercaliman.core.domain.UiNote(
            data.uuid,
            data.title,
            data.description,
            formatter.format(data.date)
        )
    }
}