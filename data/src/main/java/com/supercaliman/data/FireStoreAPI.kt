package com.supercaliman.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestoreSettings
import com.supercaliman.domain.Note
import kotlinx.coroutines.tasks.await
import java.lang.Exception


class FireStoreAPI(private var db:FirebaseFirestore,private var mapper:ModelMapper){
    val COLLECTION = "Note"

   init {

       val setting = firestoreSettings {
           isPersistenceEnabled = true
       }
       db.firestoreSettings = setting

   }


    @Throws(FirebaseFirestoreException::class,Exception::class)
    suspend fun getNotes():List<Note> = db.collection(COLLECTION).get().await().map { mapper.map(it)}

    @Throws(FirebaseFirestoreException::class,Exception::class)
    suspend fun deleteNote(uuid: String) =  db.collection(COLLECTION).document(uuid).delete().await()

    @Throws(FirebaseFirestoreException::class,Exception::class)

    suspend fun createNote(note: Note) = db.collection(COLLECTION).add(note).await()

    @Throws(FirebaseFirestoreException::class,Exception::class)
    suspend fun updateNote(note: Note) = db.collection(COLLECTION).document(note.uuid!!).set(note, SetOptions.merge()).await()


}