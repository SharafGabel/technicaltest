package com.example.sgabel.myapplication.utils

import com.example.sgabel.myapplication.model.File
import com.example.sgabel.myapplication.model.MediaType

class Utils {

    companion object {

        fun addExtensionToFiles(pListFile: List<File>): List<File> {
            val vListTmp: MutableList<File> = ArrayList()
            var vFile: File
            for (file in pListFile) {
                vFile = file
                val vMap = Utils.extractTypeFromMimeType(file.mimetype)
                vFile.extension = vMap.get(Constants.EXTENSION) as String?
                vFile.mediaType = vMap.get(Constants.TYPE_MEDIA) as MediaType?
                vListTmp.add(vFile)
            }
            return vListTmp
        }

        /**
         * @param mimetype
         * Provide an HashMap with the extension of the file and a MediaType from the mimetype providen
         * @return java.util.HashMap
         */
        fun extractTypeFromMimeType(mimetype: String?): HashMap<String, Any> {
            val vMap = HashMap<String, Any>()
            if (mimetype == null) {
                vMap.put(Constants.TYPE_MEDIA, MediaType.OTHER)
                vMap.put(Constants.EXTENSION, "")
                return vMap
            }
            if (mimetype.contains(Constants.PNG)) {
                vMap.put(Constants.TYPE_MEDIA, MediaType.IMAGE_JPG_PNG)
                vMap.put(Constants.EXTENSION, "." + Constants.PNG)
            } else if (mimetype.contains(Constants.JPG) || mimetype.contains(Constants.JPEG)) {
                vMap.put(Constants.TYPE_MEDIA, MediaType.IMAGE_JPG_PNG)
                vMap.put(Constants.EXTENSION, "." + Constants.JPG)
            } else if (mimetype.contains(Constants.DIRECTORY_STR)) {
                vMap.put(Constants.TYPE_MEDIA, MediaType.DIRECTORY)
                vMap.put(Constants.EXTENSION, Constants.DIRECTORY_STR)
            } else if (mimetype.contains(Constants.MP4)) {
                vMap.put(Constants.TYPE_MEDIA, MediaType.VIDEO)
                vMap.put(Constants.EXTENSION, "." + Constants.MP4)
            } else if (mimetype.contains(Constants.MPEG)) {
                vMap.put(Constants.TYPE_MEDIA, MediaType.AUDIO)
                vMap.put(Constants.EXTENSION, "." + Constants.MPEG)
            } else if (mimetype.contains(Constants.GIF)) {
                vMap.put(Constants.TYPE_MEDIA, MediaType.GIF)
                vMap.put(Constants.EXTENSION, "." + Constants.GIF)
            } else {
                vMap.put(Constants.TYPE_MEDIA, MediaType.OTHER)
                vMap.put(Constants.EXTENSION, "")
            }
            return vMap
        }
    }

}