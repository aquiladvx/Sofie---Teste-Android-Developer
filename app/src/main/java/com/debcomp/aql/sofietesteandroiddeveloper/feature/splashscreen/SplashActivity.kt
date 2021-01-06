package com.debcomp.aql.sofietesteandroiddeveloper.feature.splashscreen

import android.content.Intent
import android.os.Bundle
import com.debcomp.aql.sofietesteandroiddeveloper.R
import com.debcomp.aql.sofietesteandroiddeveloper.feature.task.taskHome.presentation.HomeActivity
import com.debcomp.aql.sofietesteandroiddeveloper.infra.BaseActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {

    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        activityScope.launch {
            delay(2500)

            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}