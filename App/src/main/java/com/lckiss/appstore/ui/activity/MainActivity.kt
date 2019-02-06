package com.lckiss.appstore.ui.activity

import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.lckiss.appcenter.ui.activity.AppManagerActivity
import com.lckiss.appstore.R
import com.lckiss.provider.data.protocol.FragmentInfo
import com.lckiss.appstore.injection.component.DaggerMainComponent
import com.lckiss.appstore.injection.module.MainModule
import com.lckiss.appstore.presenter.MainPresenter
import com.lckiss.appstore.presenter.contract.MainContract
import com.lckiss.appstore.ui.adapter.ViewPagerAdapter
import com.lckiss.appstore.ui.widget.BadgeActionProvider
import com.lckiss.appstore.ui.widget.SearchActionProvider
import com.lckiss.baselib.common.BaseApplication
import com.lckiss.baselib.common.BaseConstant
import com.lckiss.baselib.common.BaseConstant.Companion.POSITION
import com.lckiss.baselib.common.BaseConstant.Companion.TOKEN
import com.lckiss.baselib.common.BaseConstant.Companion.USER
import com.lckiss.baselib.common.font.CustomFont
import com.lckiss.baselib.ui.activity.BaseActivity
import com.lckiss.baselib.utils.ACache
import com.lckiss.pagecenter.ui.fragment.CategoryFragment
import com.lckiss.pagecenter.ui.fragment.GamesFragment
import com.lckiss.pagecenter.ui.fragment.RecommendFragment
import com.lckiss.pagecenter.ui.fragment.TopListFragment
import com.lckiss.provider.utils.getSettingKey
import com.lckiss.searchcenter.ui.activity.SearchActivity
import com.lckiss.usercenter.data.protocol.User
import com.lckiss.usercenter.ui.activity.LoginActivity
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.ionicons_typeface_library.Ionicons
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_header.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.ArrayList

class MainActivity : BaseActivity<MainPresenter>(), MainContract.MainView {

    private lateinit var mHeaderView: View
    private lateinit var mBadgeActionProvider: BadgeActionProvider
    private lateinit var mAdapter: ViewPagerAdapter

    override fun injectComponent() {

        DaggerMainComponent.builder().appComponent((application as BaseApplication).appComponent)
                .mainModule(MainModule(this)).build().inject(this)
    }

    override fun init() {
        RxBus.get().register(this)
        mPresenter.requestPermission()
        if (getSettingKey(this, R.string.key_auto_check_apk_update, true)) {
            mPresenter.getAppUpdateInfo()
        }
    }

    override fun setLayout(): Int {
        return R.layout.activity_main
    }

    override fun requestPermissionSuccess() {
        initDrawerLayout()
        initTabLayout()
        initUser()
    }

    /**
     * 初始化用户数据
     */
    private fun initUser() {
        var userObj: Any? = null
        try {
            userObj = ACache.get(this).getAsObject(USER)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (userObj == null) {
            mHeaderView.setOnClickListener{
                startActivity<LoginActivity>()
            }
        } else {
            val user = userObj as User
            initUserHeadView(user)
        }
    }

    private fun initTabLayout() {
        val fragments = ArrayList<FragmentInfo>(4)
        fragments.add(FragmentInfo("推荐", RecommendFragment::class.java))
        fragments.add(FragmentInfo("排行", TopListFragment::class.java))
        fragments.add(FragmentInfo("游戏", GamesFragment::class.java))
        fragments.add(FragmentInfo("分类", CategoryFragment::class.java))

        mAdapter = ViewPagerAdapter(supportFragmentManager, fragments)
        mViewPager.offscreenPageLimit = mAdapter.count
        mViewPager.adapter = mAdapter

        mTabLayout.setupWithViewPager(mViewPager)

    }


    private fun initDrawerLayout() {
        mHeaderView = mNavigationView.getHeaderView(0)

        mHeaderView.mUserHeadView.setImageDrawable(IconicsDrawable(this, CustomFont.Icon.lckiss_head).colorRes(R.color.md_white_1000))

        mNavigationView.menu.findItem(R.id.menu_app_update).icon = IconicsDrawable(this, Ionicons.Icon.ion_ios_loop)
        mNavigationView.menu.findItem(R.id.menu_download_manager).icon = IconicsDrawable(this, CustomFont.Icon.lckiss_download)
        mNavigationView.menu.findItem(R.id.menu_app_uninstall).icon = IconicsDrawable(this, Ionicons.Icon.ion_ios_trash_outline)
        mNavigationView.menu.findItem(R.id.menu_setting).icon = IconicsDrawable(this, Ionicons.Icon.ion_ios_gear_outline)
        mNavigationView.menu.findItem(R.id.menu_logout).icon = IconicsDrawable(this, CustomFont.Icon.lckiss_shutdown)

        mNavigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_logout -> logout()
                R.id.menu_app_update ->
                    startActivity<AppManagerActivity>(POSITION to 2)
                R.id.menu_download_manager ->
                startActivity<AppManagerActivity>()
                R.id.menu_app_uninstall ->
                    startActivity<AppManagerActivity>(POSITION to 3)
                R.id.menu_setting ->
                startActivity<SettingActivity>()
                else -> {
                }
            }
            false
        })
        //ToolBar part
        mToolbar.inflateMenu(R.menu.toolbar_menu)

        val mSearchMenuItem = mToolbar.menu.findItem(R.id.action_search)

        val mSearchActionProvider = MenuItemCompat.getActionProvider(mSearchMenuItem) as SearchActionProvider
        mSearchActionProvider.setIcon(DrawableCompat.wrap(IconicsDrawable(this, Ionicons.Icon.ion_ios_search)
                .color(ContextCompat.getColor(this, R.color.md_white_1000))))

        mSearchActionProvider.setOnClickListener(View.OnClickListener {
                            startActivity<SearchActivity>()
        })

        val mDownloadMenuItem = mToolbar.menu.findItem(R.id.action_download)

        mBadgeActionProvider = MenuItemCompat.getActionProvider(mDownloadMenuItem) as BadgeActionProvider
        mBadgeActionProvider.setIcon(DrawableCompat.wrap(IconicsDrawable(this, CustomFont.Icon.lckiss_download)
                .color(ContextCompat.getColor(this, R.color.md_white_1000))))

        mBadgeActionProvider.setOnClickListener(View.OnClickListener {

            if (mBadgeActionProvider.getBadgeNum() > 0) {
                                    startActivity<AppManagerActivity>(POSITION to 2)

            } else {
                                    startActivity<AppManagerActivity>(POSITION to 0)
            }
        })

        //mDrawerToggle init
        val mDrawerToggle = ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.toggle_open, R.string.toggle_close)
        mDrawerToggle.syncState()
        mDrawerLayout.addDrawerListener(mDrawerToggle)
    }

    override fun requestPermissionFail() {
        toast("授权失败，请前往设置开启")
    }


    //公共方法区域

    override fun changeAppNeedUpdateCount(count: Int) {
        mBadgeActionProvider.setText(count.toString() + "")
        if (count < 1) {
            mBadgeActionProvider.hideBadge()
        }
    }

    /**
     * 退出操作
     */
    private fun logout() {
        val aCache = ACache.get(this)

        val objUser = ACache.get(this).getAsObject(USER)
        if (objUser == null) {
            toast("您暂未登录，无需退出登录")
            return
        }

        aCache.put(TOKEN, "")
        aCache.put(USER, "")

        mHeaderView.mUserHeadView.setImageDrawable(IconicsDrawable(this, CustomFont.Icon.lckiss_head).colorRes(R.color.md_white_1000))
        mHeaderView.mTextUserName.text = "未登录"

        mHeaderView.setOnClickListener {
            startActivity<LoginActivity>()
        }

        toast("您已退出登录")

        //通知刷新界面
        refreshFragment()
    }

    /**
     * 初始化头像，昵称
     */
    private fun initUserHeadView(user: User) {
        Glide.with(this).load("https:" + user.logo_url).apply(bitmapTransform(CircleCrop()))
                .into(mHeaderView.mUserHeadView)
        mHeaderView.mTextUserName.text = user.username

        refreshFragment()
    }

    //RxBus通知区域

    /**
     * 发送刷新界面通知
     */
    private fun refreshFragment() {
        RxBus.get().post(BaseConstant.Companion.EventType.TAG_REFRESH, "all")
    }

    /**
     * 监听从安装广播发送过了包名
     *
     * @param packageName 包名
     */
    @Subscribe(tags = [Tag(BaseConstant.Companion.EventType.TAG_APP_CHANGED)])
    fun refresh(packageName: String) {
        mPresenter.getAppUpdateInfo()
    }

    /**
     * 处理登录后的事件
     */
    @Subscribe(tags = arrayOf(Tag(BaseConstant.Companion.EventType.TAG_LOGIN)))
    fun getUser(user: User) {
        initUserHeadView(user)
    }

    /**
     * 界面销毁处理
     */
    override fun onDestroy() {
        super.onDestroy()
        RxBus.get().unregister(this)
    }

}
