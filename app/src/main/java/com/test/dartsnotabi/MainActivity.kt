package com.test.dartsnotabi

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {

    val URL = "https://webservice.recruit.co.jp/manabi/station/v2?prefecture=13&key=3e2b4a21beb30b57&format=json&count=1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var loadButton:Button = this.findViewById(R.id.main_load)
        loadButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}