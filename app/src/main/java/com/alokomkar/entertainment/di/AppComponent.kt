package com.alokomkar.entertainment.di

import com.alokomkar.core.di.CoreComponent
import com.alokomkar.entertainment.MainActivity
import com.alokomkar.entertainment.ui.details.DetailsFragment
import com.alokomkar.entertainment.ui.list.ListFragment
import com.alokomkar.entertainment.ui.list.ListModule
import dagger.Component

@ActivityScope
@Component( dependencies = [CoreComponent::class], modules = [ListModule::class])
interface AppComponent {
    fun inject(fragment: ListFragment)
    fun inject(fragment: DetailsFragment)
    fun inject(activity: MainActivity)
}