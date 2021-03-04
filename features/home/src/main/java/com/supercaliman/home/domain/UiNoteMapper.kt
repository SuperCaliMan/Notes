package com.supercaliman.home.domain


import com.supercaliman.core.domain.dto.Note
import com.supercaliman.core.domain.dto.User
import com.supercaliman.core.domain.dto.Visibility
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class UiNoteMapper @Inject constructor() {

    private var formatter = SimpleDateFormat("EEE, d MMM yyyy ", Locale.getDefault())


    fun toDTOModel(data: UiNote, user: User): Note {
        return Note(
            uuid = data.uuid,
            title = data.title,
            description = data.description,
            date = Calendar.getInstance().time,
            author = user.uuid,
            visibility = Visibility.PRIVATE
        )
    }


    fun toUiModel(data: Note): UiNote {
        return UiNote(
            data.uuid,
            data.title,
            data.description,
            formatter.format(data.date)
        )
    }
}