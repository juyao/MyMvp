package com.juyao.jmvp.mvp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.Unbinder
import com.juyao.jmvp.Config
import com.juyao.jmvp.kit.KnifeKit
import com.tbruyelle.rxpermissions.RxPermissions
import com.trello.rxlifecycle.components.support.RxFragment


/**
 *
 *
 *Created by juyao on 2017/6/29 at 11:20.\n
 * 邮箱:juyao0909@gmail.com
 */


abstract class JFragment<P:IPresent<Any>>: RxFragment(),IView<P>{
    private var vDelegate: VDelegate? = null
    private var p: P? = null
    protected var context: Activity? = null
    private var rootView: View? = null
    protected var layoutInflater: LayoutInflater?= null
    private var rxPermissions: RxPermissions? = null
    private var unbinder: Unbinder? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layoutInflater=inflater
        if(rootView==null && getLayouiId()>0){
            rootView=inflater!!.inflate(getLayouiId(),null)
            bindUI(rootView)
        }else{
            (rootView!!.parent as ViewGroup).removeView(rootView)
        }
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData(savedInstanceState)
    }

    override fun bindUI(rootView: View?) {
        unbinder=KnifeKit.bind(this, rootView!!)
    }

    protected fun getvDelegate(): VDelegate{
        if (vDelegate == null) {
            vDelegate = VDelegateBase.create(context!!)
        }
        return vDelegate!!
    }

    protected fun getP(): P? {
        if (p == null) {
            p = newP()
            if (p != null) {
                p!!.attachV(this)
            }
        }
        return p
    }

    override fun onAttach(con: Context?) {
        super.onAttach(con)
        if(con is Activity){
            context=con
        }

    }

    override fun onDetach() {
        super.onDetach()
        context=null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (getP() != null) {
            getP()!!.detachV()
        }
        getvDelegate().destory()

        p = null
        vDelegate = null
    }

    protected fun getRxPermissions(): RxPermissions {
        rxPermissions = RxPermissions(activity)
        rxPermissions!!.setLogging(Config.DEV)
        return rxPermissions!!
    }

    override fun getOptionsMenuId(): Int {
        return 0
    }


}