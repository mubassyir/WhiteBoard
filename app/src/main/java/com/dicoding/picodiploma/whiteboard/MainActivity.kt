package com.dicoding.picodiploma.whiteboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val MyCanvas = MyCanvas(this)
        container.addView(MyCanvas)


        btn_draw.setOnClickListener{
            MyCanvas.drawCanvas()
        }
        btn_Erase.setOnClickListener{
            MyCanvas.eraseDraw()
        }

    }
}
