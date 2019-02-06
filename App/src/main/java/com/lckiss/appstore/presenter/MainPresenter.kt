package com.lckiss.appstore.presenter

import android.Manifest.permission.READ_PHONE_STATE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import com.google.gson.Gson
import com.lckiss.provider.data.protocol.Apk
import com.lckiss.provider.utils.getInstalledApps
import com.lckiss.appstore.presenter.contract.MainContract
import com.lckiss.baselib.common.BaseConstant.Companion.APP_UPDATE_LIST
import com.lckiss.baselib.common.compatResult
import com.lckiss.baselib.presenter.BasePresenter
import com.lckiss.baselib.rx.observer.ProgressObserver
import com.lckiss.baselib.ui.activity.BaseActivity
import com.lckiss.baselib.utils.ACache
import com.lckiss.provider.data.protocol.AppInfo
import com.lckiss.provider.data.protocol.AppsUpdateReq
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import javax.inject.Inject

/**
 * MainPresenter 业务逻辑
 */
class MainPresenter @Inject constructor(mModel: MainContract.IMainModel, mView: MainContract.MainView) : BasePresenter<MainContract.IMainModel, MainContract.MainView>(mModel, mView) {

    private val mPerModel: MainContract.IMainModel=mModel
    private val mPerView:MainContract.MainView=mView

    fun requestPermission(){
        RxPermissions(mContext as BaseActivity<*>).request(WRITE_EXTERNAL_STORAGE,READ_PHONE_STATE).subscribe {
            if (it){
                mView.requestPermissionSuccess()
            }else{
                mView.requestPermissionFail()
            }
        }
    }

    /**
     * 获取需要更新的App列表
     */
    fun getAppUpdateInfo(){
        Observable.create(ObservableOnSubscribe<AppsUpdateReq> { e ->
            e.onNext(buildParams(getInstalledApps(mContext)))
            e.onComplete()
        }).flatMap {
            mPerModel.getUpdateApps(it).compatResult()
        }.subscribe(object :ProgressObserver<List<AppInfo>>(mContext,mPerView){
            override fun onNext(t: List<AppInfo>) {
                if (t != null) {
                    ACache.get(mContext).put(APP_UPDATE_LIST, Gson().toJson(t))
                }
                mPerView.changeAppNeedUpdateCount(if (t == null) 0 else t.size)
            }
        })

    }

    /**
     * 用于在getAppUpdateInfo()方法中 拼凑请求参数
     */
    private fun buildParams(installedApks: List<Apk>): AppsUpdateReq {
        val packageNameBuilder = StringBuilder()
        val versionCodeBuilder = StringBuilder()
        installedApks.map {
            if (!it.isSystem) {
                packageNameBuilder.append("${it.packageName},")
                versionCodeBuilder.append("${it.appVersionCode},")
            }
        }
        return AppsUpdateReq(packageNameBuilder.toString(),versionCodeBuilder.toString())
    }
}