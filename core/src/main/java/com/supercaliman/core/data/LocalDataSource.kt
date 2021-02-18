package com.supercaliman.core.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.squareup.moshi.Moshi
import com.supercaliman.core.domain.LocalRepository
import com.supercaliman.core.domain.dto.User
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val moshi: Moshi
) : LocalRepository {

    private val jsonAdapterUser = moshi.adapter(User::class.java)

    override fun saveUser(user: User): Boolean {
        with(sharedPreferences.edit()) {
            putString("User", jsonAdapterUser.toJson(user))
            apply()
        }
        return true
    }

    override fun getSavedUser(): User? {
        val jsonUser = sharedPreferences.getString("User", null)
        return if (jsonUser != null) {
            jsonAdapterUser.fromJson(jsonUser)
        } else {
            null
        }
    }

    override fun deleteUser() {
        sharedPreferences.edit(commit = true) {
            clear()
        }
    }
}