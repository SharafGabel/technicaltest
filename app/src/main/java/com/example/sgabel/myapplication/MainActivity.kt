package com.example.sgabel.myapplication

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.example.sgabel.myapplication.api.ApiManager
import com.example.sgabel.myapplication.model.File
import com.example.sgabel.myapplication.model.MediaType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : ParentActivity(), SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerview)
    lateinit var mRecyclerview: RecyclerView

    @BindView(R.id.swipeRefreshLayout)
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    var mFilesAdapter: FilesAdapter? = null

    var mPath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as App).getNetworkComponent().inject(this)
        ButterKnife.bind(this)
        mSwipeRefreshLayout.setOnRefreshListener(this)

        if (mPath.equals("")) {
            mPath = "/nodes"
            setTitle(getString(R.string.mainTitle))
        }
        if (intent != null && intent.extras != null) {
            if (intent.extras.getString(Constants.PATH) == null || (intent.extras.getString(Constants.PATH) != null && !intent.extras.getString(Constants.PATH).equals(""))) {
                mPath = intent.extras.getString(Constants.PATH)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }

            if (!intent.extras.getString(Constants.TITLE).equals(""))
                setTitle(intent.extras.getString(Constants.TITLE))
        }

        getAllFiles(mPath)

    }


    fun attachAdapterAndRecyclerview(pFiles: List<File>) {
        mFilesAdapter = FilesAdapter(addExtensionToFiles(pFiles), this)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(getApplicationContext());

        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setItemAnimator(DefaultItemAnimator());
        mRecyclerview.setAdapter(mFilesAdapter);
        mFilesAdapter!!.notifyDataSetChanged()
    }

    fun addExtensionToFiles(pListFile: List<File>): List<File> {
        val vListTmp: MutableList<File> = ArrayList()
        var vFile: File
        for (file in pListFile) {
            vFile = file
            val vMap = AppUtils.extractTypeFromMimeType(file.mimetype)
            vFile.extension = vMap.get(Constants.EXTENSION) as String?
            vFile.mediaType = vMap.get(Constants.TYPE_MEDIA) as MediaType?
            vListTmp.add(vFile)
        }
        return vListTmp
    }

    /***
     * TODO : Add Observable for knowing when call has been done
     */
    fun getAllFiles(pPath: String) {
        val vFiles = mRetrofit.create(ApiManager.RestApi::class.java).getFiles(pPath)
        Log.d("url used", Constants.mBaseUrl + pPath)

        vFiles.enqueue(
                object : Callback<List<File>> {

                    override fun onResponse(call: Call<List<File>>, response: Response<List<File>>) {
                        //Set the response to the textview
                        //Toast.makeText(applicationContext,"body : "+ response.body(), Toast.LENGTH_LONG).show()
                        Log.d("b", "body : " + response.body())
                        if (response.body() != null) {
                            //Toast.makeText(applicationContext, "body : "+response.body(), Toast.LENGTH_LONG).show()
                            attachAdapterAndRecyclerview(response.body())
                        } else
                            Toast.makeText(applicationContext, "no body response ", Toast.LENGTH_LONG).show()

                        if (mSwipeRefreshLayout.isRefreshing) {
                            mSwipeRefreshLayout.setRefreshing(false)

                        }

                    }

                    override fun onFailure(call: Call<List<File>>, t: Throwable) {
                        //Set the error to the textview
                        Toast.makeText(applicationContext, "error receiving data", Toast.LENGTH_LONG).show()
                    }
                })
    }

    override fun onRefresh() {
        getAllFiles(mPath)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        try {
            Constants.mPath = Constants.mPath.substring(0, Constants.mPath.lastIndexOf('/'))
        } catch (e: StringIndexOutOfBoundsException) {
        }
    }

}
