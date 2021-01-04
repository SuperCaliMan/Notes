package com.supercaliman.analytics

import android.content.Context
import com.segment.analytics.Analytics
import com.segment.analytics.Properties
import javax.inject.Inject

class SegmentHelper @Inject constructor(private val context: Context) {

    init {
        val segment =
            Analytics.Builder(context, BuildConfig.SEGMENT_CONFIG).build()
        Analytics.setSingletonInstance(segment)
    }


    fun trackScreen(screenName: String) {
        Analytics.with(context).screen(screenName)
    }

    fun trackEvent(eventName: String, properties: Map<String, String> = mapOf()) {
        val segmentProp = Properties()
        segmentProp.putAll(properties.mapKeys {
            it.key
        })
        Analytics.with(context).track(eventName, segmentProp)
    }


}