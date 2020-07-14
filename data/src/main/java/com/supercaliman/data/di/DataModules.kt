package com.supercaliman.data.di

import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.supercaliman.data.FireStoreAPI
import com.supercaliman.data.FireStoreRepoImp
import com.supercaliman.domain.Repository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module{
    single<FirebaseFirestore>{
        FirebaseApp.initializeApp(androidContext())
        return@single Firebase.firestore
    }

    single<Repository> {
        return@single FireStoreRepoImp(get())
    }

    single { FireStoreAPI(get()) }
}