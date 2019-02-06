package com.lckiss.searchcenter.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lckiss.searchcenter.R
import kotlinx.android.synthetic.main.template_search_history.view.*

/**
 * 搜索历史适配器
 */
class SearchHistoryAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.template_search_history) {

    override fun convert(helper: BaseViewHolder, str: String) {
        helper.itemView.mSearchHistoryItem.text=str
        helper.addOnClickListener(R.id.mSearchHistoryItem)
    }
}