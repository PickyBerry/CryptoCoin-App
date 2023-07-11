package com.pickyberry.rtuitlab_recruit.domain

interface NetworkChecker {
    fun isNetworkAvailable(): Boolean
}