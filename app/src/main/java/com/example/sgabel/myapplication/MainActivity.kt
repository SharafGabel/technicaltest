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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody


class MainActivity : ParentActivity(), SwipeRefreshLayout.OnRefreshListener, RxCallback<File> {

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
            if (intent.extras.getString(Constants.PATH) == null || (intent.extras.getString(Constants.PATH) != null && intent.extras.getString(Constants.PATH).equals(""))) {
                mPath = "/nodes"
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                mPath = intent.extras.getString(Constants.PATH)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }

            if (intent.extras.getString(Constants.TITLE) != null && !intent.extras.getString(Constants.TITLE).equals(""))
                setTitle(intent.extras.getString(Constants.TITLE))
            else
                setTitle(getString(R.string.mainTitle))
        }

        getAllFiles(mPath)

    }


    fun attachAdapterAndRecyclerview(pFiles: List<File>) {
        mFilesAdapter = FilesAdapter(addExtensionToFiles(pFiles), this)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(getApplicationContext());

        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setItemAnimator(DefaultItemAnimator())
        mRecyclerview.setAdapter(mFilesAdapter)
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

    override fun handleResultList(pFiles: List<File>) {
        if (pFiles != null) {
            attachAdapterAndRecyclerview(pFiles)
        } else
            Toast.makeText(applicationContext, "no body response ", Toast.LENGTH_LONG).show()

        if (mSwipeRefreshLayout.isRefreshing) {
            mSwipeRefreshLayout.setRefreshing(false)

        }
    }

    override fun handleResultResponse(pResponse: ResponseBody) {
    }

    override fun handleError(pThrowable: Throwable) {
        Toast.makeText(applicationContext, "error receiving data : " + pThrowable.message, Toast.LENGTH_LONG).show()

    }

    fun getAllFiles(pPath: String) {
        mRetrofit.create(ApiManager.RestApi::class.java)
                .getFiles(pPath).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResultList, this::handleError)
        Log.d("url used", Constants.mBaseUrl + pPath)
    }

    /**
     * Refresh data from server
     */
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
