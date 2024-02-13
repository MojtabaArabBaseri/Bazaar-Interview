package ir.millennium.bazaar.core.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class AuxiliaryFunctionsManager @Inject constructor(@ApplicationContext val context: Context) {

    fun getVersionAndroid(): String {
        val builder = StringBuilder()
        builder.append("Android ").append(Build.VERSION.RELEASE)
        val fields = VERSION_CODES::class.java.fields
        for (field in fields) {
            val fieldName = field.name
            var fieldValue = -1
            try {
                fieldValue = field.getInt(Any())
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: NullPointerException) {
                e.printStackTrace()
            }
            if (fieldValue == Build.VERSION.SDK_INT) {
                builder.append(" : ").append(fieldName).append(" : ")
                builder.append("sdk=").append(fieldValue)
            }
        }
        return builder.toString()
    }

    open fun isNetworkConnected(): Boolean {
        val cm =
            context.getSystemService(MultiDexApplication.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }
}