package com.lckiss.pagecenter.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.lckiss.baselib.common.BaseApplication
import com.lckiss.baselib.common.BaseConstant.Companion.BASE_IMG_URL
import com.lckiss.baselib.ui.activity.BaseActivity
import com.lckiss.baselib.utils.DensityUtil
import com.lckiss.pagecenter.R
import com.lckiss.pagecenter.presenter.AppDetailPresenter
import com.lckiss.pagecenter.ui.fragment.AppDetailFragment
import com.lckiss.baselib.router.RouterPath
import com.lckiss.provider.data.protocol.AppInfo
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.ionicons_typeface_library.Ionicons
import kotlinx.android.synthetic.main.activity_app_detail.*

/**
 * 详情
 */
@Route(path = RouterPath.PageCenter.PATH_DETAIL)
class AppDetailActivity : BaseActivity<AppDetailPresenter>() {


    lateinit var mAppInfo: AppInfo

    override fun injectComponent() {
    }

    override fun init() {
        mAppInfo = intent.getSerializableExtra("appinfo") as AppInfo
        //AppInfo
        Glide.with(mIconImg).load(BASE_IMG_URL + mAppInfo.icon).into(mIconImg)
        mAppNameTv.text = mAppInfo.displayName
        //mToolbar
        mToolbar.navigationIcon = IconicsDrawable(this)
                .icon(Ionicons.Icon.ion_ios_arrow_back)
                .sizeDp(16)
                .color(ContextCompat.getColor(this, R.color.md_white_1000)
                )
        mToolbar.setNavigationOnClickListener({ finish() })

        val cacheView = (application as BaseApplication).cacheView
        val bitmap = getViewImageCache(cacheView)
        if (bitmap != null) {
            mCacheView.setBackgroundDrawable(BitmapDrawable(cacheView.context.resources, bitmap))
        }

        //缓存view位置绘制
        val location = IntArray(2)
        cacheView.getLocationOnScreen(location)
        val left = location[0]
        val top = location[1] - DensityUtil.getStatusBarH(this)

        val marginLayoutParams = ViewGroup.MarginLayoutParams(mCacheView.layoutParams)
        marginLayoutParams.topMargin = top
        marginLayoutParams.leftMargin = left
        marginLayoutParams.width = cacheView.getWidth()
        marginLayoutParams.height = cacheView.getHeight()

        val params = LinearLayout.LayoutParams(marginLayoutParams)
        mCacheView.layoutParams = params

        val h = DensityUtil.getScreenH(this)
        val animator = ObjectAnimator.ofFloat(mCacheView, "scaleY", 1f, h.toFloat())
        animator.startDelay = 500
        animator.duration = 1000
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                mCacheView.setBackgroundColor(ContextCompat.getColor(this@AppDetailActivity, R.color.md_white_1000))

                //初始化fragment
                val fragment = AppDetailFragment.newInstance(mAppInfo.id)
                val transaction = supportFragmentManager.beginTransaction()
                transaction.add(R.id.view_content, fragment)
                transaction.commitAllowingStateLoss()
            }

            override fun onAnimationEnd(animation: Animator) {
                mCoordinatorLayout.visibility = View.VISIBLE
                mCacheView.visibility = View.GONE
            }
        })
        animator.start()

    }

    override fun setLayout(): Int {
        return R.layout.activity_app_detail
    }


    private fun getViewImageCache(view: View): Bitmap? {
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        var bitmap: Bitmap? = view.drawingCache ?: return null
        bitmap = Bitmap.createBitmap(bitmap!!, 0, 0, bitmap.width, bitmap.height)
        view.destroyDrawingCache()
        return bitmap
    }

}