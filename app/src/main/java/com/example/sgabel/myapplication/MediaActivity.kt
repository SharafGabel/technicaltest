package com.example.sgabel.myapplication

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.example.sgabel.myapplication.api.ApiManager
import com.example.sgabel.myapplication.model.MediaType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

class MediaActivity : ParentActivity(), RxCallback<ResponseBody> {

    @BindView(R.id.playerView)
    lateinit var mVideoView: VideoView

    @BindView(R.id.imageView)
    lateinit var mImageView: ImageView

    @BindView(R.id.progressBar)
    lateinit var mProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mediaview)
        ButterKnife.bind(this)

        if (intent != null && intent.extras != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setTitle(intent.extras.getString(Constants.TITLE))
            if (intent.extras.get(Constants.TYPE_MEDIA) != null) {
                (application as App).getNetworkComponent().inject(this)
                if (intent.extras.get(Constants.TYPE_MEDIA).equals(MediaType.VIDEO) || intent.extras.get(Constants.TYPE_MEDIA).equals(MediaType.AUDIO)) {
                    mImageView.visibility = View.GONE
                    mVideoView.visibility = View.VISIBLE
                    mVideoView.setVideoURI(Uri.parse(intent.extras.getString(Constants.URL)))
                    val vidControl = MediaController(this)
                    vidControl.setAnchorView(mVideoView)
                    mVideoView.setMediaController(vidControl);
                    mVideoView.start()
                } else if (intent.extras.get(Constants.TYPE_MEDIA).equals(MediaType.IMAGE_JPG_PNG)) {
                    mImageView.visibility = View.VISIBLE
                    mVideoView.visibility = View.GONE
                    downloadFile(intent.extras.getString(Constants.URL), mImageView)

                } else if (intent.extras.get(Constants.TYPE_MEDIA).equals(MediaType.GIF)) {
                    mImageView.visibility = View.VISIBLE
                    mVideoView.visibility = View.GONE
                    Glide.with(this).load(intent.extras.getString(Constants.URL)).into(mImageView);

                } else {
                    launchMediaOutsideApplication(pUrl = intent.extras.getString(Constants.URL), pMimeType = intent.extras.getString(Constants.MIMETYPE))
                }
            } else {
                launchMediaOutsideApplication(pUrl = intent.extras.getString(Constants.URL), pMimeType = intent.extras.getString(Constants.MIMETYPE))
            }


        }
    }

    fun launchMediaOutsideApplication(pUrl: String, pMimeType: String) {
        try {
            intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.parse(pUrl), pMimeType)
            startActivity(intent)
            onBackPressed()
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(applicationContext, getString(R.string.noApplicationFound), Toast.LENGTH_LONG).show()
            onBackPressed()
        }

    }

    fun downloadFile(pUrl: String?, pIv: ImageView) {

        mRetrofit.create(ApiManager.RestApi::class.java)
                .downloadFile(pUrl).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResultResponse, this::handleError)

        Log.d("url used", pUrl)

        mProgressBar.visibility = View.VISIBLE

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun handleError(pThrowable: Throwable) {
        Toast.makeText(applicationContext, "error receiving data : " + pThrowable.message, Toast.LENGTH_LONG).show()
    }

    override fun handleResultResponse(pResponseBody: ResponseBody) {
        Log.d("b", "body : " + pResponseBody)

        if (pResponseBody != null) {
            val vBm = BitmapFactory.decodeStream(pResponseBody!!.byteStream())
            mImageView.setImageBitmap(vBm)
            mProgressBar.visibility = View.GONE
        } else
            Toast.makeText(applicationContext, "no body response ", Toast.LENGTH_LONG).show()
    }

    override fun handleResultList(pList: List<ResponseBody>) {
    }

}
