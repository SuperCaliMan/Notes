package com.supercaliman.data

import com.google.firebase.firestore.FirebaseFirestoreException
import com.supercaliman.domain.Note
import com.supercaliman.domain.Repository

class FireStoreRepoImp(private var api:FireStoreAPI):Repository{
    @Throws(FirebaseFirestoreException::class)
    override suspend fun getNotes(): List<Note> {
        return try {
            val data = api.getNotes()
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

    override suspend fun deleteNote(uuid: String){
       try {
           api.deleteNote(uuid)
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