import com.yugasa.yubolibrary.adapter.BotChatReplyAdapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxItemDecoration
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.yugasa.yubolibrary.R
import com.yugasa.yubolibrary.model.ResponseBot
import com.yugasa.yubolibrary.utils.ClickObjectWithGroupListener
import com.yugasa.yubosdk.utils.AppUtil

class BotChatListAdapter(val mContext: Context, mArr: List<ResponseBot>, val mOnClickListener: ClickObjectWithGroupListener) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private val VIEW_TYPE_ITEM_1 = 2
    private var isLoading = false
    var mDataset: List<ResponseBot>? = null
    private val viewPool = RecycledViewPool()

    //    user view holder cantains id for bindview
    internal class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvMyName: TextView
        init {
            tvMyName = itemView.findViewById(R.id.tvMyName)
        }
    }

    //    user view holder cantains id for bindview
    internal class OtherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView
        var rvNodes: RecyclerView
        init {
            tvName = itemView.findViewById(R.id.tvName)
            rvNodes = itemView.findViewById(R.id.rvNodes)
        }
    }

    companion object {
        fun otherChangebackgroundTextImageColor() {

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
        return if (mDataset!![position] == null) {
            VIEW_TYPE_LOADING
        } else {
            if (mDataset!![position].typeMsg.equals("outgoing", false)) {
                VIEW_TYPE_ITEM
            } else {
                VIEW_TYPE_ITEM_1
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(mContext).inflate(R.layout.my_chat_item_layout, parent, false);
            return UserViewHolder(view)
        } else if (viewType == VIEW_TYPE_ITEM_1) {
            val view = LayoutInflater.from(mContext).inflate(R.layout.other_chat_item_layout, parent, false);
            return OtherViewHolder(view)
        } else if (viewType == VIEW_TYPE_LOADING) {
            val view = LayoutInflater.from(mContext).inflate(R.layout.load_more_footer, parent, false);
            return LoadingViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(mContext).inflate(R.layout.load_more_footer, parent, false)
            return LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserViewHolder) {
            val modelResult: ResponseBot? =   mDataset!![position]
            holder.tvMyName.text = modelResult?.text
        } else if (holder is OtherViewHolder) {
            val modelResult: ResponseBot? = mDataset!![position]
            holder.tvName.text = modelResult?.text

            if (position == (mDataset!!.size - 1)) {
                if (modelResult?.replies != null) {
                    if (modelResult.replies.size > 0) {

                        holder.rvNodes.visibility = View.VISIBLE
                        // Create sub item view adapter
                        val imageAdapter = BotChatReplyAdapter(
                                mContext,
                                modelResult.replies,
                                object : ClickObjectWithGroupListener {
                                    override fun Click(position: Int, `object`: Any) {
                                        mOnClickListener.Click(position, `object`)
                                        holder.rvNodes.visibility = View.GONE
                                    }
                                })

                        val layoutManager = FlexboxLayoutManager(mContext, FlexDirection.ROW)
                        layoutManager.setFlexDirection(FlexDirection.ROW);
                        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
                        val flexboxItemDecoration = FlexboxItemDecoration(mContext)
                        flexboxItemDecoration.setOrientation(FlexboxItemDecoration.BOTH)
                        val drawable = GradientDrawable().apply {
                            setSize(
                                    mContext.getResources().getDimensionPixelSize(R.dimen._3sdp),
                                    mContext.getResources().getDimensionPixelSize(R.dimen._3sdp)
                            )
                        }
                        flexboxItemDecoration.setDrawable(drawable)
                        holder.rvNodes.addItemDecoration(flexboxItemDecoration)

                        // Create layout manager with initial prefetch item count
                        holder.rvNodes.setLayoutManager(layoutManager)
                        holder.rvNodes.setRecycledViewPool(viewPool)
                        holder.rvNodes.setAdapter(imageAdapter)
                    } else {
                        holder.rvNodes.visibility = View.GONE
                    }
                } else {
                    holder.rvNodes.visibility = View.GONE
                }
            } else {
                holder.rvNodes.visibility = View.GONE
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
//        mRecyclerView = mRecycler
    }
}
