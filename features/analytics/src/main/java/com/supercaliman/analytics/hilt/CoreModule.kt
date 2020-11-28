package com.supercaliman.analytics.hilt

import android.content.Context
import com.supercaliman.analytics.SegmentHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object CoreModule {
    @Provides
    @Singleton
    fun getSegment(@ApplicationContext context: Context): SegmentHelper {
        return SegmentHelper(context.applicationContext)
    }
}