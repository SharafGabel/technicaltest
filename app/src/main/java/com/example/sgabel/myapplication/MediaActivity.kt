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
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MediaActivity : ParentActivity() {


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
        val vFileDownloaded = mRetrofit.create(ApiManager.RestApi::class.java).downloadFile(pUrl)
        Log.d("url used", pUrl)

        mProgressBar.visibility = View.VISIBLE

        vFileDownloaded.enqueue(
                object : Callback<ResponseBody> {

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        Log.d("b", "body : " + response.body())



                        if (response.body() != null) {
                            val vBm = BitmapFactory.decodeStream(response.body().byteStream())
                            pIv.setImageBitmap(vBm)
                            mProgressBar.visibility = View.GONE
                        } else
                            Toast.makeText(applicationContext, "no body response ", Toast.LENGTH_LONG).show()

                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        //Set the error to the textview
                        Toast.makeText(applicationContext, "error receiving data", Toast.LENGTH_LONG).show()
                    }
                });
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

}
