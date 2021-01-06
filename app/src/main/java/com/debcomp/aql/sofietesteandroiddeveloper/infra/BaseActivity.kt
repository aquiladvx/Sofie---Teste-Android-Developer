package com.debcomp.aql.sofietesteandroiddeveloper.infra

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


/*
 * Davi Áquila
 * aquiladvx
 *
 * 05/01/2021
 *
 */

var isLoading = false
lateinit var loader: Loader

open class BaseActivity: AppCompatActivity() {
    init {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun showLoading() {
        if(!isLoading) {
            loader = Loader()
            loader.show(supportFragmentManager, "loading")
            isLoading = true
        }
    }

    fun hideLoading() {
        if (isLoading) {
            loader.dismiss()
            isLoading = false
        }
    }
}