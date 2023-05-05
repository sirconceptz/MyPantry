package com.hermanowicz.pantry

import android.app.Application
import android.text.TextUtils
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

private const val TAG = "VolleyPatterns"

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory

    private var requestQueue: RequestQueue? = null
    private lateinit var instance: App

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(hiltWorkerFactory)
            .build()

    fun getInstance(): App {
        return instance
    }

    fun getRequestQueue(): RequestQueue? {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(applicationContext)
        }
        return requestQueue
    }

    fun <T> addEmailToRequestQueue(req: Request<T>, tag: String?) {
        req.tag = if (TextUtils.isEmpty(tag)) TAG else tag
        Timber.d("Adding request to queue: %s", req.url)
        getRequestQueue()?.add(req)
    }

    fun <T> addEmailToRequestQueue(req: Request<T>) {
        req.tag = TAG
        getRequestQueue()?.add(req)
    }

    fun cancelPendingRequests(tag: Any?) {
        if (requestQueue != null) {
            requestQueue?.cancelAll(tag)
        }
    }
}
