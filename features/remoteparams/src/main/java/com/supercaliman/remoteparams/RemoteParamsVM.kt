package com.supercaliman.remoteparams

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.supercaliman.remoteparams.domain.RemoteConfigUseCase

class RemoteParamsVM @ViewModelInject constructor(
    var remoteConfigUseCase: RemoteConfigUseCase
) : ViewModel() {

    fun getNewFeature(): Boolean = remoteConfigUseCase.isNewFeature()
}