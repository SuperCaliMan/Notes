package com.supercaliman.data

import com.google.firebase.firestore.QueryDocumentSnapshot

import com.supercaliman.domain.Mapper
import com.supercaliman.domain.Note
import javax.inject.Inject

class ModelMapperList @Inject constructor() : Mapper<QueryDocumentSnapshot, Note> {

    override fun map(data: QueryDocumentSnapshot): Note {
        return Note(
            data.id,
            data["title"].toString(),
            data["description"].toString(),
            data.getTimestamp("date")!!.toDate()
        )
    }
}