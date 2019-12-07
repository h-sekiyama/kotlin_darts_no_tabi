package com.test.dartsnotabi

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.httpGet

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.button) as Button
        button.setOnClickListener {
            MyAsyncTask().execute()
        }
    }

    inner class MyAsyncTask : AsyncTask<Void, Int, Void>() {

        override fun onPreExecute() {
            Thread.sleep(800)
        }

        override fun doInBackground(vararg param: Void?): Void? {
            val triple = "https://webservice.recruit.co.jp/manabi/station/v2?prefecture=13&key=3e2b4a21beb30b57&format=json&count=1&start=101".httpGet().response()
            println("result is ：" + String(triple.second.data))
            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            // do nothing
        }

        override fun onPostExecute(result: Void?) {

        }

    }
}