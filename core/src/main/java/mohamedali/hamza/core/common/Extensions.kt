package mohamedali.hamza.core.common

import android.util.Log
import mohamedali.hamza.core.models.MyResponse
import okio.IOException
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

 val simpleDateFormat by lazy { SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()) }

inline fun <T> Response<T>.onSuccess(
    action: (T) -> Unit
): Response<T> {
    if (isSuccessful) body()?.run(action)
    return this
}

inline fun <T> Response<T>.onFailure(
    action: (Any) -> Unit
) {
    if (!isSuccessful) {
        Log.e("code request ${this.code()}", this.errorBody()?.string() ?: "error unknown")
        errorBody()?.run {
            action(this.string())
        }
    }
}

fun <T, R : Any> Response<T>.data(
    mapTo: (T) -> R
): MyResponse<R> {
    try {
        onSuccess {
            return MyResponse.SuccessResponse(mapTo(it))
        }
        onFailure {
            return MyResponse.ErrorResponse(it)
        }
        return MyResponse.ErrorResponse(
            error = Exception("GENERAL_NETWORK_ERROR")
        )
    } catch (e1: IOException) {
        return MyResponse.ErrorResponse(
            error = IOException("GENERAL_NETWORK_ERROR")
        )
    } catch (e: Exception) {
        return MyResponse.ErrorResponse(
            error = Exception(e.fillInStackTrace().message)
        )
    }

}