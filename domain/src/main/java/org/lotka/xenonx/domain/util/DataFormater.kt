package org.lotka.xenonx.domain.util

import java.text.SimpleDateFormat
import java.util.Locale

object DataFormater {
    fun timeStampToFormatString(timeStamp:Long , pattern:String) : String {
        return SimpleDateFormat(pattern, Locale.getDefault()).run {
            format(timeStamp)
        }
    }

}