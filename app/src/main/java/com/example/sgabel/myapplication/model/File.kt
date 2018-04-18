package com.example.sgabel.myapplication.model

class File {
    private var id: Int? = null
        get
        set

    private var name: String? = null
        get
        set

    private var mimetype: String? = null
        get
        set

    private var size: Int? = null
        get
        set

    private var modification_time: Long? = null
        get
        set

    private var path: String? = null
        get

    private var url: String? = null
        get
        set

    override fun toString(): String {
        return "File() : " + "id = " + id +
                " ,name :" + name + " ,mimetype" + mimetype +
                " ,size : " + size + " ,modification_time : " + modification_time +
                " ,path : " + path + " ,url : " + url
    }


}