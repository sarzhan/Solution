package com.youarelaunched.challenge.ui.screen.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.youarelaunched.challenge.middle.R

@Composable
fun SearchProgressView(isInProgress: Boolean) {
    if (isInProgress) {
        CircularProgressIndicator(modifier = Modifier.size(22.dp))
    } else {
        Image(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = stringResource(id = R.string.search_icon),
        )
    }
}