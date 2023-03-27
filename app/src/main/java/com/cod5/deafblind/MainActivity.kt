
// The authors disclaim copyright to this source code

package com.cod5.deafblind

import android.os.Build
import android.os.Vibrator;
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.VibratorManager
import android.webkit.JavascriptInterface
import android.webkit.WebView
import androidx.core.content.getSystemService
import com.cod5.deafblind.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.web.settings.javaScriptEnabled = true
        binding.web.loadUrl("file:///android_res/raw/index.html")
        binding.web.addJavascriptInterface(MyJs(this), "Android")
    }

    class MyJs(private val self:MainActivity) {
        @JavascriptInterface
        fun cb(id: String, event: String, err: String, data: String): String {
            val r = self.cbToJNI(id, event, err, data)
            if (r.startsWith("vibrate:")) {
                val s = r.substring(8, r.indexOf(';'))
                try {
                    val l = s.toLong()
                    if (l >= 10 && l <= 1000) {
                        self.vibrate(l)
                    }
                    return r.substring(r.indexOf(';') + 1)
                } catch (e:Exception) {

                }
            }
            return r;
        }
    }
    private external fun cbToJNI(id: String, event: String, err: String, data:String): String


    fun vibrate(tms: Long) {
        try {
            val v: Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    val vibratorService =
                        this.getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
                    v = vibratorService.defaultVibrator
                } else {
                    @Suppress("DEPRECATION")
                    v = getSystemService(VIBRATOR_SERVICE) as Vibrator
                }
                v.vibrate(VibrationEffect.createOneShot(tms, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v = getSystemService(VIBRATOR_SERVICE) as Vibrator
                @Suppress("DEPRECATION")
                v.vibrate(tms)
            }
        } catch (e: Exception) {

        }
    }

    companion object {
        init {
            System.loadLibrary("deafblind")
        }
    }
}