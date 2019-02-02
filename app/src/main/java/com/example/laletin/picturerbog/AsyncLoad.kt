import android.graphics.Bitmap
import android.os.AsyncTask
import com.example.laletin.picturerbog.*

class AsyncLoad(private val cache: CacheImages) : AsyncTask<Void, Void, Void?>() {
    override fun doInBackground(vararg params: Void): Void? {
        try {
            cache.onLoad()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}