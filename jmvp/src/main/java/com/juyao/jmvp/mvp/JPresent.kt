package com.juyao.jmvp.mvp


/**
 *
 *
 *Created by juyao on 2017/6/29 at 11:12.\n
 * 邮箱:juyao0909@gmail.com
 */


class JPresent<V : IView<Any>>:IPresent<V>{
    private var v:V?=null
    override fun attachV(view: V) {
        v=view
    }
    override fun detachV() {
        v=null
    }
    fun getV():V{
        if (v == null) {
            throw IllegalStateException("v can not be null")
        }
        return v!!
    }


}