package com.youarelaunched.challenge.data.repository

import com.youarelaunched.challenge.data.repository.model.Vendor
import com.youarelaunched.challenge.util.Result

interface VendorsRepository {

    suspend fun getVendors(query: String): Result<List<Vendor>, Throwable>
    suspend fun getAllVendors(): Result<List<Vendor>, Throwable>
}