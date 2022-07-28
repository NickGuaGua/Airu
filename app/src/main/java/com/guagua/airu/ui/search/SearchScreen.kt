package com.guagua.airu.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guagua.airu.R
import com.guagua.airu.ui.composition.LocalNavController
import com.guagua.airu.ui.home.NormalAQIsColumn
import com.guagua.airu.ui.widget.BaseScreen

@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    val navController = LocalNavController.current
    val state by viewModel.state.collectAsState()
    var keyword by remember { mutableStateOf("") }

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
                NormalAQIsColumn(aqis = state.searchResult)
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
    TopAppBar(elevation = 2.dp) {
        Icon(
            modifier = Modifier
                .size(56.dp)
                .clickable { onBackClick() }
                .padding(16.dp),
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = stringResource(id = R.string.back_icon)
        )
        TextField(
            modifier = Modifier.align(Alignment.CenterVertically),
            value = keyword,
            singleLine = true,
            placeholder = {
                Text(text = stringResource(id = R.string.search_hint))
            },
            onValueChange = {
                onKeywordChanged(it)
            },
            textStyle = TextStyle(fontSize = 16.sp),
            colors = textFieldColors(
                textColor = Color.White,
                backgroundColor = Color.Transparent,
                placeholderColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White
            )
        )
    }
}

@Composable
fun EmptyHint(modifier: Modifier = Modifier, keyword: String) {
    val hint = if (keyword.isBlank()) stringResource(id = R.string.search_hint_without_keyword)
    else stringResource(id = R.string.search_hint_no_result, keyword)

    Text(modifier = modifier, text = hint, textAlign = TextAlign.Center, fontSize = 16.sp)
}