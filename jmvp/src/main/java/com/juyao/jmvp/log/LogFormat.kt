package com.juyao.jmvp.log

import org.json.JSONArray
import org.json.JSONObject
import java.io.PrintWriter
import java.io.StringReader
import java.io.StringWriter
import java.net.UnknownHostException
import javax.xml.transform.OutputKeys
import javax.xml.transform.Source
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource


/**
 *
 *
 *Created by juyao on 2017/7/11 at 09:58.\n
 * 邮箱:juyao0909@gmail.com
 */


class LogFormat {
    companion object {
        val JSON_INDENT=4
        val XML_INDENT=4
        val VERTICAL_BORDER_CHAR:Char='║'
        val TOP_HORIZONTAL_BORDER="╔═════════════════════════════════════════════════"+
                "══════════════════════════════════════════════════"
        val DIVIDER_HORIZONTAL_BORDER="╟─────────────────────────────────────────────────"+
                "╟─────────────────────────────────────────────────"
        val BOTTOM_HORIZONTAL_BORDER="╚═════════════════════════════════════════════════"+
            "══════════════════════════════════════════════════"
        fun formatJson(json:String):String?{
            var formatted:String?=null
            if(json.isNullOrBlank()){
                return formatted
            }
            try {
                if(json.startsWith("{")){
                    val jo:JSONObject= JSONObject(json)
                    formatted=jo.toString(JSON_INDENT)
                }else if(json.startsWith("[")){
                    val ja:JSONArray= JSONArray(json)
                    formatted=ja.toString(JSON_INDENT)
                }
            }catch (e:Exception){

            }
            return formatted

        }
        fun formatXml(xml:String?):String?{
            var formated:String?=null
            if(xml.isNullOrBlank()){
                return formated
            }
            try {
                val xmlInput:Source=StreamSource(StringReader(xml))
                val xmlOutPut:StreamResult=StreamResult(StringWriter())
                val transformer=TransformerFactory.newInstance().newTransformer()
                transformer.setOutputProperty(OutputKeys.INDENT,"yes")
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
                        XML_INDENT.toString())
                transformer.transform(xmlInput,xmlOutPut)
                formated=xmlOutPut.writer.toString().replaceFirst(">", ">" + JPrinter.lineSeparator)
            }catch (e:Exception){

            }
            return formated
        }
        fun formatThrowable(tr:Throwable?):String{
            if(tr==null){
                return ""
            }
            var t:Throwable?=tr
            while (t!=null){
                if(t is UnknownHostException){
                    return ""
                }
                t=t.cause
            }
            val sw:StringWriter= StringWriter()
            val pw:PrintWriter= PrintWriter(sw)
            tr.printStackTrace(pw)
            pw.flush()
            return sw.toString()
        }

        fun formatArgs(format: String?, vararg args: Any): String {
            if (format != null) {
                return String.format(format, *args)
            } else {
                val sb = StringBuilder()
                var i = 0
                val N = args.size
                while (i < N) {
                    if (i != 0) {
                        sb.append(", ")
                    }
                    sb.append(args[i])
                    i++
                }
                return sb.toString()
            }
        }

        fun formatBorder(segments: Array<String>): String?{
            if (segments == null || segments.size == 0) {
                return ""
            }
            val nonNullSegments = arrayOfNulls<String>(segments.size)
            var nonNullCount = 0
            for (segment in segments) {
                if (segment != null) {
                    nonNullSegments[nonNullCount++] = segment
                }
            }
            if (nonNullCount == 0) {
                return ""
            }

            val msgBuilder = StringBuilder()
            msgBuilder.append(TOP_HORIZONTAL_BORDER).append(JPrinter.lineSeparator)
            for (i in 0..nonNullCount - 1) {
                msgBuilder.append(appendVerticalBorder(nonNullSegments[i]))
                if (i != nonNullCount - 1) {
                    msgBuilder.append(JPrinter.lineSeparator).append(DIVIDER_HORIZONTAL_BORDER)
                            .append(JPrinter.lineSeparator)
                } else {
                    msgBuilder.append(JPrinter.lineSeparator).append(BOTTOM_HORIZONTAL_BORDER)
                }
            }
            return msgBuilder.toString()
        }

        private fun appendVerticalBorder(msg: String?): String {
            val borderedMsgBuilder = StringBuilder(msg!!.length + 10)
            val lines = msg!!.split(JPrinter.lineSeparator.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            var i = 0
            val N = lines.size
            while (i < N) {
                if (i != 0) {
                    borderedMsgBuilder.append(JPrinter.lineSeparator)
                }
                val line = lines[i]
                borderedMsgBuilder.append(VERTICAL_BORDER_CHAR).append(line)
                i++
            }
            return borderedMsgBuilder.toString()
        }


    }
}