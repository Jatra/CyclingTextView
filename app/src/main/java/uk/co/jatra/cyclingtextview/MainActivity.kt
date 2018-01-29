package uk.co.jatra.cyclingtextview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var tv : CyclingTextView = findViewById(R.id.text)
//        tv.setText(listOf("one", "two", "three", "four"))
    }
}
