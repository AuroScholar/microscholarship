package com.yugasa.yubolibrary.service

import android.content.Context
import android.util.Log
import com.yugasa.yubolibrary.BuildConfig.BASE_URL
import com.yugasa.yubosdk.utils.AppConstants
import com.yugasa.yubosdk.utils.AppUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.lang.reflect.Type

class RetroSeviceTask {

    interface ServiceResultListener {
        fun onResult(serviceUrl: String?, serviceMethod: String?, httpStatusCode: Int, resultType: Type?, resultObj: Any?
        )
    }

    private val TAG = RetroSeviceTask::class.java.simpleName

    private var listener: ServiceResultListener? = null

    var apiInterface: ApiInterface? = null

    var miError: String? = null

//    private val queue: RequestQueue? = null

    private var apiUrl: String? = null

    private var resultObj: Any? = null

    private var disposable: CompositeDisposable? = null

    private var requestBodyMap: Map<String, RequestBody>? = null

    private var paramObj: Any? = null

    private var resultType: Type? = null

    private var apiMethod: String? = null

    private var type: String? = null

    var methodUrl: String? = null

    private val statusCode = 0

    private var contexto: Context? = null

    private val getParameters: String? = null

    private var file: MultipartBody.Part? = null

    private var imageFile: List<MultipartBody.Part>? = null

    fun setListFile(imageFile: List<MultipartBody.Part>?) {
        this.imageFile = imageFile
    }

    fun setFile(file: MultipartBody.Part?) {
        this.file = file
    }

    fun getType(): String? {
        return type
    }

    fun setType(type: String?) {
        this.type = type
    }

    fun setDisposable(disposable: CompositeDisposable?) {
        this.disposable = disposable
    }

    fun setParamObj(paramObj: Any?) {
        this.paramObj = paramObj
    }

    fun setRequestBodyMap(requestBodyMap: Map<String, RequestBody>?) {
        this.requestBodyMap = requestBodyMap
    }

    fun setListener(listener: ServiceResultListener?) {
        this.listener = listener
    }

    fun setContext(context: Context?) {
        contexto = context
    }

    fun setResultType(resultType: Type?) {
        this.resultType = resultType
    }

    fun setApiUrl(apiUrl: String?) {
        this.apiUrl = apiUrl
    }

    fun setApiMethod(apiMethod: String?) {
        this.apiMethod = apiMethod
    }

    fun setResultObj(resultObj: Any?) {
        this.resultObj = resultObj
    }

    //  Post data using formdata
    fun createFormdata() {
        try {

            if (apiUrl != null) {
                methodUrl = apiUrl
            }
            if (null != apiMethod && false == apiMethod!!.isEmpty()) {
                methodUrl = apiUrl + apiMethod
            }
            apiInterface = RetroBase.getClient(apiUrl!!)?.create(ApiInterface::class.java) // Create retro fit client instans and Api and interface
            disposable!!.add(
                apiInterface!!.getLoginResponse(apiMethod, requestBodyMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<Any>() {
                        override fun onSuccess(o: Any) {
                            AppUtil.LogMsg("responseJson", BASE_URL + apiMethod + "  " + AppUtil.getCustomGson()?.toJson(o))
                            resultObj = AppUtil.getCustomGson()?.fromJson(AppUtil.getCustomGson()?.toJson(o), resultType)
                            if (listener != null) {
                                listener!!.onResult(apiUrl, apiMethod, statusCode, resultType, resultObj)
                            }
                        }

                        override fun onError(e: Throwable) {
                            try {
                                AppUtil.LogMsg("responseJson", methodUrl + "  " + AppUtil.getCustomGson()?.toJson(e))
                                val jsonObject = JSONObject()
                                jsonObject.put("error", true)
                                jsonObject.put("error_description", "Something went wrong")
                                jsonObject.put("error_code", "201")
                                AppUtil.LogMsg(TAG, "Adi  $jsonObject")
                                resultObj = AppUtil.getCustomGson()?.fromJson(jsonObject.toString(), resultType)
                                if (listener != null) {
                                    listener!!.onResult(apiUrl, apiMethod, statusCode, resultType, resultObj)
                                }
                                e.printStackTrace()
                            } catch (ex: Exception) {
                                ex.printStackTrace()
                            }
                        }
                    })
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // post data with json
    fun createJsondata() {
        try {
            if (apiUrl != null) {
                methodUrl = apiUrl
            }
            if (null != apiMethod && false == apiMethod!!.isEmpty()) {
                methodUrl = apiUrl + apiMethod
            }
            apiInterface = RetroBase.getClient(apiUrl!!)?.create(ApiInterface::class.java) // Create retro fit client instans and Api and interface
            AppUtil.LogMsg(TAG, "request: " + methodUrl + "  " + AppUtil.getCustomGson()!!.toJson(paramObj))
            disposable!!.add(
                apiInterface!!.getWithoutHeaderApi(apiMethod, AppUtil.getRequestBody(paramObj))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<Any?>() {
                        override fun onSuccess(o: Any) {
                            AppUtil.LogMsg(
                                "responseJson",
                                "$BASE_URL$apiMethod  " + AppUtil.getCustomGson()?.toJson(o)
                            )
                            resultObj = AppUtil.getCustomGson()?.fromJson(AppUtil.getCustomGson()?.toJson(o), resultType)
                            if (listener != null) {
                                listener!!.onResult(apiUrl, apiMethod, statusCode, resultType, resultObj)
                            }
                        }

                        override fun onError(e: Throwable) {
                            try {
                                e.printStackTrace()
                                AppUtil.LogMsg("responseJson", methodUrl + "  " + AppUtil.getCustomGson()!!.toJson(e))
                                val jsonObject = JSONObject()
                                jsonObject.put("error", true)
                                jsonObject.put("error_description", "Something went wrong")
                                jsonObject.put("error_code", "201")
                                resultObj = AppUtil.getCustomGson()?.fromJson(jsonObject.toString(), resultType)
                                if (listener != null) {
                                    listener!!.onResult(apiUrl, apiMethod, statusCode, resultType, resultObj)
                                }
                            } catch (ex: java.lang.Exception) {
                                ex.printStackTrace()
                            }
                        }
                    })
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}