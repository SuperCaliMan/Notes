package com.supercaliman.data

import com.supercaliman.domain.Note
import com.supercaliman.domain.Repository

class FireStoreRepoImp(private var api:FireStoreAPI):Repository{

    override suspend fun getNotes(): List<Note> {
        return try {
            api.getNotes()
        }catch (e: Exception){
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