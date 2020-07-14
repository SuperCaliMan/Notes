package com.supercaliman.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestoreSettings
import com.supercaliman.domain.Note
import kotlinx.coroutines.tasks.await
import com.supercaliman.domain.Result
import timber.log.Timber
import java.lang.Exception
import java.sql.Time
import java.sql.Timestamp
import java.util.*


class FireStoreAPI(private var db:FirebaseFirestore) {
    val collection = "Note"

   init {

       val setting = firestoreSettings {
           isPersistenceEnabled = true
       }
       db.firestoreSettings = setting

   }

    @Throws(FirebaseFirestoreException::class,Exception::class)
    suspend fun getNotes():List<Note> = db.collection(collection).get().await().map { Note(
        //TODO improve with mapper
        it.id,
        it["title"].toString(),
        it["description"].toString(),
        it.getTimestamp("date")!!.toDate())
    }




    @Throws(FirebaseFirestoreException::class,Exception::class)
    suspend fun deleteNote(uuid: String) =  db.collection(collection).document(uuid).delete().await()

    @Throws(FirebaseFirestoreException::class,Exception::class)

    suspend fun createNote(note: Note) = db.collection(collection).add(note).await()

    @Throws(FirebaseFirestoreException::class,Exception::class)
    suspend fun updateNote(note: Note) = db.collection(collection).document(note.uuid!!).set(note, SetOptions.merge()).await()


}