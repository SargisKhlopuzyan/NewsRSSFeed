package app.sargis.khlopuzyan.news.rss.feed.util

import android.content.Context
import android.os.StrictMode
import android.util.Log
import app.sargis.khlopuzyan.news.rss.feed.App
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class CacheManager {

    companion object {

        fun downloadFile(guid: String?, url: String?): String? {

            guid?.let {
                val connection = URL(url).openConnection() as HttpURLConnection

                val policy: StrictMode.ThreadPolicy =
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy)

                var fileName: String? = null
                try {
                    val data = connection.inputStream.bufferedReader().use { it.readText() }
                    fileName = writeToFileInInternalStorage(guid, data)
                } finally {
                    connection.disconnect()
                }

                Thread.sleep(4000)

                Log.e("LOG_TAG", "searchNewsDetails: 2.1")
                return fileName
            }

            Log.e("LOG_TAG", "searchNewsDetails: 2.2")
            return null
        }

        fun deleteFile(guid: String?, url: String?): Boolean {
            guid?.let {
                val fileName = generateUrlFromGUID(guid)
                fileName?.let {
                    if (isCacheAvailable(fileName)) {
                        return File(fileName).delete()
                    }
                }
            }
            return false
        }

        private fun writeToFileInInternalStorage(guid: String, data: String): String? {
            val fileName = generateUrlFromGUID(guid)
            val fileOutputStream: FileOutputStream
            return try {
                fileOutputStream = App.getContext().openFileOutput(fileName, Context.MODE_PRIVATE)
                fileOutputStream.write(data.toByteArray())
                fileName
            } catch (e: Exception) {
                null
            }
        }

        private fun generateUrlFromGUID(guid: String?): String? {
//            var name = guid?.substringBeforeLast("/")?.substringAfterLast("/")
            var name = guid?.substringBeforeLast("/")
            name = name?.substringAfterLast("/")
            return name
        }


        fun readFileContentFromInternalStorage(guid: String?): String? {
            val fileName = generateUrlFromGUID(guid)

            try {
                val fileInputStream: FileInputStream? = App.getContext().openFileInput(fileName)
                val inputStreamReader = InputStreamReader(fileInputStream as InputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder: StringBuilder = StringBuilder()
                var text: String? = null

                while ({ text = bufferedReader.readLine(); text }() != null) {
                    stringBuilder.append(text)
                }

                return stringBuilder.toString()

            } catch (e: FileNotFoundException) {
                return null
            }
        }

        private fun isCacheAvailable(fileName: String): Boolean {
            return File(fileName).exists()
        }
    }
}