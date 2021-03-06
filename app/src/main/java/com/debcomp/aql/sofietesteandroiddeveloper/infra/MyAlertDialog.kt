package com.debcomp.aql.sofietesteandroiddeveloper.infra

import android.app.Activity
import android.app.Dialog
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import com.debcomp.aql.sofietesteandroiddeveloper.R


/*
 * Davi Áquila
 * aquiladvx
 *
 * 05/01/2021
 *
 */

open class MyAlertDialog : AppCompatDialogFragment() {

    companion object {

        /**
         * Metodo utilizado para comunicar algum aviso ao usuario,
         * Titulo definido como "Ops" por default.
         *
         * @param txt           Texto do dialogo.
         * @param activity      Activity.
         *
         */
        fun showWarningDialog(txt: String, activity: Activity) {
            val dialog = Dialog(activity)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_warning)

            val body = dialog.findViewById(R.id.txt_dialog) as TextView
            body.text = txt
            val yesBtn = dialog.findViewById(R.id.btnOk) as Button
            yesBtn.setOnClickListener {
                dialog.dismiss()
            }
            val lp = WindowManager.LayoutParams()
            lp.copyFrom(dialog.window?.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            dialog.show()
            dialog.window?.attributes = lp
        }

        /**
         * Metodo utilizado para comunicar algum aviso ao usuario,
         * onde ele tenha uma opcao de escolha.
         *
         * Titulo definido como "Opa!" por default.
         *
         * @param txt           Texto do dialogo.
         * @param activity      Activity.
         *
         * @return Dialog instance.
         */
        fun showWarningChoiceDialog(txt: String, activity: Activity): Dialog {
            val dialog = Dialog(activity)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_choice_warning)

            val body = dialog.findViewById(R.id.txt_dialog) as TextView
            body.text = txt
            val lp = WindowManager.LayoutParams()
            lp.copyFrom(dialog.window?.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            dialog.window?.attributes = lp
            return dialog
        }
    }

}