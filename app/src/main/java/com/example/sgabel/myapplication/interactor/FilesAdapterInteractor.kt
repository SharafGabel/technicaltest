package com.example.sgabel.myapplication.interactor

import android.content.Intent
import android.util.Log
import android.widget.LinearLayout
import com.example.sgabel.myapplication.activity.FileExplorerActivity
import com.example.sgabel.myapplication.activity.MediaActivity
import com.example.sgabel.myapplication.model.File
import com.example.sgabel.myapplication.utils.Constants

class FilesAdapterInteractor {
    fun showFileOrDirectory(pLinearLayout: LinearLayout, pActivity: FileExplorerActivity, pFile: File) {
        pLinearLayout.setOnClickListener({

            val vIntent: Intent

            if (pFile.extension.equals(Constants.DIRECTORY_STR)) {
                Constants.mPath += pFile.path!!
                Log.d("url", Constants.mBaseUrl + Constants.mPath)

                vIntent = Intent(pActivity, FileExplorerActivity::class.java)
                vIntent.putExtra(Constants.PATH, Constants.mPath)
            } else {
                vIntent = Intent(pActivity, MediaActivity::class.java)
                vIntent.putExtra(Constants.URL, pFile.url)
                vIntent.putExtra(Constants.MIMETYPE, pFile.mimetype)
            }


            vIntent.putExtra(Constants.TITLE, pFile.name)
            vIntent.putExtra(Constants.TYPE_MEDIA, pFile.mediaType)
            pActivity.startActivity(vIntent)
        })
    }
}