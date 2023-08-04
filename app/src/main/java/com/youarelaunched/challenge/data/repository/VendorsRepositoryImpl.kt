package com.youarelaunched.challenge.data.repository

import com.youarelaunched.challenge.data.network.api.ApiVendors
import com.youarelaunched.challenge.data.network.models.NetworkCategory
import com.youarelaunched.challenge.data.network.models.NetworkVendor
import com.youarelaunched.challenge.data.repository.model.Vendor
import com.youarelaunched.challenge.data.repository.model.VendorCategory
import com.youarelaunched.challenge.di.DispatcherIo
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.text.Typography.bullet
import com.youarelaunched.challenge.util.Result
import com.youarelaunched.challenge.util.mapError
import com.youarelaunched.challenge.util.mapSuccess

class VendorsRepositoryImpl @Inject constructor(
    @DispatcherIo private val workDispatcher: CoroutineDispatcher,
    private val api: ApiVendors
) : VendorsRepository {

    override suspend fun getAllVendors(): Result<List<Vendor>, Throwable> = withContext(workDispatcher) {
        return@withContext api.getVendors().mapSuccess { networkVendors ->
            networkVendors.map { networkVendor -> networkVendor.toVendor() }
        }.mapError { it }
    }

    override suspend fun getVendors(query: String): Result<List<Vendor>, Throwable> =
        withContext(workDispatcher) {
            return@withContext api.getVendors().mapSuccess { networkVendors ->
                networkVendors
                    .filter { it.companyName.contains(query, ignoreCase = true) }
                    .map { networkVendor -> networkVendor.toVendor() }
            }.mapError { it }
        }

    private suspend fun NetworkVendor.toVendor() = coroutineScope {

        val categories = categories?.map {
            async {
                it.toCategory()
            }
        }

        val tags = tags?.let { tagsList ->
            async {
                buildString {
                    tagsList.forEach { tag ->
                        append(bullet)
                        append(" ")
                        append(tag.name)
                        append(" ")
                    }
                }
            }
        }

        Vendor(
            id = id,
            companyName = companyName,
            coverPhoto = coverPhoto.mediaUrl,
            area = areaServed,
            favorite = favorite,
            categories = categories?.awaitAll(),
            tags = tags?.await()
        )
    }

    private fun NetworkCategory.toCategory() =
        VendorCategory(
            id = id,
            title = name,
            imgUrl = image.mediaUrl
        )

}