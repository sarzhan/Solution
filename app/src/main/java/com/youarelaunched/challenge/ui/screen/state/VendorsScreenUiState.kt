package com.youarelaunched.challenge.ui.screen.state

import com.youarelaunched.challenge.data.repository.model.Vendor

data class VendorsScreenUiState(
    val query: String = "",
    val isVendorsLoading: Boolean = false,
    val resultPlaceholderTitle: Int? = null,
    val vendors: List<Vendor> = emptyList(),
)
