package com.supercaliman.core.di


import com.supercaliman.core.data.FireStoreAPI
import com.supercaliman.core.data.FireStoreRepoImp
import com.supercaliman.core.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun getRepository(api: FireStoreAPI): Repository {
        return FireStoreRepoImp(api)
    }
}