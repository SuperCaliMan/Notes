package com.supercaliman.data

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot

import com.supercaliman.domain.Note
import javax.inject.Inject

class ModelMapperFirestore @Inject constructor() {

    fun mapNoteCollection(data: QueryDocumentSnapshot): Note {
        return Note(
            data.id,
            data["title"].toString(),
            data["description"].toString(),
            data.getTimestamp("date")!!.toDate()
        )
    }

    fun mapNote(documentSnapshot: DocumentSnapshot): Note {
        return Note(
            documentSnapshot.id,
            documentSnapshot["title"].toString(),
            documentSnapshot["description"].toString(),
            documentSnapshot.getTimestamp("date")!!.toDate()
        )
    }
}