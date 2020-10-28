package com.supercaliman.domain.useCase

import com.supercaliman.domain.RemoteConfigApp
import javax.inject.Inject

class RemoteConfigUseCase @Inject constructor(private val remoteConfigApp: RemoteConfigApp) {

    fun isNewFeature(): Boolean = remoteConfigApp.isNewFeature()
}