package com.debcomp.aql.sofietesteandroiddeveloper.infra

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.debcomp.aql.sofietesteandroiddeveloper.R


/*
 * Davi Áquila
 *
 * 05/09/2020
 *
 */

class Loader: DialogFragment() {

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.loading, container, false)
        if (dialog != null) {
            if (dialog!!.window != null) {
                dialog!!.window
                    ?.setBackgroundDrawable(ColorDrawable(Color.argb(150, 0, 0, 0)))
                dialog!!.window!!.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
                dialog!!.window!!.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT
                )
            }
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.setCancelable(false)
            dialog!!.setCanceledOnTouchOutside(false)
            dialog!!.setOnKeyListener { dialog: DialogInterface?, keyCode: Int, event: KeyEvent? -> keyCode == KeyEvent.KEYCODE_BACK }
        }
        return v
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            STYLE_NORMAL,
            R.style.FullScreen
        )
    }
}