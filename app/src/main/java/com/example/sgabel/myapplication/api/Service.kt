package com.example.sgabel.myapplication.api


class Service(private final var restApi: ApiManager.RestApi) {


    /*
    fun getFiles(callbackFiles : GetFilesCallback): Subscription {
        return restApi.getObservableFiles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<List<File>> {
                    override fun onComplete() {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onSubscribe(s: Subscription?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onNext(t: List<File>) {
                        callbackFiles.onSuccess(t)
                    }

                    override fun onError(t: Throwable) {
                        callbackFiles.onError(NetworkError(t))
                    }
                })
    }
    */

    /*
    fun getFilesObs(username: String): Observable<List<File>> {
        return Observable.create(object : ObservableOnSubscribe<List<File>> {
            override fun subscribe(e: ObservableEmitter<List<File>>) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

            }



        }).subscribeOn(Schedulers.threadPoolForIO())

    }

    */
}
