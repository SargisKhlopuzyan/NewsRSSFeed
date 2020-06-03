package app.sargis.khlopuzyan.news.rss.feed.util

import app.sargis.khlopuzyan.news.rss.feed.App
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class CacheManager {

    companion object {

        fun downloadFile(guid: String, url: String?): String? {
            val connection = URL(url).openConnection() as HttpURLConnection
            val fileName: String?
            try {
                val data = connection.inputStream.bufferedReader().use { it.readText() }
                fileName = writeToFileInInternalStorage(guid, data)
            } finally {
                connection.disconnect()
            }
            return fileName
        }

        fun deleteFile(guid: String): Boolean {
            val cacheDir = getCacheFileDir()
            val fileName = generateFileCacheNameFromGUID(guid)
            val file = getFileCacheAbsolutePath(cacheDir, fileName)
            return File(file).delete()
        }

        private fun writeToFileInInternalStorage(guid: String, data: String): String? {

            val cacheDir = getCacheFileDir()

            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
            }

            val fileName = generateFileCacheNameFromGUID(guid)
            val file = getFileCacheAbsolutePath(cacheDir, fileName)

            val fileOutputStream: FileOutputStream
            return try {
                fileOutputStream = FileOutputStream(file)
                fileOutputStream.write(data.toByteArray())
                fileName
            } catch (e: Exception) {
                null
            }
        }

        private fun generateFileCacheNameFromGUID(guid: String): String? {
            return guid.substringBeforeLast("/").substringAfterLast("/")
        }

        private fun getCacheFileDir(): File {
            val root: String = App.getContext().getExternalFilesDir(null).toString()
            return File("$root/rss")
        }

        private fun getFileCacheAbsolutePath(dir: File, fileName: String?): String {
            return "$dir/${fileName}.txt"
        }

        fun readFileContentFromInternalStorage(guid: String?): String? {

            guid?.let {
                val fileName = generateFileCacheNameFromGUID(guid)

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
                }
            }

            return null
        }
    }
}