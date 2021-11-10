package com.yugasa.yubosdk.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yugasa.yubolibrary.BuildConfig
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer

class AppUtil {

    companion object {
        protected val TAG = AppUtil::class.java.simpleName

        val JSON: MediaType? = MediaType.parse("application/json; charset=utf-8")

        // Log msg
        fun LogMsg(tag: String?, msg: String?) {
            if (BuildConfig.DEBUG) {
                Log.e(tag, msg!!)
            }
        }

        fun LogException(ex: Throwable) {
            if (true == AppConstants._isFileExceptionLoggingEnabled) {
                val writer: Writer = StringWriter()
                val printWriter = PrintWriter(writer)
                ex.printStackTrace(printWriter)
                val stackTrace = writer.toString()
                printWriter.close()
            }
        }

        fun LogError(tag: String?, msg: String?) {
            if (AppConstants._isLoggingEnabled) {
                if (AppConstants._isFileLoggingEnabled) {
                    Log.e(tag, msg!!)
                }
            }
        }

        fun getAppSharedPreference(aContext: Context?): SharedPreferences? {
            var sp: SharedPreferences? = null
            if (null != aContext) {
                sp = aContext.getSharedPreferences(
                        AppConstants.APP_SHARED_PREF_NAME,
                        Context.MODE_PRIVATE
                )
            }
            return sp
        }

        fun getCustomGson(): Gson? {
            val gb = GsonBuilder()
            return gb.create()
        }

        fun hideKeyboard(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = activity.currentFocus
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun getRequestBody(`object`: Any?): RequestBody? {
            var requestBody: RequestBody? = null
            try {
                val requestJson: String? = getCustomGson()?.toJson(`object`)
                //            LogMsg("requestJson", url+"  "+requestJson);  String url,
                requestBody = RequestBody.create(JSON, requestJson)
                return requestBody
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            return requestBody
        }

        fun showToast(context: Context?, msg: String?) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }
}