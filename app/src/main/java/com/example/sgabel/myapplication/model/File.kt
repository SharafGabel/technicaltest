package com.example.sgabel.myapplication.model

class File {
    var id: Int? = null
        get
        set

    var name: String? = null
        get
        set

    var mimetype: String? = null
        get
        set

    var size: Int? = null
        get
        set

    var modification_time: Long? = null
        get
        set

    var path: String? = null
        get

    var url: String? = null
        get
        set

    var extension: String? = null
        get
        set

    var mediaType: MediaType? = null
        get
        set

    override fun toString(): String {
        return "File() : " + "id = " + id +
                " ,name :" + name + " ,mimetype" + mimetype +
                " ,size : " + size + " ,modification_time : " + modification_time +
                " ,path : " + path + " ,url : " + url
    }


}