package com.example.sgabel.myapplication

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.sgabel.myapplication.activity.FileExplorerActivity
import com.example.sgabel.myapplication.activity.RowViewItf
import com.example.sgabel.myapplication.interactor.FilesAdapterInteractor
import com.example.sgabel.myapplication.model.File
import com.example.sgabel.myapplication.presenter.FilesAdapterPresenter

class FilesAdapter(fileList: List<File>?, mActivity: FileExplorerActivity) : RecyclerView.Adapter<FilesAdapter.MyViewHolder>() {

    var mFilesAdapterPresenter: FilesAdapterPresenter = FilesAdapterPresenter(fileList, mActivity, FilesAdapterInteractor())

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), RowViewItf {

        var tv_namefile: TextView
        var iv_Folder: ImageView
        var mItemView: LinearLayout


        init {
            tv_namefile = view.findViewById(R.id.namefile)
            iv_Folder = view.findViewById(R.id.ivFolder)
            mItemView = view.findViewById(R.id.itemView)
        }

        override fun setRowDirectory(pVisibility: Int) {
            iv_Folder.visibility = pVisibility
        }

        override fun setRowName(pName: String) {
            tv_namefile.text = pName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recyclerview, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        mFilesAdapterPresenter.onBindViewHolder(holder, position)
    }

    override fun getItemCount(): Int {
        return mFilesAdapterPresenter.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return mFilesAdapterPresenter.getItemViewType(position)
    }

}