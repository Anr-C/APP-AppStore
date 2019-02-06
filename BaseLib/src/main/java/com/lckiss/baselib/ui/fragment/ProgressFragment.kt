package com.lckiss.baselib.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.hwangjr.rxbus.RxBus
import com.lckiss.baselib.R
import com.lckiss.baselib.common.BaseApplication
import com.lckiss.baselib.injection.component.AppComponent
import com.lckiss.baselib.presenter.BasePresenter
import com.lckiss.baselib.presenter.view.BaseView
import kotlinx.android.synthetic.main.fragment_progress.*
import kotlinx.android.synthetic.main.fragment_progress.view.*
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * 带加载 进度的BaseFragment
 */
abstract class ProgressFragment<T : BasePresenter<*, *>> : Fragment(), BaseView {

    @Inject
    lateinit var mRetrofit: Retrofit

    @Inject
    lateinit var mPresenter: T

    lateinit var mRootView: FrameLayout

    lateinit var mApplication: BaseApplication


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mRootView = inflater.inflate(R.layout.fragment_progress, container, false) as FrameLayout

        //当前页面为空时的点击事件
        mRootView.mEmptyView.setOnClickListener(getOnclickListener())

        return mRootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        RxBus.get().register(this)

        mApplication=activity?.application as BaseApplication

        injectComponent(mApplication.appComponent)

        setRealContentView()

        init()
    }

    private fun setRealContentView() {
        LayoutInflater.from(activity).inflate(setLayout(), mContentView, true);
    }

    abstract fun injectComponent(appComponent: AppComponent)

    abstract fun init()

    abstract fun setLayout(): Int

    override fun showLoading() {
        showView(R.id.mProgressView)
    }

    override fun showError(msg: String) {
        showView(R.id.mEmptyView)
        mRootView.mErrorTv.text=msg
    }

    override fun dismissLoading() {
        showView(R.id.mContentView)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.get().unregister(this)
    }

    private fun showView(viewId: Int) {
        for (i in 0 until mRootView.childCount) {
            if (mRootView.getChildAt(i).id == viewId) {
                mRootView.getChildAt(i).visibility = View.VISIBLE
            } else {
                mRootView.getChildAt(i).visibility = View.GONE
            }
        }

    }

    abstract fun getOnclickListener(): View.OnClickListener
}