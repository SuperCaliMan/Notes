package com.supercaliman.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.squareup.moshi.Moshi
import com.supercaliman.core.domain.LocalRepository
import com.supercaliman.core.domain.dto.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val datastore: DataStore<Preferences>,
    private val moshi: Moshi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : LocalRepository {

    private val jsonAdapterUser = moshi.adapter(User::class.java)
    private val USER_KEY = stringPreferencesKey("user")

    override suspend fun saveUser(user: User) {
        datastore.edit { settings ->
            settings[USER_KEY] = jsonAdapterUser.toJson(user)
        }
    }

    override suspend fun getSavedUser(): User? = withContext(dispatcher) {
        datastore.data.map {
            it[USER_KEY]?.let { data ->
                jsonAdapterUser.fromJson(data)
            }
        }.first()
    }

    override suspend fun deleteUser() {
        datastore.edit { settings ->
            settings.clear()
        }
    }
}