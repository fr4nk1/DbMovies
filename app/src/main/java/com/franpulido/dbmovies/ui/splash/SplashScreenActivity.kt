package com.franpulido.dbmovies.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.franpulido.dbmovies.ui.common.startActivity
import com.franpulido.dbmovies.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity<MainActivity> {}
        finishAfterTransition()
    }

}
