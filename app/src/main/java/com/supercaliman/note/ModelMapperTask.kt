package com.supercaliman.note

import com.supercaliman.domain.Mapper
import com.supercaliman.domain.Note
import com.supercaliman.domain.UiNote
import java.text.SimpleDateFormat
import java.util.*

class ModelMapperTask:Mapper<Note,UiNote> {

    private var formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    override fun map(note: Note): UiNote {
       return UiNote(
           note.title,
           note.description,
           formatter.format(note.date)
       )
    }
}