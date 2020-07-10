package com.supercaliman.data

import com.google.firebase.firestore.FirebaseFirestoreException
import com.supercaliman.domain.Note
import com.supercaliman.domain.Repository
import com.supercaliman.domain.Result
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.lang.Exception

class FireStoreRepoImp(private var api:FireStoreAPI):Repository{
    override suspend fun getNotes(): Result<List<Note>> {
        return try {
            val data = api.getNotes()
            Timber.v(data.toString())
            Result.Success(data)

        }catch (e: FirebaseFirestoreException){
            Result.Error(e)

        }catch (e: Exception){
            Result.Error(e)
        }
    }

    override suspend fun createNote(note: Note): Result<Boolean> {
        return try {
            api.createNote(note)
            Result.Success(true)
        }catch (e: FirebaseFirestoreException){
            Result.Error(e)

        }catch (e: Exception){
            Result.Error(e)
        }
    }

    override suspend fun deleteNote(note: Note): Result<Boolean> {
       return try {
           api.deleteNote(note)
           Result.Success(true)
       }catch (e: FirebaseFirestoreException){
           Result.Error(e)

       }catch (e: Exception){
           Result.Error(e)
       }
    }

    override suspend fun updateNote(note: Note): Result<Boolean> {
        return try {
            api.updateNote(note)
            Result.Success(true)
        }catch (e: FirebaseFirestoreException){
            Result.Error(e)

        }catch (e: Exception){
            Result.Error(e)
        }
    }
}