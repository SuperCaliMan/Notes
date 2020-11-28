package com.supercaliman.core.data


import com.supercaliman.core.domain.Note
import com.supercaliman.core.domain.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class FireStoreRepoImp(private var api: FireStoreAPI) : Repository {

    @ExperimentalCoroutinesApi
    override suspend fun getNotes(): Flow<List<Note>> {
        return try {
            val data = api.getNotes()
            data
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun createNote(note: Note) {
        try {
            api.createNote(note)
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun deleteNote(uuid: String){
       try {
           api.deleteNote(uuid)
       }catch (e: Exception){
           throw e
       }
    }

    override suspend fun updateNote(note: Note) {
        try {
            api.updateNote(note)
        }catch (e: Exception){
            throw e
        }
    }
}