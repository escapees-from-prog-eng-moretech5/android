package dev.argraur.moretech.activitycore

import android.util.Log
import androidx.activity.ComponentActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Singleton

@Singleton
class ActivityProvider {
    private val _activityFlow = MutableStateFlow<ComponentActivity?>(null)
    val activityFlow: StateFlow<ComponentActivity?> get() = _activityFlow

    val activity: ComponentActivity? get() = _activityFlow.value

    fun attachActivity(activity: ComponentActivity) {
        Log.i("ActivityProvider", "Attached activity")
        _activityFlow.value = activity
    }

    fun detachActivity() {
        _activityFlow.value = null
    }
}