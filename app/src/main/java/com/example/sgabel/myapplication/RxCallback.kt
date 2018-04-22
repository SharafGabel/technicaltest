package com.example.sgabel.myapplication

import okhttp3.ResponseBody

interface RxCallback<T> {
    fun handleError(pThrowable: Throwable)
    fun handleResultList(pList: List<T>)
    fun handleResultResponse(pResponse: ResponseBody)
}