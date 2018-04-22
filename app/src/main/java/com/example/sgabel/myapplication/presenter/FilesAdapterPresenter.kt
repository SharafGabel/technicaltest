package com.example.sgabel.myapplication.presenter

import android.view.View
import com.example.sgabel.myapplication.FilesAdapter
import com.example.sgabel.myapplication.activity.FileExplorerActivity
import com.example.sgabel.myapplication.interactor.FilesAdapterInteractor
import com.example.sgabel.myapplication.model.File
import com.example.sgabel.myapplication.utils.Constants

class FilesAdapterPresenter(var fileList: List<File>?, var pActivity: FileExplorerActivity, var pInteractor: FilesAdapterInteractor) {


    fun onBindViewHolder(holder: FilesAdapter.MyViewHolder, position: Int) {
        val vFile = fileList!![position]

        //force if and else if on visibility for prevent bad recycling view
        if (holder.itemViewType.equals(Constants.DIRECTORY)) {
            holder.setRowDirectory(View.VISIBLE)
        } else if (holder.itemViewType.equals(Constants.FILE)) {
            holder.setRowDirectory(View.GONE)
        }

        if (vFile.extension.equals(Constants.DIRECTORY_STR)) {
            holder.setRowName(vFile.name!!)
        } else {
            holder.setRowName(String.format(vFile.name + vFile.extension))
        }

        pInteractor.showFileOrDirectory(holder.mItemView, pActivity, vFile)
    }

    fun getItemCount(): Int {
        return fileList!!.size
    }

    fun getItemViewType(position: Int): Int {
        return if (fileList!!.get(position).extension.equals(Constants.DIRECTORY_STR)) Constants.DIRECTORY else Constants.FILE
    }
}