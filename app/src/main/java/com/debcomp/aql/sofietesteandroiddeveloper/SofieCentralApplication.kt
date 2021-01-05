package com.debcomp.aql.sofietesteandroiddeveloper

import android.app.Application


/*
 * Davi Áquila
 * aquiladvx
 *
 * 05/01/2021
 *
 */

class SofieCentralApplication: Application() {

    companion object {
        lateinit var instance: SofieCentralApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}