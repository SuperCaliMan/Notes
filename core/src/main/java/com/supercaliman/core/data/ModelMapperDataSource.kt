package com.supercaliman.core.data

import com.google.firebase.firestore.DocumentSnapshot
import com.supercaliman.core.domain.Mapper
import com.supercaliman.core.domain.dto.Note
import com.supercaliman.core.domain.dto.Visibility
import javax.inject.Inject

class ModelMapperDataSource @Inject constructor() : Mapper<DocumentSnapshot, Note> {

    override fun map(data: DocumentSnapshot): Note {
        return Note(
            data.id,
            data["title"].toString(),
            data["description"].toString(),
            data.getTimestamp("date")!!.toDate(),
            author = data["author"].toString(),
            visibility = Visibility.valueOf(data["visibility"].toString().toUpperCase())
        )
    }
}