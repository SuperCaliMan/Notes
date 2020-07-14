package com.supercaliman.note.di

import com.supercaliman.data.FireStoreRepoImp
import com.supercaliman.domain.Repository
import com.supercaliman.domain.useCase.CreateNoteTaskUseCase
import com.supercaliman.domain.useCase.DeleteNoteTaskUseCase
import com.supercaliman.domain.useCase.GetNoteTaskUseCase
import com.supercaliman.domain.useCase.UpdateNoteTaskUseCase
import com.supercaliman.note.ui.main.SharedViewModel
import com.supercaliman.note.ui.main.createNote.NoteCreateViewModel
import com.supercaliman.note.ui.main.detailNoteUI.NoteDetailViewModel
import com.supercaliman.note.ui.main.readNotes.NoteListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

//TODO:Separate module in different submodule, each one in a single android module
val appModule = module {


    single<Repository> {
        return@single FireStoreRepoImp(get())
    }

    viewModel { NoteListViewModel(get()) }
    viewModel { NoteCreateViewModel(get()) }
    viewModel { NoteDetailViewModel(get(),get()) }
    viewModel { SharedViewModel() }


    factory { UpdateNoteTaskUseCase(get()) }
    factory { DeleteNoteTaskUseCase(get()) }
    factory { GetNoteTaskUseCase(get()) }
    factory { CreateNoteTaskUseCase(get()) }


}