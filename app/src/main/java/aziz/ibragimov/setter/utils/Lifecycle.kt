package aziz.ibragimov.setter.utils

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleRegistry
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner


@Composable
fun SetLifecycleListeners(
    lifecycle: Lifecycle,
    observer: LifecycleEventObserver
) {
    DisposableEffect(Unit) {
        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}


@Composable
fun SetOnStopOrStartListener(
    lifecycle: Lifecycle,
    onStop: () -> Unit,
    onStart: () -> Unit
) {
    SetLifecycleListeners(
        lifecycle = lifecycle,
        observer = { _, event ->
            when(event) {
                Lifecycle.Event.ON_STOP -> {
                    onStop()
                }
                Lifecycle.Event.ON_START -> {
                    onStart()
                }
                else -> {}
            }

        }
    )
}

@Composable
fun SetOnStopListener(
    lifecycle: Lifecycle,
    onStop: () -> Unit,
) {
    SetLifecycleListeners(
        lifecycle = lifecycle,
        observer = { _, event ->
            if (event == Lifecycle.Event.ON_STOP) onStop()
        }
    )

}


@Composable
fun SetOnStartListener(
    lifecycle: Lifecycle,
    onStart: () -> Unit,
) {
    SetLifecycleListeners(
        lifecycle = lifecycle,
        observer = { _, event ->
            if (event == Lifecycle.Event.ON_START) onStart()
        }
    )

}


@Composable
fun SetOnDestroyListener(
    lifecycle: Lifecycle,
    onDestroy: () -> Unit,
) {
    SetLifecycleListeners(
        lifecycle = lifecycle,
        observer = { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) onDestroy()
        }
    )

}


@Composable
fun SetOnResumeListener(
    lifecycle: Lifecycle,
    onResume: () -> Unit,
) {
    SetLifecycleListeners(
        lifecycle = lifecycle,
        observer = { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) onResume()
        }
    )

}



