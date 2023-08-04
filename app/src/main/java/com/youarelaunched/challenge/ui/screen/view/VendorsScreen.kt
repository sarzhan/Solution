package com.youarelaunched.challenge.ui.screen.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youarelaunched.challenge.middle.R
import com.youarelaunched.challenge.data.repository.model.Vendor
import com.youarelaunched.challenge.ui.screen.view.components.ChatsumerSnackbar
import com.youarelaunched.challenge.ui.screen.view.components.SearchProgressView
import com.youarelaunched.challenge.ui.screen.view.components.SearchView
import com.youarelaunched.challenge.ui.screen.view.components.VendorItem
import com.youarelaunched.challenge.ui.theme.VendorAppTheme

@Composable
fun VendorsRoute(
    viewModel: VendorsVM
) {
    val state by viewModel.searchResult.collectAsState()

    VendorsScreen(
        queryInput = state.query,
        vendors = state.vendors,
        isSearchInProgress = state.isVendorsLoading,
        searchResultPlaceholder = state.resultPlaceholderTitle,
        onNewQuery = viewModel::onNewQuery,

    )
}

@Composable
fun VendorsScreen(
    queryInput: String,
    vendors: List<Vendor>,
    searchResultPlaceholder: Int?,
    isSearchInProgress: Boolean,
    onNewQuery: (String) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = VendorAppTheme.colors.background,
        snackbarHost = { ChatsumerSnackbar(it) }
    ) { paddings ->
        Column(modifier = Modifier.fillMaxSize()) {
            SearchView(
                input = queryInput,
                hint = stringResource(id = R.string.hint_search_query),
                onInputChanged = onNewQuery,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .padding(horizontal = 16.dp),
            ) {
                SearchProgressView(isInProgress = isSearchInProgress)
            }
            if (searchResultPlaceholder != null) {

                Text(
                    text = stringResource(id = searchResultPlaceholder),
                    color = VendorAppTheme.colors.buttonSelected,
                    fontSize = 24.sp,
                    fontWeight = W700,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .wrapContentHeight(),
                )
            }
            LazyColumn(
                modifier = Modifier
                    .padding(paddings)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(
                    vertical = 24.dp,
                    horizontal = 16.dp
                )
            ) {
                items(vendors) { vendor ->
                    VendorItem(
                        vendor = vendor
                    )
                }

            }
        }
    }
}
