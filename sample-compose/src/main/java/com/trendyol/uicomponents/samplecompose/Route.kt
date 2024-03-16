package com.trendyol.uicomponents.samplecompose

sealed class Route(val destination: String) {

    object Components : Route("components")

    object TimelineView : Route("timelineView")

    object RatingBar : Route("ratingbar")

    object Shimmer : Route("shimmer")
}
