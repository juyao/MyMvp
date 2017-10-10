package com.juyao.jmvp.mvp

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.View
import butterknife.Unbinder
import com.juyao.jmvp.Config
import com.juyao.jmvp.kit.KnifeKit
import com.tbruyelle.rxpermissions.RxPermissions
import com.trello.rxlifecycle.components.support.RxAppCompatActivity


/**
 *
 *
 *Created by juyao on 2017/6/28 at 16:51.\n
 * 邮箱:juyao0909@gmail.com
 */
abstract class JActivity<out P:IPresent<Any>>: RxAppCompatActivity(),IView<P>{
     var vDelegate:VDelegate? = null
    private var p:P? = null
    lateinit var context:Activity
    lateinit var rxPermission:RxPermissions
    lateinit var unbinder: Unbinder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context=this
        if(getLayouiId()>0){
            setContentView(getLayouiId())
            bindUI(null)
        }
        initData(savedInstanceState)

    }

    override fun bindUI(rootView: View?) {
        unbinder=KnifeKit.bind(this)
    }
    fun getvDelegate():VDelegate?{
        if(vDelegate==null){
            vDelegate=VDelegateBase.create(context)
        }
        return vDelegate
    }

     fun getP(): P? {
        if (p == null) {
            p = newP()
            if (p != null) {
                p!!.attachV(this)
            }
        }
        return p
    }

    override fun onResume() {
        super.onResume()
        getvDelegate()!!.resume()
    }

    override fun onPause() {
        super.onPause()
        getvDelegate()!!.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(getP()!=null){
            getP()!!.detachV()
        }
        getvDelegate()!!.destory()
        p=null
        vDelegate=null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(getOptionsMenuId()>0){
            menuInflater.inflate(getOptionsMenuId(),menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    protected fun getRxPermissions(): RxPermissions {
        rxPermission = RxPermissions(this)
        rxPermission.setLogging(Config.DEV)
        return rxPermission
    }

    override fun getOptionsMenuId(): Int {
        return 0
    }







}

