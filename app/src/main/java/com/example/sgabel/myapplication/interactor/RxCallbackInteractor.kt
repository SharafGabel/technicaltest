package com.example.sgabel.myapplication.interactor

import okhttp3.ResponseBody

interface RxCallbackInteractor<T> {
    fun handleError(pThrowable: Throwable)
    fun handleResultList(pList: List<T>)
    fun handleResultResponse(pResponse: ResponseBody)
}