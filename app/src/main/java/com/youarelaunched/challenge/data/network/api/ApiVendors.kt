package com.youarelaunched.challenge.data.network.api

import com.youarelaunched.challenge.data.network.models.NetworkVendor
import com.youarelaunched.challenge.util.Result

interface ApiVendors {

    suspend fun getVendors(): Result<List<NetworkVendor>, Throwable>
}