package com.supercaliman.data

import com.google.firebase.firestore.QueryDocumentSnapshot

import com.supercaliman.domain.Mapper
import com.supercaliman.domain.Note

class ModelMapper:Mapper<QueryDocumentSnapshot, Note> {

    override fun map(data: QueryDocumentSnapshot): Note {
        return Note(
            data.id,
            data["title"].toString(),
            data["description"].toString(),
            data.getTimestamp("date")!!.toDate())
    }
}