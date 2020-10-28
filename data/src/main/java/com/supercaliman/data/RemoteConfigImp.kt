package com.supercaliman.data

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.gson.Gson
import com.supercaliman.domain.RemoteConfigApp
import javax.inject.Inject

class RemoteConfigImp @Inject constructor(private val gson: Gson) : RemoteConfigApp {

    override fun isNewFeature(): Boolean =
        read<Boolean>(ConfigParam.NEW_FEATURE, Boolean::class.java) ?: NEW_FEATURE

    private val config: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance().apply {
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(CONFIG_CACHE_EXPIRATION_SECONDS)
            .build()
        setConfigSettingsAsync(configSettings)
        fetch(CONFIG_CACHE_EXPIRATION_SECONDS)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    activate()
                }
            }
    }


    private fun <T> read(param: ConfigParam, returnType: Class<T>): T? {
        val value: Any? = when (returnType) {
            String::class.java -> config.getString(param.key)
            Boolean::class.java -> config.getBoolean(param.key)
            Long::class.java -> config.getLong(param.key)
            Int::class.java -> config.getLong(param.key).toInt()
            Double::class.java -> config.getDouble(param.key)
            Float::class.java -> config.getDouble(param.key).toFloat()
            else -> {
                val json = config.getString(param.key)
                json.takeIf { it.isNotBlank() }?.let { gson.jsonToObjectOrNull(json, returnType) }
            }
        }
        @Suppress("UNCHECKED_CAST")
        return (value as? T)
    }

    private fun <T> Gson.jsonToObjectOrNull(json: String?, clazz: Class<T>): T? =
        try {
            fromJson(json, clazz)
        } catch (ignored: Exception) {
            null
        }

    private companion object {
        /**
         * Config expiration interval 10 minutes.
         */
        private const val CONFIG_CACHE_EXPIRATION_SECONDS = 300L
        private const val NEW_FEATURE = false
    }


    private enum class ConfigParam(val key: String) {
        NEW_FEATURE("new_feature"),
        SAMPLE("sample_param")
    }

}