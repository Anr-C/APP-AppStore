package com.lckiss.pagecenter.ui.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lckiss.baselib.common.BaseConstant.Companion.BASE_IMG_URL
import com.lckiss.pagecenter.R
import com.lckiss.pagecenter.data.protocol.Category
import kotlinx.android.synthetic.main.template_category.view.*

/**
 * CategoryAdapter
 */
class CategoryAdapter : BaseQuickAdapter<Category, BaseViewHolder>(R.layout.template_category) {
    override fun convert(helper: BaseViewHolder, item: Category) {
        helper.itemView.mNameText.text=item.name
        Glide.with(helper.itemView.mIconImg.context).load(BASE_IMG_URL + item.icon).into(helper.itemView.mIconImg)
    }

}