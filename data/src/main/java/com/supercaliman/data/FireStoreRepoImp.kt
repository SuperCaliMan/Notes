package com.supercaliman.data

import com.google.firebase.firestore.FirebaseFirestoreException
import com.supercaliman.domain.Note
import com.supercaliman.domain.Repository
import com.supercaliman.domain.Result
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.lang.Exception

class FireStoreRepoImp(private var api:FireStoreAPI):Repository{
    override suspend fun getNotes(): List<Note> {
        return try {
            val data = api.getNotes()
            Timber.v(data.toString())
            data

        }catch (e: FirebaseFirestoreException){
            throw e

        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun createNote(note: Note) {
        try {
            api.createNote(note)
        }catch (e: FirebaseFirestoreException){
            throw e

        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun deleteNote(note: Note){
       try {
           api.deleteNote(note)

       }catch (e: FirebaseFirestoreException){
           throw e

       }catch (e: Exception){
           throw e
       }
    }

    override suspend fun updateNote(note: Note) {
        try {
            api.updateNote(note)
        }catch (e: FirebaseFirestoreException){
            throw e
        }catch (e: Exception){
            throw e
        }
    }
}