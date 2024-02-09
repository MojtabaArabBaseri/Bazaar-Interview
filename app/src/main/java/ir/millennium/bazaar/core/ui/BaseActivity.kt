package ir.millennium.bazaar.core.ui

import android.content.Context
import android.content.res.Configuration
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint
import io.github.inflationx.viewpump.ViewPumpContextWrapper

@AndroidEntryPoint
abstract class BaseActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        val newConfiguration = Configuration(newBase.resources?.configuration)
        newConfiguration.fontScale = 1.0f
        applyOverrideConfiguration(newConfiguration)
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }
}
