package com.alokomkar.entertainment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alokomkar.entertainment.ui.EntertainViewModel
import javax.inject.Inject

class MainActivity: AppCompatActivity() {

    @Inject
    lateinit var viewModel: EntertainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as EntertainApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}