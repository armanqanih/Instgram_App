package org.lotka.xenonx.presentation.util

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.content.FileProvider
import com.yalantis.ucrop.UCrop
import org.lotka.xenonx.domain.util.getFileName
import java.io.File
import java.util.UUID

class CropActivityResultContract() :ActivityResultContract<Uri,Uri?>() {

    override fun createIntent(context: Context, input: Uri): Intent {
        return UCrop.of(input,
            Uri.fromFile(
                File(context.cacheDir,
                context.contentResolver.getFileName(input)
                ))
        )
            .withAspectRatio(16f, 9f)
            .getIntent(context)

    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return if (resultCode == RESULT_OK && intent != null) {
            UCrop.getOutput(intent)
        } else {
            null
        }
    }

}

