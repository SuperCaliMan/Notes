package com.supercaliman.core.data

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestoreSettings
import com.supercaliman.core.domain.dto.Note
import com.supercaliman.core.domain.dto.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class FireStoreAPI @Inject constructor(
    @ApplicationContext private var context: Context,
    private var mapper: ModelMapperDataSource
) : NotesApi {

    private val db: FirebaseFirestore

    init {
        FirebaseApp.initializeApp(context)
        db = FirebaseFirestore.getInstance()
        val setting = firestoreSettings {
            isPersistenceEnabled = true
        }
        db.firestoreSettings = setting
    }


    @ExperimentalCoroutinesApi
    @Throws(Exception::class)
    override suspend fun getNotes(user: User): Flow<List<Note>> {
        return callbackFlow {
            val subscription: ListenerRegistration =
                db.collection(COLLECTION)
                    .whereEqualTo("author", user.uuid)
                    .addSnapshotListener { snapshot, _ ->
                        snapshot?.let {
                            offer(it.documents.map { data -> mapper.map(data) }.toList())
                        }
                    }
            awaitClose { subscription.remove() }
        }
    }

    @Throws(Exception::class)
    override suspend fun deleteNote(uuid: String) =
        db.collection(COLLECTION).document(uuid).delete().await()

    @Throws(Exception::class)
    override suspend fun createNote(note: Note) = db.collection(COLLECTION).add(note).await()

    @Throws(Exception::class)
    override suspend fun updateNote(note: Note) =
        db.collection(COLLECTION).document(note.uuid!!).set(note, SetOptions.merge()).await()

    companion object {
        const val COLLECTION = "Note"
    }


}