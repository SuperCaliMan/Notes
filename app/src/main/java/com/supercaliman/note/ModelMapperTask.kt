package com.supercaliman.note

import com.supercaliman.domain.Mapper
import com.supercaliman.domain.Note
import com.supercaliman.domain.UiNote
import java.text.SimpleDateFormat
import java.util.*

class ModelMapperTask:Mapper<Note,UiNote> {

    private var formatter = SimpleDateFormat("EEE, d MMM yyyy ", Locale.getDefault())

    override fun map(data: Note): UiNote {
       return UiNote(
           data.uuid,
           data.title,
           data.description,
           formatter.format(data.date)
       )
    }
}