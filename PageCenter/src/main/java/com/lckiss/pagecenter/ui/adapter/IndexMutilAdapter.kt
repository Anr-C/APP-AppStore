package com.lckiss.pagecenter.ui.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.lckiss.baselib.common.BaseApplication
import com.lckiss.baselib.common.BaseConstant.Companion.HOT_TYPE
import com.lckiss.provider.ui.adapter.AppInfoAdapter
import com.lckiss.pagecenter.R
import com.lckiss.pagecenter.data.protocol.Index
import com.lckiss.pagecenter.ui.activity.AppDetailActivity
import com.lckiss.pagecenter.ui.activity.HotAppActivity
import com.lckiss.pagecenter.ui.activity.SubjectActivity
import com.lckiss.provider.ui.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.template_banner.view.*
import kotlinx.android.synthetic.main.template_nav_icon.view.*
import kotlinx.android.synthetic.main.template_recyleview_with_title.view.*
import org.jetbrains.anko.startActivity
import retrofit2.Retrofit
import java.io.Serializable

/**
 * 推荐页多布局Adapter
 */
class IndexMutilAdapter(val context: Context, val application: BaseApplication, val retrofit: Retrofit) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener, Serializable {

    companion object {
        const val TYPE_BANNER = 1
        const val TYPE_ICON = 2
        const val TYPE_APPS = 3
        const val TYPE_GAMES = 4
    }

    lateinit var mIndexData: Index

    /**
     * 设置数据
     */
    fun setData(index: Index) {
        mIndexData = index
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            TYPE_BANNER->{
                return BannerViewHolder(LayoutInflater.from(context).inflate(R.layout.template_banner, parent, false))
            }
            TYPE_ICON->{
                return NavIconViewHolder(LayoutInflater.from(context).inflate(R.layout.template_nav_icon, parent, false))
            }
            TYPE_APPS->{
                return AppViewHolder(LayoutInflater.from(context).inflate(R.layout.template_recyleview_with_title, null, false), TYPE_APPS)
            }
            TYPE_GAMES->{
                return AppViewHolder(LayoutInflater.from(context).inflate(R.layout.template_recyleview_with_title, null, false), TYPE_GAMES)
            }
            else->{
                //暂定默认
                return BannerViewHolder(LayoutInflater.from(context).inflate(R.layout.template_banner, parent, false))
            }
        }
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun getItemViewType(position: Int): Int {
        when (position) {
            0 -> {
                return TYPE_BANNER
            }
            1 -> {
                return TYPE_ICON
            }
            2 -> {
                return TYPE_APPS
            }
            3 -> {
                return TYPE_GAMES
            }
            else -> {
                return 0
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BannerViewHolder->{
                val banners = mIndexData.banners
                val urls = ArrayList<String>(banners.size)
                for (banner in banners) {
                    urls.add(banner.thumbnail)
                }
                holder.itemView.mBannerLayout.setViewUrls(urls)
            }

            is NavIconViewHolder->{
                holder.itemView.mLayoutHotApp.setOnClickListener(this)
                holder.itemView.mLayoutHotGame.setOnClickListener(this)
                holder.itemView.mLayoutHotSubject.setOnClickListener(this)
            }

            else->{

                val mAppViewHolder = holder as AppViewHolder
                val mAppInfoAdapter = AppInfoAdapter.builder()
                        .showBrief(true)
                        .showCategoryName(true)
                        .showPosition(false)
                        .retrofit(retrofit)
                        .build()
                if (mAppViewHolder.type == TYPE_APPS) {
                    mAppViewHolder.itemView.mRecyclerViewTitle.text = "热门应用"

                    mAppInfoAdapter.addData(mIndexData.recommendApps)
                } else {
                    mAppViewHolder.itemView.mRecyclerViewTitle.text = "热门游戏"
                    mAppInfoAdapter.addData(mIndexData.recommendGames)
                }
                mAppViewHolder.itemView.mRecyclerViewWithTitle.setAdapter(mAppInfoAdapter)

                mAppViewHolder.itemView.mRecyclerViewWithTitle.addOnItemTouchListener(object : OnItemClickListener() {
                    override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                        application.cacheView=view
                        context.startActivity<AppDetailActivity>("appinfo" to mAppInfoAdapter.getItem(position))
                    }
                })
            }

        }
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.mLayoutHotApp -> {
                context.startActivity<HotAppActivity>(HOT_TYPE to TYPE_APPS)
            }
            R.id.mLayoutHotGame -> {
                context.startActivity<HotAppActivity>(HOT_TYPE to TYPE_GAMES)
            }
            R.id.mLayoutHotSubject -> {
                context.startActivity<SubjectActivity>()
            }
        }

    }

    //三种 ViewHolder内部类 游戏和应用同类型

    internal inner class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.mBannerLayout.setImageLoader { context, path, imageView -> Glide.with(context).load(path).into(imageView) }
        }
    }

    internal inner class NavIconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    internal inner class AppViewHolder(itemView: View, var type: Int) : RecyclerView.ViewHolder(itemView) {

        init {

            itemView.mRecyclerViewWithTitle.layoutManager = object : LinearLayoutManager(context) {
                override fun canScrollVertically(): Boolean {
                    //不允许垂直滚动
                    return false
                }
            }
            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST)
            itemView.mRecyclerViewWithTitle.addItemDecoration(itemDecoration)
        }

    }
}