package com.lckiss.usercenter.ui.widget

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.widget.RelativeLayout
import com.lckiss.usercenter.R
import kotlinx.android.synthetic.main.view_loading_button.view.*

/**
 * LoadingButton
 */

class LoadingButton : RelativeLayout {

    //默认文字大小
    private var mDefaultTextSize: Int = 0
    //加载时文字
    private var mLoadingText: String? = null
    //按钮文字
    private var mButtonText: String? = null
    //文字大小
    var textSize: Int = 0
        private set
    //文字颜色
    private var mTextColor: Int = 0
    //是否显示按钮
    var isLoadingShowing: Boolean = false
        private set
    //文字样式
    var typeface: Typeface? = null
    //可选条件
    private val inRight: Animation? = null
    private val inLeft: Animation? = null
    private var mCurrentInDirection: Int = 0
    private val mTextSwitcherReady: Boolean = false

    companion object {
        //内部静态常量
        internal const val DEFAULT_COLOR = android.R.color.white
        const val IN_FROM_RIGHT = 0
        const val IN_FROM_LEFT = 1
    }

    //注册构造函数
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }


    private fun init(context: Context, attrs: AttributeSet?) {
        //获取默认设置的文字大小
        mDefaultTextSize = resources.getDimensionPixelSize(R.dimen.text_default_size)
        //默认关闭加载
        isLoadingShowing = false
        //加载视图
        LayoutInflater.from(getContext()).inflate(R.layout.view_loading_button, this, true)

        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.LoadingButton, 0, 0)

            try {
                textSize = typedArray.getDimensionPixelSize(R.styleable.LoadingButton_textSize, mDefaultTextSize)
                //设置自定义按钮文字
                setText(typedArray.getString(R.styleable.LoadingButton_text))
                //设置自定义加载文字
                mLoadingText = typedArray.getString(R.styleable.LoadingButton_loadingText)
                if (mLoadingText == null) {
                    mLoadingText = getContext().getString(R.string.default_loading)
                }
                //设置进度条颜色与文字颜色
                setProgressColor(typedArray.getColor(R.styleable.LoadingButton_progressColor, ContextCompat.getColor(context,DEFAULT_COLOR)))
                setTextColor(typedArray.getColor(R.styleable.LoadingButton_textColor, ContextCompat.getColor(context,DEFAULT_COLOR)))

            } finally {
                typedArray.recycle()
            }
        } else {
            mLoadingText = getContext().getString(R.string.default_loading)
            setProgressColor(ContextCompat.getColor(context,DEFAULT_COLOR))
            setTextColor(ContextCompat.getColor(context,DEFAULT_COLOR))
            textSize = mDefaultTextSize
        }


    }

    /**
     * 设置进度条颜色
     */
    private fun setProgressColor(colorRes: Int) {
        mProgressBar.indeterminateDrawable
                .mutate()
                .setColorFilter(colorRes, PorterDuff.Mode.SRC_ATOP)
    }
    /**
     * 设置文字颜色
     */
    private fun setTextColor(textColor: Int) {
        this.mTextColor = textColor
        mTextView.setTextColor(textColor)
    }

    /**
     *  设置动画时长
     */
    fun setAnimationInDirection(inDirection: Int) {
        mCurrentInDirection = inDirection
    }

    /**
     * 文字设置
     */
    private fun setText(text: String?) {
        if (text != null) {
            mButtonText = text
            mTextView!!.text = text
        }
    }

    /**
     * 加载文字设置
     */
    fun setLoadingText(text: String?) {
        if (text != null) {
            mLoadingText = text
        }
    }


    /**
     * 显示进度条
     */
    fun showLoading() {
        if (!isLoadingShowing) {
            mProgressBar.visibility = View.VISIBLE
            mTextView.text = mLoadingText
            isLoadingShowing = true
            isEnabled = false
        }
    }
    /**
     * 显示按钮文字
     */
    fun showButtonText() {
        if (isLoadingShowing) {
            mProgressBar.visibility = View.INVISIBLE
            mTextView.text = mButtonText
            isLoadingShowing = false
            isEnabled = true
        }
    }

}