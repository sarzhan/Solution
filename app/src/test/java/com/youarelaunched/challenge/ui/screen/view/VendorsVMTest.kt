package com.youarelaunched.challenge.ui.screen.view

import com.youarelaunched.challenge.data.repository.VendorsRepository
import com.youarelaunched.challenge.util.Result
import com.youarelaunched.challenge.data.repository.model.Vendor
import com.youarelaunched.challenge.data.repository.model.VendorCategory
import com.youarelaunched.challenge.middle.R
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import com.youarelaunched.challenge.testutils.ViewModelTest


class VendorsVMTest : ViewModelTest() {

    private lateinit var vendorsRepository: VendorsRepository
    private lateinit var viewModel: VendorsVM

    @Before
    fun setup() {
        vendorsRepository = mockk()
        viewModel = VendorsVM(vendorsRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when data is loaded successfully, state should contain at least one vendor`() = runTest {
        // Arrange
        val vendors = createTestVendor()

        coEvery { vendorsRepository.getAllVendors() } returns Result.Success(vendors)

        // Act
        triggerLoadOfData()
        advanceTimeBy(1000)

        // Assert
        val state = viewModel.searchResult.first()
        assertEquals("Vendor list should not be empty", vendors, state.vendors)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when data loading fails, state should reflect an error`() = runTest {
        // Arrange
        coEvery { vendorsRepository.getAllVendors() } returns Result.Error(Exception("Test error"))

        // Act
        triggerLoadOfData()
        advanceTimeBy(1000)

        // Assert
        val state = viewModel.searchResult.value
        assertEquals("Placeholder title should be set to error", R.string.search_error, state.resultPlaceholderTitle)
    }

    private fun triggerLoadOfData() = viewModel.onNewQuery("")

    private fun createTestVendor(): List<Vendor> {
        return listOf(
            Vendor(
                id = 1,
                companyName = "Test Company",
                coverPhoto = "test_cover_photo_url",
                area = "Test Area",
                favorite = true,
                categories = listOf(
                    VendorCategory(
                        id = 1,
                        title = "Test Category",
                        imgUrl = "test_category_img_url"
                    )
                ),
                tags = "test"
            )
        )
    }
}
