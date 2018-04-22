package com.example.sgabel.myapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import com.example.sgabel.myapplication.model.MediaType
import java.io.File


class AppUtils {

    companion object {

        fun getDefaultViewIntent(uri: Uri, pActivity: AppCompatActivity, pMimeType: String): Intent? {
            val pm = pActivity.getPackageManager()
            val intent = Intent(Intent.ACTION_VIEW)
            // Let's probe the intent exactly in the same way as the VIEW action
            val name = File(uri.getPath()).getName()
            intent.setDataAndType(uri, pMimeType)
            val lri = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            return if (lri.size > 0) intent else null
        }

        /**
         * Utilisation de string en retour plutôt qu'une enum par rapport aux nombres importants de possibilitées au niveau des types
         */
        fun extractTypeFromMimeType(mimetype: String?): HashMap<String, Any> {
            val vMap = HashMap<String, Any>()
            if (mimetype == null) {
                vMap.put(Constants.TYPE_MEDIA, MediaType.OTHER)
                vMap.put(Constants.EXTENSION, "")
                return vMap
            }
            if (mimetype.contains("png")) {
                vMap.put(Constants.TYPE_MEDIA, MediaType.IMAGE_JPG_PNG)
                vMap.put(Constants.EXTENSION, ".png")
            } else if (mimetype.contains("jpg") || mimetype.contains("jpeg")) {
                vMap.put(Constants.TYPE_MEDIA, MediaType.IMAGE_JPG_PNG)
                vMap.put(Constants.EXTENSION, ".jpg")
            } else if (mimetype.contains("directory")) {
                vMap.put(Constants.TYPE_MEDIA, MediaType.DIRECTORY)
                vMap.put(Constants.EXTENSION, "directory")
            } else if (mimetype.contains("mp4")) {
                vMap.put(Constants.TYPE_MEDIA, MediaType.VIDEO)
                vMap.put(Constants.EXTENSION, ".mp4")
            } else if (mimetype.contains("mpeg")) {
                vMap.put(Constants.TYPE_MEDIA, MediaType.AUDIO)
                vMap.put(Constants.EXTENSION, ".mpeg")
            } else if (mimetype.contains("gif")) {
                vMap.put(Constants.TYPE_MEDIA, MediaType.GIF)
                vMap.put(Constants.EXTENSION, ".gif")
            } else {
                vMap.put(Constants.TYPE_MEDIA, MediaType.OTHER)
                vMap.put(Constants.EXTENSION, "")
            }
            return vMap
        }
    }

}