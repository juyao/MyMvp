package com.juyao.jmvp.log

import android.text.TextUtils
import android.util.Log
import com.juyao.jmvp.Config


/**
 *
 *
 *Created by juyao on 2017/7/10 at 11:31.\n
 * 邮箱:juyao0909@gmail.com
 */


class JLog {
    companion object {
        val LOG=Config.LOG
        val TAG_ROOT:String=Config.LOG_TAG
        fun json(json:String){
            json(Log.DEBUG,null,json)
        }
        fun json(loglevel:Int,tag:String?,json:String){
            if(LOG){
                val formatJson = LogFormat.formatBorder(arrayOf<String>(LogFormat.formatJson(json)!!))
                JPrinter.println(loglevel,tag?: TAG_ROOT,formatJson)
            }
        }

        fun xml(xml:String){
            xml(Log.DEBUG,null,xml)
        }
        fun xml(loglevel: Int,tag:String?,xml:String){
            if(LOG){
                val formatxml=LogFormat.formatBorder(arrayOf<String>(LogFormat.formatXml(xml)!!))
                JPrinter.println(loglevel,tag?: TAG_ROOT,formatxml)
            }
        }

        fun error(throwable: Throwable) {
            error(null, throwable)
        }

        fun error(tag: String?, throwable: Throwable) {
            if (LOG) {
                val formatError = LogFormat.formatBorder(arrayOf(LogFormat.formatThrowable(throwable)))
                JPrinter.println(Log.ERROR, if (TextUtils.isEmpty(tag)) TAG_ROOT else tag, formatError)
            }
        }

        private fun msg(logLevel: Int, tag: String?, format: String, vararg args: Any) {
            if (LOG) {
                val formatMsg = LogFormat.formatBorder(arrayOf(LogFormat.formatArgs(format, args)))
                JPrinter.println(logLevel, if (TextUtils.isEmpty(tag)) TAG_ROOT else tag, formatMsg)
            }
        }

        fun d(msg: String, vararg args: Any) {
            msg(Log.DEBUG, null, msg, *args)
        }

        fun d(tag: String, msg: String, vararg args: Any) {
            msg(Log.DEBUG, tag, msg, *args)
        }

        fun e(msg: String, vararg args: Any) {
            msg(Log.ERROR, null, msg, *args)
        }

        fun e(tag: String, msg: String, vararg args: Any) {
            msg(Log.ERROR, tag, msg, *args)
        }

    }

}