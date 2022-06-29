package ru.korobeynikov.p1201clickwidget

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.browser.*

class MyBrowser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.browser)

        webView.webViewClient=WebViewClient()
        var data=""
        val extras=intent.extras
        if(extras!=null)
            data=extras.getString("site","")
        webView.loadUrl(data)
    }
}