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
    }

    private fun createClaimTimelineItems(): List<TimelineItem> {
        //active
        val item1 = TimelineItem(
            outsideColor = colorWhite,
            insideColor = colorRed,
            text = "İade Talebi",
            textColor = colorRed,
            rightLineColor = colorRed
        )

        //active
        val item2 = TimelineItem(
            outsideColor = colorWhite,
            insideColor = colorRed,
            text = "İade Kargoya Verildi",
            textColor = colorRed,
            leftLineColor = colorRed,
            rightLineColor = colorGray
        )

        //passive
        val item3 = TimelineItem(
            outsideColor = colorGray,
            insideColor = colorWhite,
            text = "İade İnceleniyor",
            textColor = colorGray,
            leftLineColor = colorGray,
            rightLineColor = colorGray
        )

        //passive
        val item4 = TimelineItem(
            outsideColor = colorGray,
            insideColor = colorWhite,
            text = "İade Edildi",
            textColor = colorGray,
            leftLineColor = colorGray
        )
        return listOf(item1, item2, item3, item4)
    }

    private fun createShipmentTimelineItems(): List<TimelineItem> {
        //active
        val item1 = TimelineItem(
            outsideColor = colorWhite,
            insideColor = colorGreen,
            text = "Siparişiniz Alındı",
            textColor = colorGreen,
            rightLineColor = colorGreen
        )

        //active
        val item2 = TimelineItem(
            outsideColor = colorWhite,
            insideColor = colorGreen,
            text = "Siparişiniz Hazırlanıyor",
            textColor = colorGreen,
            leftLineColor = colorGreen,
            rightLineColor = colorGreen
        )

        //passive
        val item3 = TimelineItem(
            outsideColor = colorWhite,
            insideColor = colorGreen,
            text = "Kargoya Verildi",
            textColor = colorGreen,
            leftLineColor = colorGreen,
            rightLineColor = colorGray
        )

        //passive
        val item4 = TimelineItem(
            outsideColor = colorGray,
            insideColor = colorWhite,
            text = "Teslim Edildi",
            textColor = colorGray,
            leftLineColor = colorGray
        )
        return listOf(item1, item2, item3, item4)
    }

    companion object {
        private const val colorWhite = "#FFFFFF"
        private const val colorGray = "#E5E5E5"
        private const val colorRed = "#BB0000"
        private const val colorGreen = "#0BC15C"
    }
}
