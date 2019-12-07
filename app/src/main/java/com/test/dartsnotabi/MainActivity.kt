package com.test.dartsnotabi

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import org.json.JSONObject
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.IOException
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var stationName: String
    lateinit var stationText: TextView
    var randomInt: Int = 0
    lateinit var resultJson: JSONObject
    lateinit var accessor: HttpAccessor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stationText = findViewById(R.id.station_textview)
        val updateButton: Button = findViewById(R.id.button)
        updateButton.setOnClickListener {
            MyAsyncTask().execute()
        }

        val copyButton: Button = findViewById(R.id.copy)
        copyButton.setOnClickListener {
            val clip: ClipData = ClipData.newPlainText("simple text", stationName)
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class MyAsyncTask : AsyncTask<Void, Int, Void>() {

        override fun onPreExecute() {
            Thread.sleep(500)
        }

        @SuppressLint("WrongThread")
        override fun doInBackground(vararg param: Void?): Void? {
            randomInt = Random.nextInt(935)
            accessor = HttpAccessor()
            resultJson =
                accessor.getJson("https://webservice.recruit.co.jp/manabi/station/v2?prefecture=13&key=3e2b4a21beb30b57&format=json&count=1&start=$randomInt")
            stationName = readJsonNode(resultJson.toString())
            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            // do nothing
        }

        override fun onPostExecute(result: Void?) {
            stationText.text = stationName
        }

    }
}

@Throws(IOException::class)
private fun readJsonNode(json: String): String {
    val mapper = ObjectMapper()

    // JSON文字列を読み込み、JsonNodeオブジェクトに変換（Fileやbyte[]も引数に取れる）
    val root = mapper.readTree(json)

    return root.get("results").get("station").get(0).get("name").toString()
}

class HttpAccessor {
    fun getJson(url: String): JSONObject {
        val (_, _, result) = url.httpGet().responseJson()

        return when (result) {
            is Result.Failure -> {
                val ex = result.getException()

                JSONObject(mapOf("results" to ex.toString()))
            }
            is Result.Success -> {
                result.get().obj()
            }
        }
    }
}