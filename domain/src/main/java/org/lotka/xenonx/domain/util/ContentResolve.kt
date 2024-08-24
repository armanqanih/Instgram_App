package org.lotka.xenonx.domain.util

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import androidx.customview.widget.Openable

fun ContentResolver.getFileName(uri: Uri): String {
    val returnCursor = query(uri, null, null, null, null)
    val nameIndex = returnCursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor?.moveToFirst()
    val fileName = returnCursor?.getString(nameIndex ?: 0)
    returnCursor?.close()
    return fileName ?: ""


}