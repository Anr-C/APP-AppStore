package com.lckiss.pagecenter.ui.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lckiss.baselib.common.BaseConstant.Companion.BASE_IMG_URL
import com.lckiss.pagecenter.R
import com.lckiss.pagecenter.data.protocol.Subject
import kotlinx.android.synthetic.main.template_imageview_subject.view.*

/**
 * SubjectAdapter
 */
class SubjectAdapter : BaseQuickAdapter<Subject, BaseViewHolder>(R.layout.template_imageview_subject) {
    override fun convert(helper: BaseViewHolder, item: Subject) {
        Glide.with(mContext).load(BASE_IMG_URL + item.mticon).into(helper.itemView.mImageView)
    }

}