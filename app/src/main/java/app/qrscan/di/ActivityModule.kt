package app.qrscan.di

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import app.qrscan.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun providesNavController(activity: Activity): NavController =
            activity.findNavController(R.id.nav_host_fragment)
}
