package com.atlas.downloader.ui.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atlas.downloader.R
import com.atlas.downloader.ui.components.AtlasLogo
import com.atlas.downloader.ui.theme.Atlas
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    val currentOnFinished by rememberUpdatedState(onFinished)
    val fade = remember { Animatable(0f) }
    val zoom = remember { Animatable(0.92f) }

    LaunchedEffect(Unit) {
        coroutineScope {
            launch { fade.animateTo(1f, tween(durationMillis = 700)) }
            launch { zoom.animateTo(1f, tween(durationMillis = 900, easing = FastOutSlowInEasing)) }
        }
        delay(600)
        currentOnFinished()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.graphicsLayer {
                alpha = fade.value
                scaleX = zoom.value
                scaleY = zoom.value
            },
        ) {
            AtlasLogo(fontSize = 52.sp)
            Spacer(Modifier.height(14.dp))
            Text(
                text = stringResource(R.string.tagline),
                style = MaterialTheme.typography.bodyMedium,
                color = Atlas.colors.textSecondary,
            )
        }
    }
}
