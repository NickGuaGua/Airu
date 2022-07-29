package com.guagua.airu.ui.widget

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guagua.airu.R
import com.guagua.airu.data.exception.AiruException
import com.guagua.airu.ui.theme.TextColor

/**
 * BaseScreen provides default implementation of loading, retry callback and [AiruException] handling.
 */
@Composable
fun BaseScreen(
    isLoading: Boolean = false,
    hasContent: Boolean = false,
    error: Throwable? = null,
    onRetryClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        content()

        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.secondary)
            return@Box
        }

        error?.let {
            val errorMessage = when (it) {
                is AiruException.NetworkError -> stringResource(id = R.string.error_network)
                is AiruException.Timeout -> stringResource(id = R.string.error_timeout)
                is AiruException -> stringResource(id = R.string.error_general, it.code)
                else -> stringResource(id = R.string.error_general, "-1")
            }

            val errorIconResId = when (it) {
                is AiruException.NetworkError -> R.drawable.ic_network_error
                is AiruException.Timeout -> R.drawable.ic_timeout
                else -> R.drawable.ic_general_error
            }

            if (hasContent) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            } else {
                Box(Modifier.matchParentSize(), contentAlignment = Alignment.Center) {
                    Column(
                        modifier = Modifier.width(IntrinsicSize.Max),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier.size(68.dp),
                            painter = painterResource(id = errorIconResId),
                            contentDescription = stringResource(id = R.string.error_icon),
                            tint = MaterialTheme.colors.background
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = errorMessage,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextColor.TitleLight
                        )

                        onRetryClick?.let {
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                modifier = Modifier.clickable { it.invoke() },
                                text = stringResource(id = R.string.retry),
                                color = MaterialTheme.colors.secondary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}