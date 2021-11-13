package com.auro.yubolibrary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.auro.scholr.R

import com.auro.yubolibrary.model.ResponseBot
import com.auro.yubolibrary.utils.ClickObjectWithGroupListener


class BotChatReplyAdapter(val mContext: Context, mArr: List<ResponseBot.Reply>, val mOnClickListener: ClickObjectWithGroupListener) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    var mDataset: List<ResponseBot.Reply>? = null

    //    user view holder cantains id for bindview
    internal class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvMyName: TextView
        var consParent: LinearLayout
        init {
            tvMyName = itemView.findViewById(R.id.tvMyName)
            consParent = itemView.findViewById(R.id.consParent)
        }
    }

    //    Load more layout value
    internal class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var progressBar: ProgressBar

        init {
            progressBar = itemView.findViewById<View>(R.id.load_more_progressBar) as ProgressBar
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mDataset!![position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(mContext).inflate(R.layout.reply_item_layout, parent, false);
            return UserViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(mContext).inflate(R.layout.load_more_footer, parent, false)
            return LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserViewHolder) {
            val modelResult: ResponseBot.Reply? =   mDataset!![position]
            holder.tvMyName.text = modelResult?.option
            holder.tvMyName.setOnClickListener {
                mOnClickListener.Click(position = position, `object` = modelResult!!)
            }

        } else if (holder is LoadingViewHolder) {
            holder.progressBar.isIndeterminate = true
        }
    }

    override fun getItemCount(): Int {
        return if (mDataset == null) 0 else mDataset!!.size
    }

    init {
        mDataset = mArr
    }
}
