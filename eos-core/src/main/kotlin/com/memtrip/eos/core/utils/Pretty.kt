package com.memtrip.eos.core.utils

import java.math.RoundingMode
import java.text.DecimalFormat

object Pretty {

    fun net(value: Long): String {
        if (value == -1L) {
            return "unlimited"
        } else {
            var unit = "bytes"
            var bytes = value.toFloat()

            if (bytes >= 1024 * 1024 * 1024 * 1024L) {
                unit = "TiB"
                bytes /= 1024 * 1024 * 1024 * 1024L
            } else if (bytes >= 1024 * 1024 * 1024) {
                unit = "GiB"
                bytes /= 1024 * 1024 * 1024
            } else if (bytes >= 1024 * 1024) {
                unit = "MiB"
                bytes /= 1024 * 1024
            } else if (bytes >= 1024) {
                unit = "KiB"
                bytes /= 1024
            }

            return "${roundNet(bytes)} $unit"
        }
    }

    fun ram(value: Long): String {
        val floatValue = value.toFloat()
        return roundRAM(floatValue / 1000)
    }

    fun cpu(value: Long): String {
        if (value == -1L) {
            return "unlimited"
        } else {

            var unit = ""
            var micro = value.toFloat()

            if (micro > 1000000*60*60L) {
                micro /= 1000000*60*60L
                unit = "hr"
            }

            if (micro > 1000000*60) {
                micro /= 1000000*60
                unit = "min"
            }

            if (micro > 1000000) {
                micro /= 1000000
                unit = "sec"
            }

            if (micro > 1000) {
                micro /= 1000
                unit = "ms"
            }

            return "${roundCPU(micro)} $unit"
        }
    }

    private fun roundNet(value: Float): String = with (DecimalFormat("#.##")) {
        roundingMode = RoundingMode.CEILING
        format(value)
    }

    private fun roundRAM(value: Float): String = with (DecimalFormat("#.##")) {
        roundingMode = RoundingMode.CEILING
        format(value)
    }

    private fun roundCPU(value: Float): String = with (DecimalFormat("#.###")) {
        roundingMode = RoundingMode.CEILING
        format(value)
    }
}