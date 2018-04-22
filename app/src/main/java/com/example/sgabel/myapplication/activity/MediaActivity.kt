package com.example.sgabel.myapplication.activity

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
import com.example.sgabel.myapplication.App
import com.example.sgabel.myapplication.R
import com.example.sgabel.myapplication.api.RestApiItf
import com.example.sgabel.myapplication.interactor.RxCallbackInteractor
import com.example.sgabel.myapplication.model.MediaType
import com.example.sgabel.myapplication.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

class MediaActivity : ParentActivity(), RxCallbackInteractor<ResponseBody> {

    //region Attributes
    @BindView(R.id.playerView)
    lateinit var mVideoView: VideoView

    @BindView(R.id.imageView)
    lateinit var mImageView: ImageView

    @BindView(R.id.progressBar)
    lateinit var mProgressBar: ProgressBar
    //endregion Attributes

    //region Override methods
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
                    setViewVisibility(View.GONE, View.VISIBLE)
                    launchAudioVideoAndAttachMediaControl()
                } else if (intent.extras.get(Constants.TYPE_MEDIA).equals(MediaType.IMAGE_JPG_PNG)) {
                    setViewVisibility(View.VISIBLE, View.GONE)
                    downloadFile(intent.extras.getString(Constants.URL))
                } else if (intent.extras.get(Constants.TYPE_MEDIA).equals(MediaType.GIF)) {
                    setViewVisibility(View.VISIBLE, View.GONE)
                    Glide.with(this).load(intent.extras.getString(Constants.URL)).into(mImageView);
                } else {
                    launchMediaOutsideApplication(pUrl = intent.extras.getString(Constants.URL), pMimeType = intent.extras.getString(Constants.MIMETYPE))
                }
            } else {
                launchMediaOutsideApplication(pUrl = intent.extras.getString(Constants.URL), pMimeType = intent.extras.getString(Constants.MIMETYPE))
            }


        }
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

    override fun handleResultResponse(pResponse: ResponseBody) {
        Log.d("b", "body : " + pResponse)

        if (pResponse != null) {
            val vBm = BitmapFactory.decodeStream(pResponse.byteStream())
            mImageView.setImageBitmap(vBm)
            hideProgressBar()
        } else
            Toast.makeText(applicationContext, "no body response ", Toast.LENGTH_LONG).show()
    }

    override fun handleResultList(pList: List<ResponseBody>) {
    }
    //endregion Override methods

    //region LaunchMedia
    private fun launchAudioVideoAndAttachMediaControl() {
        mVideoView.setVideoURI(Uri.parse(intent.extras.getString(Constants.URL)))
        val vidControl = MediaController(this)
        vidControl.setAnchorView(mVideoView)
        mVideoView.setMediaController(vidControl);
        mVideoView.start()
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
    //endregion LaunchMedia

    //region WS
    fun downloadFile(pUrl: String?) {

        mRetrofit.create(RestApiItf::class.java)
                .downloadFile(pUrl).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResultResponse, this::handleError)

        Log.d("url used", pUrl)

        showProgressBar()

    }
    //endregion WS

    //region UI
    fun setViewVisibility(pVisibilityImageview: Int, pVisibilityVideoView: Int) {
        mImageView.visibility = pVisibilityImageview
        mVideoView.visibility = pVisibilityVideoView
    }

    fun showProgressBar() {
        mProgressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        mProgressBar.visibility = View.GONE
    }
    //endregion UI

}
