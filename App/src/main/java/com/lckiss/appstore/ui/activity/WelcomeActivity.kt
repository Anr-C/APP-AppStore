package com.lckiss.appstore.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.AccelerateDecelerateInterpolator
import com.eftimoff.androipathview.PathView
import com.lckiss.appstore.R
import com.lckiss.baselib.common.BaseConstant.Companion.IS_SHOW_GUIDE
import com.lckiss.baselib.utils.ACache
import kotlinx.android.synthetic.main.activity_welcome.*
import org.jetbrains.anko.startActivity

/**
 * 欢迎使用界面
 */
class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        mPathView.pathAnimator.delay(20).duration(5000)
                .interpolator(AccelerateDecelerateInterpolator())
                .listenerEnd(PathView.AnimatorBuilder.ListenerEnd {
                    val isSowGuide = ACache.get(this).getAsString(IS_SHOW_GUIDE)
                    if (isSowGuide==null) {
                        startActivity<GuideActivity>()
                    } else {
                        startActivity<MainActivity>()
                    }
                    this.finish()
                }).start()
    }
}