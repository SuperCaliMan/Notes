package com.example.compose

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.supercaliman.domain.useCase.RemoteConfigUseCase

class RemoteParamsVM @ViewModelInject constructor(
    private var remoteConfigUseCase: RemoteConfigUseCase
) : ViewModel() {

    fun getNewFeature(): Boolean = remoteConfigUseCase.isNewFeature()
}