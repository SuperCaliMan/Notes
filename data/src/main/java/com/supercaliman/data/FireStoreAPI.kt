package com.supercaliman.data

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestoreSettings
import com.supercaliman.domain.Note
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class FireStoreAPI @Inject constructor(
    @ApplicationContext private var context: Context,
    private var mapper: ModelMapperFirestore
) {

    private val COLLECTION = "Note"
    private val db: FirebaseFirestore

    init {
        FirebaseApp.initializeApp(context)
        db = FirebaseFirestore.getInstance()
        val setting = firestoreSettings {
            isPersistenceEnabled = true
        }
        db.firestoreSettings = setting
    }


    @Throws(Exception::class)
    suspend fun getNotes(): List<Note> =
        db.collection(COLLECTION).get().await().map { mapper.map(it) }

    @Throws(Exception::class)
    suspend fun deleteNote(uuid: String) = db.collection(COLLECTION).document(uuid).delete().await()

    @Throws(Exception::class)
    suspend fun createNote(note: Note) = db.collection(COLLECTION).add(note).await()

    @Throws(Exception::class)
    suspend fun updateNote(note: Note) =
        db.collection(COLLECTION).document(note.uuid!!).set(note, SetOptions.merge()).await()


}