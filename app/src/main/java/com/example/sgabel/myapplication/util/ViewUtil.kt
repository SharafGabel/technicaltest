package com.example.sgabel.myapplication.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.inputmethod.InputMethodManager


class ViewUtil {

    companion object {
        fun hideKeyboard(pActivity: Activity) {
            val imm = pActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(pActivity.getWindow().getDecorView().getWindowToken(), 0)
        }

        fun dxToPx(dp: Int): Float {
            val density: Float = Resources.getSystem().getDisplayMetrics().density
            return Math.round(dp * density).toFloat()
        }

        fun pxToDx(px: Float): Int {
            val densityDpi: Int = Resources.getSystem().displayMetrics.densityDpi
            return (px / (densityDpi / 160f)).toInt()
        }
    }
}