package com.trendyol.uicomponents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.trendyol.timelineview.TimelineItem
import com.trendyol.uicomponents.databinding.ActivityTimelineViewBinding

class TimelineViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTimelineViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_timeline_view
        )

        binding.timelineViewClaim.setItems(createClaimTimelineItems())
        binding.timelineViewShipment.setItems(createShipmentTimelineItems())
        binding.timelineViewClaim.startTooltipAnimation(600, 200f)
    }

    private fun createClaimTimelineItems(): List<TimelineItem> {
        //active
        val item1 = TimelineItem(
            outsideColor = colorWhite,
            insideColor = colorRed,
            text = "İade Talebi",
            textColor = colorRed,
            endLineColor = colorRed
        )

        //active
        val item2 = TimelineItem(
            outsideColor = colorWhite,
            insideColor = colorRed,
            text = "İade Kargoya Verildi",
            textColor = colorRed,
            startLineColor = colorRed,
            endLineColor = colorGray
        )

        //passive
        val item3 = TimelineItem(
            outsideColor = colorGray,
            insideColor = colorWhite,
            text = "İade İnceleniyor",
            textColor = colorGray,
            startLineColor = colorGray,
            endLineColor = colorGray
        )

        //passive
        val item4 = TimelineItem(
            outsideColor = colorGray,
            insideColor = colorWhite,
            text = "İade Edildi",
            textColor = colorGray,
            startLineColor = colorGray,
            endLineColor = colorGray
        )

        val item5 = TimelineItem(
            outsideColor = colorGray,
            insideColor = colorWhite,
            text = "İade İnceleniyor",
            textColor = colorGray,
            startLineColor = colorGray,
            endLineColor = colorGray
        )

        val item6 = TimelineItem(
            outsideColor = colorGray,
            insideColor = colorWhite,
            text = "İade İnceleniyor",
            textColor = colorGray,
            startLineColor = colorGray,
            endLineColor = colorGray
        )

        val item7 = TimelineItem(
            outsideColor = colorGray,
            insideColor = colorWhite,
            text = "İade Edildi",
            textColor = colorGray,
            startLineColor = colorGray
        )
        return listOf(item1, item2, item3, item4, item5, item6, item7)
    }

    private fun createShipmentTimelineItems(): List<TimelineItem> {
        //active
        val item1 = TimelineItem(
            outsideColor = colorWhite,
            insideColor = colorGreen,
            text = "Siparişiniz Alındı",
            textColor = colorGreen,
            endLineColor = colorGreen
        )

        //active
        val item2 = TimelineItem(
            outsideColor = colorWhite,
            insideColor = colorGreen,
            text = "Siparişiniz Hazırlanıyor",
            textColor = colorGreen,
            startLineColor = colorGreen,
            endLineColor = colorGreen
        )

        //passive
        val item3 = TimelineItem(
            outsideColor = colorWhite,
            insideColor = colorGreen,
            text = "Kargoya Verildi",
            textColor = colorGreen,
            startLineColor = colorGreen,
            endLineColor = colorGray
        )

        //passive
        val item4 = TimelineItem(
            outsideColor = colorGray,
            insideColor = colorWhite,
            text = "Teslimat Noktasında",
            textColor = colorGray,
            startLineColor = colorGray,
            endLineColor = colorGray
        )

        val item5 = TimelineItem(
            outsideColor = colorGray,
            insideColor = colorWhite,
            text = "Teslim Edildi",
            textColor = colorGray,
            startLineColor = colorGray
        )
        return listOf(item1, item2, item3, item4, item5)
    }

    companion object {
        private const val colorWhite = "#FFFFFF"
        private const val colorGray = "#E5E5E5"
        private const val colorRed = "#BB0000"
        private const val colorGreen = "#0BC15C"
    }
}
