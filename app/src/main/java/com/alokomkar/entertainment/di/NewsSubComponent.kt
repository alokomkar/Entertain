package com.alokomkar.entertainment.di

import com.alokomkar.core.di.CoreComponent
import com.alokomkar.entertainment.MainActivity
import com.alokomkar.entertainment.ui.list.ListModule
import dagger.Component

@ActivityScope
@Component( dependencies = [CoreComponent::class], modules = [ListModule::class])
interface NewsSubComponent {
    fun inject( activity: MainActivity )
}