package com.example.sgabel.myapplication.api

import com.example.sgabel.myapplication.model.File

interface GetFilesCallback {
    fun onSuccess(pFiles: List<File>)

    fun onError(networkError: NetworkError)
}