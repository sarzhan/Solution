package com.youarelaunched.challenge.ui.screen.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youarelaunched.challenge.ui.theme.VendorAppTheme

@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    input: String,
    inputTextStyle: TextStyle = TextStyle(
        color = Color.Black,
        fontSize = 16.sp,
    ),
    hint: String,
    hintTextStyle: TextStyle = TextStyle(
        color = VendorAppTheme.colors.text,
        fontSize = 16.sp,
    ),
    onInputChanged: (String) -> Unit,
    trailingView: @Composable () -> Unit,
) {
    BasicTextField(
        value = input,
        singleLine = true,
        textStyle = inputTextStyle,
        onValueChange = onInputChanged,
        modifier = modifier.defaultMinSize(minHeight = 48.dp),
    ) { innerTextField ->
        Surface(
            color = VendorAppTheme.colors.background,
            shape = RoundedCornerShape(size = 16.dp),
            elevation = 8.dp,
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                Box {
                    if (input.isEmpty()) {
                        Text(text = hint, style = hintTextStyle)
                    }
                    innerTextField()
                }
                trailingView()
            }
        }
    }
}