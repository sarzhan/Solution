package com.youarelaunched.challenge.ui.screen.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youarelaunched.challenge.data.repository.VendorsRepository
import com.youarelaunched.challenge.data.repository.model.Vendor
import com.youarelaunched.challenge.middle.R
import com.youarelaunched.challenge.ui.screen.state.VendorsScreenUiState
import com.youarelaunched.challenge.util.EMPTY
import com.youarelaunched.challenge.util.Result.Error
import com.youarelaunched.challenge.util.Result.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TEXT_ENTERED_DEBOUNCE_MILLIS = 500L

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class VendorsVM @Inject constructor(
    private val repository: VendorsRepository
) : ViewModel() {


    private val queryFlow = MutableStateFlow(String.EMPTY)

    private val _searchResult = MutableStateFlow(VendorsScreenUiState())
    val searchResult: StateFlow<VendorsScreenUiState> = _searchResult.asStateFlow()

    init {
        viewModelScope.launch {
            queryFlow
                .filter { it.length >= 3 || it.isEmpty() }
                .debounce(TEXT_ENTERED_DEBOUNCE_MILLIS)
                .mapLatest { query ->
                    if (query.isEmpty()) {
                        when (val vendorsResult = repository.getAllVendors()) {
                            is Error -> emitErrorState()
                            is Success -> emitSuccessState(vendorsResult.result)
                        }
                    } else {
                        handleQuery(query)
                    }
                }
                .collect()
        }
    }


    fun onNewQuery(query: String) {
        _searchResult.update { value ->
            value.copy(
                query = query,
            )
        }
        queryFlow.value = query
    }

    private suspend fun handleQuery(query: String) {
        emitShowOrHideProgress(query)

        if (query.isNotEmpty()) {
            handleSearchMovie(query)
        }
    }



    private suspend fun handleSearchMovie(query: String) {
        // emulate a small delay in delivering the vendors as it can be quite fast
        delay(1_000L)

        when (val vendorsResult = repository.getVendors(query)) {
            is Error -> emitErrorState()
            is Success -> emitSuccessState(vendorsResult.result )
        }
    }

    private fun emitShowOrHideProgress(query: String) {
        _searchResult.update { value ->
            if (query.isEmpty()) {
                value.copy(isVendorsLoading = false)
            } else {
                value.copy(isVendorsLoading = true)
            }
        }
    }

    private fun emitErrorState() {
        _searchResult.update { value ->
            value.copy(
                isVendorsLoading = false,
                vendors = emptyList(),
                resultPlaceholderTitle = R.string.search_error,
            )
        }
    }

    private fun emitSuccessState(vendors: List<Vendor>) {
        _searchResult.update { value ->
            value.copy(
                isVendorsLoading = false,
                vendors = vendors,
                resultPlaceholderTitle = if (vendors.isEmpty()) R.string.empty_result_title else null,
            )
        }
    }

}