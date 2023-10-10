package ru.korobeynikov.p13networkprofiler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import ru.korobeynikov.p13networkprofiler.databinding.ActivityMainBinding
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.view = this
    }

    fun clickDownload() {
        val urls = LinkedList<String>()
        Collections.addAll(urls,
            "https://fikiwiki.com/uploads/posts/2022-02/1644929466_35-fikiwiki-com-p-reki-krasivie-kartinki-41.jpg",
            "https://linkisteel.ru/wp-content/uploads/b/5/0/b501abaf37a31477354bc34b96161813.jpeg",
            "https://vsegda-pomnim.com/uploads/posts/2022-03/1648665199_5-vsegda-pomnim-com-p-reki-i-ozera-rossii-foto-5.jpg",
            "https://vsegda-pomnim.com/uploads/posts/2022-03/1648664133_29-vsegda-pomnim-com-p-lesa-i-reki-rossii-foto-30.jpg",
            "https://vsegda-pomnim.com/uploads/posts/2022-03/1647166733_62-vsegda-pomnim-com-p-reka-krasivaya-foto-66.jpg",
            "https://vsegda-pomnim.com/uploads/posts/2022-03/1647166689_4-vsegda-pomnim-com-p-reka-krasivaya-foto-4.jpg",
            "https://fikiwiki.com/uploads/posts/2022-02/1644929431_11-fikiwiki-com-p-reki-krasivie-kartinki-12.jpg",
            "https://oir.mobi/uploads/posts/2021-06/1623242382_7-oir_mobi-p-krasivie-reki-rossii-priroda-krasivo-foto-9.jpg")
        val executor = Executors.newFixedThreadPool(5)
        for (url in urls)
            executor.submit {
                download(url)
                try {
                    TimeUnit.SECONDS.sleep(2)
                } catch (e: InterruptedException) {}
            }
    }

    private fun download(urlString: String) {
        var urlConnection: HttpURLConnection? = null
        try {
            urlConnection = URL(urlString).openConnection() as HttpURLConnection
            val inputStream = BufferedInputStream(urlConnection.inputStream)
            while (inputStream.read() != -1) {}
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            urlConnection?.disconnect()
        }
    }
}