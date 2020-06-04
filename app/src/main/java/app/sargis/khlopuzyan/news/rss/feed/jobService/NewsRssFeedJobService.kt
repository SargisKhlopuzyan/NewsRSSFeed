package app.sargis.khlopuzyan.news.rss.feed.jobService

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class NewsRssFeedJobService : JobService() {

    companion object {
        val TAG = "JobService"
    }

    private var jobCanceled = false

    override fun onStartJob(params: JobParameters): Boolean {
        Log.e(TAG, "Job started")
        doBackgroundWork(params)

        // Asuma vor petqa ekran@ pahi min4ev task@ verjacni UI threadum e katarvum
        return true
    }

    override fun onStopJob(params: JobParameters): Boolean {
        Log.e(TAG, "Job canceled before completion")
        jobCanceled = true
        return true
    }

    private fun doBackgroundWork(params: JobParameters) {
        Log.e(TAG, "doBackgroundWork")
        Thread {

            for (i in 0..9) {
                Log.e(TAG, "run: $i")

                if (jobCanceled) {
                    return@Thread
                }

                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    Log.e(TAG, "InterruptedException")
                }
            }

            Log.e(TAG, "Job finished")
            jobFinished(params, true)
        }.start()
    }

}