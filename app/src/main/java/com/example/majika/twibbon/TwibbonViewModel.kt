package com.example.majika.twibbon

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TwibbonViewModel : ViewModel() {

    private val _bitMap = MutableLiveData<Bitmap?>()
    val bitMap: LiveData<Bitmap?>
        get() = _bitMap

    fun setData(bitmap: Bitmap) {
        _bitMap.value = bitmap
    }

    init {
        _bitMap.value = null
    }
}