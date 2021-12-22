package ru.korobeynikov.p0321simplebrowser

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.browser.*

class BrowserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.browser)

        webView.webViewClient=WebViewClient()
        val data=intent.data
        webView.loadUrl(data.toString())
    }
}