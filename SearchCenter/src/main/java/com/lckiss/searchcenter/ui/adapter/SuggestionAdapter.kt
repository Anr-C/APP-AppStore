package com.lckiss.searchcenter.ui.adapter

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lckiss.searchcenter.R
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.ionicons_typeface_library.Ionicons
import kotlinx.android.synthetic.main.suggest_item.view.*

/**
 * 搜索建议适配器
 */
class SuggestionAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.suggest_item) {

    override fun convert(helper: BaseViewHolder, str: String) {
        helper.itemView.mSuggestionIconImg.setImageDrawable(IconicsDrawable(mContext, Ionicons.Icon.ion_ios_search)
                .color(ContextCompat.getColor(mContext, R.color.md_grey_400)).sizeDp(16))
        helper.itemView.mSuggestionTv.text=str
    }
}