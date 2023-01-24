package ru.korobeynikov.p0321simplebrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p0321simplebrowser.databinding.ActivityBrowserBinding

class BrowserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityBrowserBinding>(this,
            R.layout.activity_browser)
        val webView = binding.webView
        webView.webViewClient = WebViewClient()
        val data = intent.data
        webView.loadUrl(data.toString())
    }
}