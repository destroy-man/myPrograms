package com.korobeynikov.rssnews;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;
public class RssItemDisplayer extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rss_item_displayer);

        RssItem selectedRssItem = MainActivity.selectedRssItem;

        WebView wb=findViewById(R.id.wbRss);
        wb.loadUrl(selectedRssItem.getLink());

        wb.setWebViewClient(new WebViewClient(){
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //Users will be notified in case there an error (i.e. no internet connection)
                Toast.makeText(RssItemDisplayer.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }

            public void onPageFinished(WebView view, String url) {
                CookieSyncManager.getInstance().sync();
            }
        });
    }
}
