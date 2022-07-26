package com.guagua.airu.ui.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.guagua.airu.R
import com.guagua.airu.data.model.AQI
import com.guagua.airu.ui.widget.BaseScreen

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val refreshState = rememberSwipeRefreshState(isRefreshing = state.isRefresh)

    LaunchedEffect(viewModel) {
        viewModel.getAQIs()
    }

    BaseScreen(
        isLoading = state.isLoading && !state.isRefresh,
        hasContent = !state.severeAQIs.isNullOrEmpty() && !state.normalAQIs.isNullOrEmpty(),
        error = state.error,
        onRetryClick = { viewModel.getAQIs() }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HomeTopBar(stringResource(id = R.string.air_pollution))
            SwipeRefresh(state = refreshState, onRefresh = { viewModel.getAQIs(true) }) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Column(modifier = Modifier.fillMaxSize()) {
                        state.severeAQIs?.let {
                            SevereAQIsRow(aqis = it)
                            Spacer(modifier = Modifier.height(16.dp))
                            Spacer(
                                modifier = Modifier
                                    .height(1.dp)
                                    .fillMaxWidth()
                                    .background(Color.Gray)
                            )
                        }
                        state.normalAQIs?.let {
                            NormalAQIsColumn(aqis = it) { aqi ->
                                if (!aqi.status.isGood()) {
                                    Toast.makeText(
                                        context,
                                        "AQI: ${aqi.status}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeTopBar(title: String) {
    TopAppBar(elevation = 2.dp) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .clickable { }
                .padding(16.dp),
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = stringResource(id = R.string.search_icon)
        )
    }
}

@Composable
fun SevereAQIsRow(aqis: List<AQI>) {
    LazyRow(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(aqis, key = { it.siteId }) {
            SevereAqiItem(it)
        }
    }
}

@Composable
fun SevereAqiItem(aqi: AQI) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .wrapContentHeight()
            .border(2.dp, Color.Black, RoundedCornerShape(6.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row {
            Text(text = aqi.siteId)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                modifier = Modifier.wrapContentSize(),
                text = aqi.siteName,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = aqi.pm2_5.toString())
        }
        Row {
            Text(text = aqi.county)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = aqi.status.key)
        }
    }
}

@Composable
fun NormalAQIsColumn(aqis: List<AQI>, onItemClick: ((AQI) -> Unit)? = null) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(aqis, key = { it.siteId }) {
            NormalAqiItem(it) {
                onItemClick?.invoke(it)
            }
        }
    }
}

@Composable
fun NormalAqiItem(
    aqi: AQI,
    onItemClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.clickable(!aqi.status.isGood()) {
            onItemClick?.invoke()
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = aqi.siteId
        )
        Column(Modifier.weight(1f)) {
            Text(text = aqi.siteName)
            Text(text = aqi.county)
        }
        Column(
            modifier = Modifier
                .weight(2f)
                .padding(end = 16.dp), horizontalAlignment = Alignment.End
        ) {
            Text(text = aqi.pm2_5.toString())
            Text(
                text = if (aqi.status.isGood()) stringResource(id = R.string.status_good_hint)
                else aqi.status.key,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (!aqi.status.isGood()) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(top = 16.dp, bottom = 16.dp, end = 16.dp),
                painter = painterResource(id = R.drawable.ic_arrow_forward),
                contentDescription = stringResource(id = R.string.forward_arrow_icon)
            )
        }
    }
}