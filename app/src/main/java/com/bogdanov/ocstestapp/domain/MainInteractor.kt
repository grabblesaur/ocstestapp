package com.bogdanov.ocstestapp.domain

import com.bogdanov.ocstestapp.data.MainRepository
import javax.inject.Inject

class MainInteractor @Inject constructor(val mainRepository: MainRepository) {

    fun getData() = mainRepository.getData()

}