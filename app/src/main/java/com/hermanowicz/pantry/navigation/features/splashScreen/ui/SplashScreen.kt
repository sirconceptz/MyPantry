package com.hermanowicz.pantry.navigation.features.splashScreen.ui

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.hermanowicz.pantry.BuildConfig
import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.ui.theme.LocalSpacing
import com.hermanowicz.pantry.ui.theme.PrimaryColor
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToMyPantry: () -> Unit
) {
    val scale = remember { Animatable(0.0f) }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(1000, easing = {
                OvershootInterpolator(4f).getInterpolation(it)
            })
        )
        delay(500)
        onNavigateToMyPantry()
    }

    SplashView(scale.value)
}

@Composable
fun SplashView(animValue: Float) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .size(LocalSpacing.current.splashScreenLogo)
                .background(PrimaryColor)
                .scale(animValue),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.ic_logo_content),
                contentDescription = null
            )
        }
        Text(
            text = stringResource(id = R.string.app_name),
            style = TextStyle(
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryColor
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier
                .padding(LocalSpacing.current.small)
                .fillMaxWidth(),
            text = stringResource(id = R.string.app_version) + ": " + BuildConfig.VERSION_NAME,
            textAlign = TextAlign.End
        )
    }
}

@Preview
@Composable
private fun Preview() {
    Column {
        SplashScreen(onNavigateToMyPantry = {})
    }
}
