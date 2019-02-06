package com.lckiss.searchcenter.ui.activity

import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.lckiss.baselib.common.BaseApplication
import com.lckiss.baselib.common.hideKeyBoard
import com.lckiss.baselib.ui.activity.BaseActivity
import com.lckiss.provider.ui.adapter.AppInfoAdapter
import com.lckiss.provider.ui.widget.DividerItemDecoration
import com.lckiss.searchcenter.R
import com.lckiss.searchcenter.data.protocol.SearchResp
import com.lckiss.searchcenter.injection.component.DaggerSearchComponent
import com.lckiss.searchcenter.injection.module.SearchModule
import com.lckiss.searchcenter.presenter.SearchPresenter
import com.lckiss.searchcenter.presenter.contract.SearchContract
import com.lckiss.searchcenter.ui.adapter.SearchHistoryAdapter
import com.lckiss.searchcenter.ui.adapter.SuggestionAdapter
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.ionicons_typeface_library.Ionicons
import kotlinx.android.synthetic.main.activity_search.*
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.lckiss.baselib.common.execute
import com.lckiss.baselib.router.RouterPath
import com.lckiss.provider.ui.widget.SpaceItemDecoration
import io.reactivex.Observable
import java.io.Serializable
import java.util.concurrent.TimeUnit


/**
 * 搜索界面
 */
class SearchActivity : BaseActivity<SearchPresenter>(), SearchContract.ISearchView {

    private lateinit var mHistoryAdapter: SearchHistoryAdapter
    private lateinit var mSuggestionAdapter: SuggestionAdapter
    private lateinit var mAppInfoAdapter: AppInfoAdapter

    private var mTempSearchKey:String=""

    override fun injectComponent() {
        DaggerSearchComponent.builder().appComponent((application as BaseApplication).appComponent)
                .searchModule(SearchModule(this))
                .build()
                .inject(this)
    }

    override fun init() {

        initView()
        initRv()

        mPresenter.showHistory()

        mKeyWordsClearBtn.setOnClickListener {
            //隐藏清除按钮
            mKeyWordsClearBtn.visibility = View.GONE
            mSearchEt.setText("")
            //显示搜索历史
            mPresenter.showHistory()

            mHistoryRl.visibility = View.VISIBLE
            mSuggestionRv.visibility = View.GONE
            mResultRv.visibility = View.GONE
        }

        mSearchEt.setOnEditorActionListener { v, _, _->
            search(v.text.toString().trim())
            false
        }

        //监听字符变动
        val mKeyWordsObservable =
                Observable.create<String> {
                    mSearchEt.addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(s: Editable?) {
                        }

                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            it.onNext(s.toString().trim())
                        }
                    })
                }

        mKeyWordsObservable.debounce(400,TimeUnit.MICROSECONDS).filter {
            it.isNotEmpty()
        }.execute()
                .subscribe {
                    if (it == mTempSearchKey){
                        return@subscribe
                    }
                    mTempSearchKey=it

                    if (it.isNotEmpty()) {
                        mKeyWordsClearBtn.visibility = View.VISIBLE
                        mPresenter.getSuggestions(it)
                    } else {
                        mKeyWordsClearBtn.visibility = View.GONE
                    }
        }
    }

    private fun initView() {
        mToolbar.navigationIcon = IconicsDrawable(this)
                .icon(Ionicons.Icon.ion_ios_arrow_back)
                .sizeDp(16)
                .color(ContextCompat.getColor(this, R.color.md_white_1000)
                )
        mToolbar.setNavigationOnClickListener { finish() }

        mKeyWordsClearBtn.setImageDrawable(IconicsDrawable(this, Ionicons.Icon.ion_ios_close_empty)
                .color(ContextCompat.getColor(this, R.color.md_white_1000)).sizeDp(16))

        mHistoryClearBtn.setImageDrawable(IconicsDrawable(this, Ionicons.Icon.ion_backspace_outline)
                .color(ContextCompat.getColor(this, R.color.md_grey_600)).sizeDp(16))

        mHistoryClearBtn.setOnClickListener {
            //历史记录清理
            mHistoryAdapter.setNewData(ArrayList<String>())
            mHistoryAdapter.notifyDataSetChanged()
            mPresenter.cleanHistory()
        }
    }

    private fun initRv(){
        //初始化搜索建议
        mSuggestionAdapter = SuggestionAdapter()
        mSuggestionRv.layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST)
        mSuggestionRv.addItemDecoration(itemDecoration)
        mSuggestionRv.adapter = mSuggestionAdapter
        mSuggestionRv.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                val keyWord: String = mSuggestionAdapter.getItem(position)!!
                search(keyWord)
            }
        })

        //处理搜索结果
        mAppInfoAdapter = AppInfoAdapter.builder().showBrief(false).showCategoryName(true).retrofit(mRetrofit).build()
        mResultRv.layoutManager = LinearLayoutManager(this)
        mResultRv.addItemDecoration(itemDecoration)
        mResultRv.adapter = mAppInfoAdapter
        mResultRv.addOnItemTouchListener(object : OnItemClickListener() {
            override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                //缓存View
                (application as BaseApplication).cacheView = view
                //跳转
                ARouter.getInstance().build(RouterPath.PageCenter.PATH_DETAIL)
                        .withSerializable("appinfo", adapter.getItem(position) as Serializable)
                        .navigation()
            }
        })

        //搜索历史
        mHistoryAdapter = SearchHistoryAdapter()
        mHistoryRv.addItemDecoration(SpaceItemDecoration(3))
        mHistoryRv.layoutManager = GridLayoutManager(this, 6)
        mHistoryRv.adapter = mHistoryAdapter
        mHistoryRv.addOnItemTouchListener(object : OnItemChildClickListener() {
            override fun onSimpleItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
                val keyWord:String=mHistoryAdapter.getItem(position)!!
                search(keyWord)
            }
        })
    }

    override fun setLayout(): Int {
        return R.layout.activity_search
    }

    override fun showSearchHistory(list: List<String>) {
        mHistoryAdapter.setNewData(list)
        mSuggestionRv.visibility = View.GONE
        mHistoryRl.visibility = View.VISIBLE
        mResultRv.visibility = View.GONE
    }

    override fun showSuggestions(list: List<String>) {
        mSuggestionAdapter.setNewData(list)
        mSuggestionRv.visibility = View.VISIBLE
        mHistoryRl.visibility = View.GONE
        mResultRv.visibility = View.GONE
    }

    override fun showSearchResult(result: SearchResp) {
        mAppInfoAdapter.setNewData(result.listApp)
        mSuggestionRv.visibility = View.GONE
        mHistoryRl.visibility = View.GONE
        mResultRv.visibility = View.VISIBLE
    }

    fun search(keyWord: String) {
        if (keyWord.isEmpty()){
            return
        }
        mPresenter.search(keyWord)
        mSearchEt.setText(keyWord)
        //隐藏软键盘
        mSearchEt.hideKeyBoard()
    }

}