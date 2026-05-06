package ru.korobeynikov.p0321simplebrowser

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun BrowserScreen(url: String) {
    Column {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()
                }
            },
            update = { webView ->
                webView.loadUrl(url)
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}