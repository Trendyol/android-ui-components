package com.trendyol.uicomponents.samplecompose

sealed class Route(val destination: String) {

    data object Components : Route("components")

    data object TimelineView : Route("timelineView")

    data object RatingBar : Route("ratingbar")

    data object QuantityPicker : Route("quantityPicker")
}
