package app.sargis.khlopuzyan.news.rss.feed

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import app.sargis.khlopuzyan.news.rss.feed.jobService.NewsRssFeedJobService
import app.sargis.khlopuzyan.news.rss.feed.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        scheduleJob()

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(
                    R.id.content,
                    MainFragment.newInstance(),
                    "fragment_main"
                )
            }
        }
    }

    private fun scheduleJob() {
        val componentName = ComponentName(this, NewsRssFeedJobService::class.java)
        val jobInfo = JobInfo.Builder(123, componentName)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setPersisted(true)
//            .setPeriodic(30 * 1000)
            .setMinimumLatency(30 * 1000)
            .setOverrideDeadline(3 * 1000)
            .build()

        val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler

        val resultCode = jobScheduler.schedule(jobInfo)

        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.e(NewsRssFeedJobService.TAG, "Job Scheduled")
        } else {
            Log.e(NewsRssFeedJobService.TAG, "Job Scheduling failed")
        }
    }

    private fun cancelJob() {
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.cancel(123)
        Log.e(NewsRssFeedJobService.TAG, "Job Canceled")
    }
}