package com.supercaliman.login.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.supercaliman.login.data.AuthRepoImpl
import com.supercaliman.login.domain.AuthRepo
import com.supercaliman.login.domain.UserMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
object FirebaseAuthModule {


    @Provides
    @Singleton
    fun getAuthRepo(): AuthRepo {
        val firebase = Firebase.auth
        return AuthRepoImpl(firebase, UserMapper())
    }
}