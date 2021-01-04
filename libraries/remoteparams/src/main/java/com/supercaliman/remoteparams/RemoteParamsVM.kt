package com.supercaliman.remoteparams

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.supercaliman.remoteparams.domain.RemoteConfigApp

class RemoteParamsVM @ViewModelInject constructor(
    var remoteConfigImp: RemoteConfigApp
) : ViewModel() {


    fun getNewFeature(): Boolean = remoteConfigImp.isNewFeature()
}