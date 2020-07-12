package com.perqin.andori

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm.isEnabled.observe(this) {
            enableSwitch.isChecked = it
        }

        enableSwitch.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                FloatingService.start(this)
            } else {
                FloatingService.stop(this)
            }
        }
    }
}
