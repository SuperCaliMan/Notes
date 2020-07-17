package com.supercaliman.note.di

import com.supercaliman.domain.useCase.CreateNoteTaskUseCase
import com.supercaliman.domain.useCase.DeleteNoteTaskUseCase
import com.supercaliman.domain.useCase.GetNoteTaskUseCase
import com.supercaliman.domain.useCase.UpdateNoteTaskUseCase
import com.supercaliman.note.ui.main.ViewModels.DetailNoteViewModel
import com.supercaliman.note.ui.main.ViewModels.NoteCreateViewModel
import com.supercaliman.note.ui.main.ViewModels.NoteListViewModel
import com.supercaliman.note.ui.main.ViewModels.SharedViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    viewModel {  NoteListViewModel(get()) }
    viewModel { NoteCreateViewModel(get()) }
    viewModel { SharedViewModel() }
    viewModel { DetailNoteViewModel() }

    factory { UpdateNoteTaskUseCase(get()) }
    factory { DeleteNoteTaskUseCase(get()) }
    factory { GetNoteTaskUseCase(get()) }
    factory { CreateNoteTaskUseCase(get()) }

}