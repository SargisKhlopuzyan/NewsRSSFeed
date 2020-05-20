package app.sargis.khlopuzyan.news.rss.feed

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import app.sargis.khlopuzyan.news.rss.feed.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        Log.e("LOG_TAG", "onCreate 1")

        if (savedInstanceState == null) {

            Log.e("LOG_TAG", "onCreate 2")

            supportFragmentManager.beginTransaction().add(
                android.R.id.content,
                MainFragment.newInstance(),
                "fragment_main"
            ).commit()
        }
    }

}