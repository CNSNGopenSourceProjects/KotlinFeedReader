package br.com.conseng.kotlinfeedreader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import java.net.URL

/**
 * Faz o download da imagem do artigo na Internet e salva diretamento na tela.
 */
class DownloadImageTask (val imageView:ImageView) : AsyncTask<String, Void, Bitmap>() {
    /**
     * Perform the background thread to get the image bitmap from Internet.
     * @param [params] The parameters of the task:
     * params[0] = image URL
     * @return The downloaded image bitmap.
     */
    override fun doInBackground(vararg params: String?): Bitmap {
        val url = params[0]
        val stream = URL(url).openStream()
        val bitmap = BitmapFactory.decodeStream(stream)

        return bitmap
    }

    /**
     * Runs on the UI thread after [doInBackground].
     * The specified result is the value returned by [doInBackground].
     * @param [result] The result of the operation computed by [doInBackground].
     */
    override fun onPostExecute(result: Bitmap?) {
        imageView.setImageBitmap(result)
    }
}