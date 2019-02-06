package com.lckiss.appstore.ui.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v4.view.ActionProvider
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import com.lckiss.appstore.R
import kotlinx.android.synthetic.main.menu_badge_provider.view.*

/**
 * BadgeActionProvider
 */
class BadgeActionProvider(context: Context) : ActionProvider(context) {

    private lateinit var onClickListener: View.OnClickListener

    private lateinit var view:View

    override fun onCreateActionView(): View {
        val size = context.resources.getDimensionPixelSize(android.support.design.R.dimen.abc_action_bar_default_height_material)
        val layoutParams = ViewGroup.LayoutParams(size, size)
        view = LayoutInflater.from(context).inflate(R.layout.menu_badge_provider, null, false)
        view.layoutParams = layoutParams

        view.setOnClickListener(BadgeMenuClickListener())

        return view
    }

    fun setIcon(drawable: Drawable) {
        view.mIcon.setImageDrawable(drawable)
    }

    fun setIcon(@DrawableRes res: Int) {
        view.mIcon.setImageResource(res)
    }

    //设置显示的文字
    fun setText(i: CharSequence) {
        showBadge()
        view.mTxtBadge.text = i
    }

    fun getBadgeNum(): Int {
        try {
            return Integer.parseInt(view.mTxtBadge.text.toString())
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            return 0
        }

    }

    fun hideBadge() {
        view.mTxtBadge.visibility = GONE
    }

    fun showBadge() {
        view.mTxtBadge.visibility = VISIBLE
    }

    fun setOnClickListener(onClickListener: View.OnClickListener) {
        this.onClickListener = onClickListener
    }

    private inner class BadgeMenuClickListener : View.OnClickListener {
        override fun onClick(v: View) {
                onClickListener.onClick(v)
        }
    }
}