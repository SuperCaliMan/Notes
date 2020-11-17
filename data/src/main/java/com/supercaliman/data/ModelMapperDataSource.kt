package com.supercaliman.data

import com.google.firebase.firestore.DocumentSnapshot
import com.supercaliman.domain.Mapper
import com.supercaliman.domain.Note
import javax.inject.Inject

class ModelMapperDataSource @Inject constructor() : Mapper<DocumentSnapshot, Note> {

    override fun map(data: DocumentSnapshot): Note {
        return Note(
            data.id,
            data["title"].toString(),
            data["description"].toString(),
            data.getTimestamp("date")!!.toDate()
        )
    }
}