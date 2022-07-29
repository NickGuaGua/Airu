package com.guagua.airu.ui.search

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guagua.airu.R
import com.guagua.airu.ui.KeyboardState
import com.guagua.airu.ui.composition.LocalNavController
import com.guagua.airu.ui.home.NormalAQIsColumn
import com.guagua.airu.ui.rememberKeyboardAsState
import com.guagua.airu.ui.theme.TextColor
import com.guagua.airu.ui.widget.BaseScreen

@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    val navController = LocalNavController.current
    val state by viewModel.state.collectAsState()
    var keyword by remember { mutableStateOf("") }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboardState = rememberKeyboardAsState()

    LaunchedEffect(keyboardState) {
        if (keyboardState == KeyboardState.Closed) focusManager.clearFocus()
    }

    BaseScreen(
        isLoading = state.isLoading,
        hasContent = state.searchResult.isNotEmpty(),
        error = state.error,
        onRetryClick = { viewModel.search(keyword) }
    ) {
        Column(Modifier.fillMaxSize()) {
            SearchBar(
                keyword = keyword,
                onBackClick = { navController.popBackStack() },
                onKeywordChanged = {
                    keyword = it
                    viewModel.search(it)
                }
            )
            Box(modifier = Modifier.fillMaxSize()) {
                NormalAQIsColumn(aqis = state.searchResult) {
                    Toast.makeText(
                        context,
                        "AQI: ${it.status}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                if (state.searchResult.isEmpty() && !state.isLoading) {
                    EmptyHint(Modifier.align(Alignment.Center), keyword)
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    keyword: String,
    onBackClick: () -> Unit,
    onKeywordChanged: (keyword: String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    TopAppBar(elevation = 2.dp) {
        Icon(
            modifier = Modifier
                .size(56.dp)
                .clickable { onBackClick() }
                .padding(16.dp),
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = stringResource(id = R.string.back_icon),
            tint = MaterialTheme.colors.onPrimary
        )
        TextField(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .focusRequester(focusRequester),
            value = keyword,
            singleLine = true,
            placeholder = {
                Text(text = stringResource(id = R.string.search_hint))
            },
            onValueChange = {
                onKeywordChanged(it)
            },
            textStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium),
            colors = textFieldColors(
                textColor = TextColor.TitleLight,
                backgroundColor = Color.Transparent,
                placeholderColor = TextColor.Subtitle,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.onPrimary
            )
        )
    }
}

@Composable
fun EmptyHint(modifier: Modifier = Modifier, keyword: String) {
    val hint = if (keyword.isBlank()) stringResource(id = R.string.search_hint_without_keyword)
    else stringResource(id = R.string.search_hint_no_result, keyword)

    Text(
        modifier = modifier,
        text = hint,
        textAlign = TextAlign.Center,
        fontSize = 16.sp,
        color = TextColor.TitleLight
    )
}