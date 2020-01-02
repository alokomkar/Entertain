package com.alokomkar.entertainment

import android.app.Application
import com.alokomkar.core.di.AppModule
import com.alokomkar.core.di.CoreComponent
import com.alokomkar.core.di.DaggerCoreComponent
import com.alokomkar.entertainment.di.AppComponent
import com.alokomkar.entertainment.di.DaggerAppComponent

class EntertainApplication : Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.builder().appModule(AppModule(this)).build()
    }

    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        DaggerAppComponent.builder().coreComponent(coreComponent).build()
    }

}