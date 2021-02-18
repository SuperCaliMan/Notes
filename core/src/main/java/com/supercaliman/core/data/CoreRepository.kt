package com.supercaliman.core.data


import com.supercaliman.core.domain.LocalRepository
import com.supercaliman.core.domain.NotesApi
import com.supercaliman.core.domain.Repository
import com.supercaliman.core.domain.dto.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CoreRepository @Inject constructor(
    private var api: NotesApi,
    private var localDataSource: LocalRepository
) : Repository {


//    private var currentUser: User? = null
//
//    init {
//        currentUser = localDataSource.retriveSavedUser()
//    }

    @ExperimentalCoroutinesApi
    override suspend fun getNotes(): Flow<List<Note>> {
        return try {
            val currentUser = localDataSource.getSavedUser()
            val data = api.getNotes(currentUser!!)
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
        } catch (e: Exception) {
            throw e
        }
    }
}