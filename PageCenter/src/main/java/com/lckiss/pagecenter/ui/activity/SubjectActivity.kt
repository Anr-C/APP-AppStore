package com.lckiss.pagecenter.ui.activity

import android.support.v4.content.ContextCompat
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.lckiss.baselib.presenter.BasePresenter
import com.lckiss.baselib.ui.activity.BaseActivity
import com.lckiss.pagecenter.R
import com.lckiss.pagecenter.data.protocol.Subject
import com.lckiss.pagecenter.ui.fragment.subject.SubjectDetailFragment
import com.lckiss.pagecenter.ui.fragment.subject.SubjectFragment
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.ionicons_typeface_library.Ionicons
import kotlinx.android.synthetic.main.template_toolbar_framelayout.*

/**
 * 主题界面
 */
class SubjectActivity : BaseActivity<BasePresenter<*, *>>() {

    companion object {
        const val FRAGMENT_SUBJECT = 0
        const val FRAGMENT_DETAIL = 1
    }

    //默认第一个界面
    private var mIndex = FRAGMENT_SUBJECT

    //主题
    private var subjectFragment: SubjectFragment? = null
    //主题详情
    private var subjectDetailFragment: SubjectDetailFragment? = null

    //空实现
    override fun injectComponent() {
    }

    override fun init() {
        mToolbar.navigationIcon = IconicsDrawable(this)
                .icon(Ionicons.Icon.ion_ios_arrow_back)
                .sizeDp(16)
                .color(ContextCompat.getColor(this, R.color.md_white_1000)
                )

        mToolbar.setNavigationOnClickListener { onHandNavigation() }

        RxBus.get().register(this)

        showSubjectFragment()

    }


    override fun setLayout(): Int {
        return R.layout.template_toolbar_framelayout
    }

    /**
     * 返回按钮事件处理
     */
    override fun onBackPressed() {
        onHandNavigation()
    }

    /**
     * 主题界面则finish，否则就显示
     */
    private fun onHandNavigation() {
        if (mIndex == FRAGMENT_SUBJECT) {
            finish()
        } else {
            showSubjectFragment()
        }
    }

    private fun showSubjectFragment() {
        mIndex = FRAGMENT_SUBJECT
        mToolbar.title = "热门专题"
        val ft = supportFragmentManager.beginTransaction()
        //如果有主题页 先隐藏 用于返回状态
        if (subjectFragment != null) {
            ft.remove(subjectFragment)
        }
        //如果有详情页 先隐藏
        if (subjectDetailFragment != null) {
            ft.remove(subjectDetailFragment)
        }
        //如果对象已经释放 则创建
        if (subjectFragment == null) {
            ft.replace(R.id.content_view, SubjectFragment())
        } else {
            ft.show(subjectFragment)
        }

        ft.commitAllowingStateLoss()
    }


    @Subscribe
    fun loadDetailFragment(subject: Subject) {
        mIndex = FRAGMENT_DETAIL
        val ft = supportFragmentManager.beginTransaction()
        if (subjectDetailFragment != null) {
            ft.remove(subjectDetailFragment)
        }
        subjectDetailFragment = SubjectDetailFragment.newInstance(subject)
        ft.add(R.id.content_view, subjectDetailFragment)

        ft.commitAllowingStateLoss()

        mToolbar.title = subject.title
    }

}
