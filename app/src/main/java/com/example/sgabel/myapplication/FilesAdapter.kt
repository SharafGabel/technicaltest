package com.example.sgabel.myapplication

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.sgabel.myapplication.model.File

class FilesAdapter(private val fileList: List<File>?, var mActivity: MainActivity) : RecyclerView.Adapter<FilesAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_namefile: TextView
        var iv_Folder: ImageView

        init {
            tv_namefile = view.findViewById(R.id.namefile)
            iv_Folder = view.findViewById(R.id.ivFolder)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recyclerview, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val vFile = fileList!![position]

        //force if and else if on visibility for prevent bad recycling view
        if (holder.itemViewType.equals(Constants.DIRECTORY)) {
            holder.iv_Folder.visibility = View.VISIBLE
        } else if (holder.itemViewType.equals(Constants.DIRECTORY)) {
            holder.iv_Folder.visibility = View.GONE
        }

        if (vFile.extension.equals("directory")) {
            holder.tv_namefile.setText(vFile.name)
            holder.tv_namefile.setOnClickListener({
                Constants.mPath += vFile.path!!
                Log.d("url", Constants.mBaseUrl + Constants.mPath)
                val vIntent = Intent(mActivity, MainActivity::class.java)
                vIntent.putExtra(Constants.PATH, Constants.mPath)
                vIntent.putExtra(Constants.TITLE, vFile.name)
                vIntent.putExtra(Constants.TYPE_MEDIA, vFile.mediaType)
                mActivity.startActivity(vIntent)
            })
        } else {
            holder.tv_namefile.text = String.format(vFile.name + vFile.extension)
            holder.tv_namefile.setOnClickListener(View.OnClickListener {
                val vIntent = Intent(mActivity, MediaActivity::class.java)
                vIntent.putExtra(Constants.URL, vFile.url)
                vIntent.putExtra(Constants.TITLE, vFile.name)
                vIntent.putExtra(Constants.TYPE_MEDIA, vFile.mediaType)
                vIntent.putExtra(Constants.MIMETYPE, vFile.mimetype)
                mActivity.startActivity(vIntent)
            })
        }

    }

    override fun getItemCount(): Int {
        return fileList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (fileList?.get(position)?.extension.equals("directory")) Constants.DIRECTORY else Constants.FILE
    }
}