package com.guagua.airu.ui.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.guagua.airu.ui.composition.LocalNavController
import com.guagua.airu.ui.getColor
import com.guagua.airu.ui.navigation.Screen
import com.guagua.airu.ui.theme.TextColor
import com.guagua.airu.ui.widget.BaseScreen

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val navController = LocalNavController.current
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
            HomeTopBar(stringResource(id = R.string.air_pollution)) {
                if (state.normalAQIs?.isNotEmpty() == true) {
                    navController.navigate(Screen.Search.path)
                }
            }
            SwipeRefresh(state = refreshState, onRefresh = { viewModel.getAQIs(true) }) {
                Column {
                    Column(modifier = Modifier.fillMaxSize()) {
                        state.severeAQIs?.let {
                            SevereAQIsRow(aqis = it)
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
fun HomeTopBar(title: String, onSearchClick: (() -> Unit)? = null) {
    TopAppBar(elevation = 2.dp) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onPrimary
        )
        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onSearchClick != null) { onSearchClick?.invoke() }
                .padding(16.dp),
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = stringResource(id = R.string.search_icon),
            tint = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun SevereAQIsRow(aqis: List<AQI>) {
    LazyRow(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .padding(vertical = 12.dp),
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
            .background(
                aqi.status
                    .getColor()
                    .copy(alpha = 0.6f), RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = aqi.siteId,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = TextColor.TitleLight
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                modifier = Modifier.wrapContentSize(),
                text = aqi.siteName,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = TextColor.Title
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = aqi.pm2_5.toString(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor.Title
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = aqi.county, fontSize = 11.sp, color = TextColor.TitleLight)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = aqi.status.key, fontSize = 11.sp, color = TextColor.TitleLight)
        }
    }
}

@Composable
fun NormalAQIsColumn(aqis: List<AQI>, onItemClick: ((AQI) -> Unit)? = null) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        itemsIndexed(aqis, key = { _, aqi -> aqi.siteId }) { index, aqi ->
            NormalAqiItem(aqi) {
                onItemClick?.invoke(aqi)
            }
            if (index != aqis.lastIndex) {
                Divider(modifier = Modifier.padding(horizontal = 32.dp))
            }
        }
    }
}

@Composable
fun Divider(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .fillMaxSize()
            .height(1.dp)
            .background(MaterialTheme.colors.primary)
    )
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
            text = aqi.siteId,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = TextColor.TitleLight
        )
        Column(Modifier.weight(1f)) {
            Text(text = aqi.siteName, fontWeight = FontWeight.Medium, fontSize = 18.sp)
            Text(text = aqi.county, fontSize = 11.sp, color = TextColor.Subtitle)
        }
        Column(
            modifier = Modifier
                .weight(2f)
                .padding(end = 16.dp), horizontalAlignment = Alignment.End
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(aqi.status.getColor())
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = aqi.pm2_5.toString(), fontSize = 18.sp)
            }
            Text(
                text = if (aqi.status.isGood()) stringResource(id = R.string.status_good_hint)
                else aqi.status.key,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 11.sp
            )
        }
        if (!aqi.status.isGood()) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(top = 16.dp, bottom = 16.dp, end = 16.dp)
                    .size(18.dp),
                painter = painterResource(id = R.drawable.ic_arrow_forward),
                contentDescription = stringResource(id = R.string.forward_arrow_icon),
                tint = MaterialTheme.colors.onSurface
            )
        }
    }
}