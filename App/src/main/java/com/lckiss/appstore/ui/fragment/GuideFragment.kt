package com.lckiss.appstore.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lckiss.appstore.R
import com.lckiss.usercenter.ui.widget.LoadingButton
import kotlinx.android.synthetic.main.fragment_guide.view.*

/**
 * GuideFragment
 */
class GuideFragment : Fragment() {

    companion object {
        const val IMG_ID = "IMG_ID"
        const val COLOR_ID = "COLOR_ID"
        const val TEXT_ID = "TEXT_ID"

        fun newInstance(imgResId: Int, bgColorResId: Int, textResID: Int): GuideFragment {
            val fragment = GuideFragment()
            val bundle = Bundle()
            bundle.putInt(IMG_ID, imgResId)
            bundle.putInt(COLOR_ID, bgColorResId)
            bundle.putInt(TEXT_ID, textResID)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_guide, container, false)

        val args = arguments
        val colorId = args!!.getInt(COLOR_ID)
        val imgId = args.getInt(IMG_ID)
        val textId = args.getInt(TEXT_ID)

        view.mRootView.setBackgroundColor(ContextCompat.getColor(activity!!, colorId))
        view.mImgView.setImageResource(imgId)
        view.mTextTv.setText(textId)
        return  view
    }
}