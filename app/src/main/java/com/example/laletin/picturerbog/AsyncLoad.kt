import android.graphics.Bitmap
import android.os.AsyncTask
import com.example.laletin.picturerbog.*

class AsyncLoad(private val cache: CacheImages) : AsyncTask<Int, Void, Pair<Bitmap?, String?>?>() {
    override fun doInBackground(vararg params: Int?): Pair<Bitmap?, String?>? {
        try {
            cache.onLoad()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}