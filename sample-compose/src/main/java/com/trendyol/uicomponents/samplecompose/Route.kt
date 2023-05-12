package com.trendyol.uicomponents.samplecompose

sealed class Route(val destination: String) {

    object Components : Route("components")

    object TimelineView : Route("timelineView")
}
