package com.supercaliman.core.di


import android.content.Context
import androidx.datastore.preferences.createDataStore
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.supercaliman.core.data.CoreRepository
import com.supercaliman.core.data.FireStoreAPI
import com.supercaliman.core.data.LocalDataSource
import com.supercaliman.core.data.ModelMapperDataSource
import com.supercaliman.core.domain.LocalRepository
import com.supercaliman.core.domain.NotesApi
import com.supercaliman.core.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun getFireStoreApi(@ApplicationContext context: Context): NotesApi {
        return FireStoreAPI(context, ModelMapperDataSource())
    }


    @Provides
    @Singleton
    fun getRepository(api: NotesApi, localRepository: LocalRepository): Repository {
        return CoreRepository(api, localRepository)
    }

    @Provides
    @Singleton
    fun getLocalDataSource(@ApplicationContext context: Context): LocalRepository {
        return LocalDataSource(
            context.createDataStore("user_setting"),
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        )
    }
}