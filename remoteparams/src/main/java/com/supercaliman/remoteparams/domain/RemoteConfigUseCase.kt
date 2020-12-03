package com.supercaliman.remoteparams.domain


import javax.inject.Inject

class RemoteConfigUseCase @Inject constructor(private val remoteConfigApp: RemoteConfigApp) {

    fun isNewFeature(): Boolean = remoteConfigApp.isNewFeature()
}