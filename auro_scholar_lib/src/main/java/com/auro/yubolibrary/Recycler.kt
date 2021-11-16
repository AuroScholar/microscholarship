package com.auro.yubolibrary

import BotChatListAdapter
import android.content.Context
import android.provider.Settings
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.auro.yubolibrary.model.*
import com.auro.yubolibrary.service.RetroSeviceTask
import com.auro.yubolibrary.utils.ClickObjectWithGroupListener
import com.auro.yubosdk.utils.AppConstants
import com.auro.yubosdk.utils.AppConstants.BASE_URL
import com.auro.yubosdk.utils.AppConstants.BASE_URL_NEW
import com.auro.yubosdk.utils.AppUtil
import io.reactivex.disposables.CompositeDisposable
import java.lang.reflect.Type

class Recycler @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RecyclerView(context, attrs, defStyleAttr), RetroSeviceTask.ServiceResultListener {



    private var layoutManager: LinearLayoutManager? = null
    var botChatListAdapter: BotChatListAdapter? = null;
    var responseSdkYubo = ArrayList<ResponseBot>()
    private val disposable = CompositeDisposable()
    protected val TAG = Recycler::class.java.simpleName
    var setAnimationsRecycle: SetTypingAnimation? = null
    val id: String = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    var chatApi: String? = null;

    fun callBotApi(strApi: String, text: String, userId: String) {
        if (!strApi.isEmpty()) {
         callApiSendData(strApi, text, userId);
        }
    }

    init {
        botChatListAdapter = BotChatListAdapter(context, responseSdkYubo, object: ClickObjectWithGroupListener{
            override fun Click(position: Int, `object`: Any) {
                var replyNode = `object` as ResponseBot.Reply
                callApiSendData(chatApi!!,
                    replyNode.option,
                    id,
                    replyNode.link)
            }
        })

        adapter = botChatListAdapter
    }

    fun callApiSendData(strApi: String, text: String, userId: String, nodeId: String? = "False") {
        var setSession = Session("", "", "")
        var setData    = Data(userId,
            strApi,
            "False",
            "False",
            text,
            nodeId!!,
            setSession,
        "pro",
        "True",
        "False",
            "False")

        var setParams  = RequestYubo(setData)

        var dataPrint:String = AppUtil.getCustomGson()?.toJson(setData)!!
        var setDataList: ResponseBot = AppUtil.getCustomGson()!!.fromJson(dataPrint, ResponseBot::class.java)
        responseSdkYubo.add(setDataList)
        botChatListAdapter?.notifyDataSetChanged()
        setAnimationsRecycle?.showAnimation(true)
        val retroSeviceTask = RetroSeviceTask()
        retroSeviceTask.setApiUrl(BASE_URL)
        retroSeviceTask.setApiMethod(AppConstants.Service_Method_Demo)
        retroSeviceTask.setDisposable(disposable)
        retroSeviceTask.setParamObj(setParams)
        retroSeviceTask.setListener(this)
        retroSeviceTask.setResultType(ResponseBot::class.java)
        retroSeviceTask.createJsondata()
    }

    fun callApiFirstMsg(strApi: String) {
        this.chatApi = strApi
        var setParams = ParamRequest(strApi, id)
        val retroSeviceTask = RetroSeviceTask()
        retroSeviceTask.setApiUrl(BASE_URL_NEW)
        retroSeviceTask.setApiMethod(AppConstants.Service_Method_Get_Msg)
        retroSeviceTask.setDisposable(disposable)
        retroSeviceTask.setParamObj(setParams)
        retroSeviceTask.setListener(this)
        retroSeviceTask.setResultType(YuboFirstMsgResult::class.java)
        retroSeviceTask.createJsondata()
    }

    override fun onResult(serviceUrl: String?, serviceMethod: String?, httpStatusCode: Int, resultType: Type?, resultObj: Any?) {
        if (serviceMethod.equals(AppConstants.Service_Method_Demo, true)) {
            handleResult(resultObj)
        } else if (serviceMethod.equals(AppConstants.Service_Method_Get_Msg, true)){
            handleMsgResult(resultObj)
        } else if (serviceMethod.equals(AppConstants.Service_Method_Get_Chat, true)){

        }
    }

    // handle first msg response
    private fun handleMsgResult(resultObj: Any?) {
        var yuboFirstMsgResult : YuboFirstMsgResult = resultObj as YuboFirstMsgResult
        if (yuboFirstMsgResult.status.equals("success")) {
            var setSession = Session("", "", "")
            var setData = Data(
                id,
                yuboFirstMsgResult.data.clientId,
                "False",
                "False",
                yuboFirstMsgResult.data.renderData.msg,
                "False",
                setSession,
                "pro",
                "True",
                "False",
                "False",
                typeMsg = "incoming"
            )

            var dataPrint: String = AppUtil.getCustomGson()?.toJson(setData)!!
            var setDataList: ResponseBot = AppUtil.getCustomGson()!!.fromJson(dataPrint, ResponseBot::class.java)
            responseSdkYubo.add(setDataList)
            botChatListAdapter?.notifyItemInserted(responseSdkYubo.size-1)
            botChatListAdapter?.notifyDataSetChanged()

        } else if (yuboFirstMsgResult.status.equals("sessionExpired")) {
            AppUtil.showToast(context, yuboFirstMsgResult.message)
        } else {
            AppUtil.showToast(context, yuboFirstMsgResult.message)
        }
    }

    // handle bot response
    private fun handleResult(resultObj: Any?) {
        var requestYubo : ResponseBot = resultObj as ResponseBot
        if (requestYubo.status.equals("success", true)) {
            responseSdkYubo.add(requestYubo)
            setAnimationsRecycle?.showAnimation(false)
            botChatListAdapter?.notifyItemInserted(responseSdkYubo.size-1)
            botChatListAdapter?.notifyDataSetChanged()
            setAnimationsRecycle?.scrollPosition(responseSdkYubo.size-1, responseSdkYubo.get(responseSdkYubo.size-1).type_option)
        } else if (requestYubo.status.equals("fallback", true)) {
            responseSdkYubo.add(requestYubo)
            setAnimationsRecycle?.showAnimation(false)
            botChatListAdapter?.notifyItemInserted(responseSdkYubo.size-1)
            botChatListAdapter?.notifyDataSetChanged()
            setAnimationsRecycle?.scrollPosition(responseSdkYubo.size-1, responseSdkYubo.get(responseSdkYubo.size-1).type_option)
        }
    }

    // inter face for typing option & animation
    public interface SetTypingAnimation {
        public fun showAnimation(boolean: Boolean)
        public fun scrollPosition(scrollToBottom: Int, typeOption: Boolean? = true)
    }
}
