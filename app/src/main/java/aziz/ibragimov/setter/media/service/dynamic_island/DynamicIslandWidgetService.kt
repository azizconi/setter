package aziz.ibragimov.setter.media.service.dynamic_island

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.compositionContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import aziz.ibragimov.setter.data.local.music.Music
import aziz.ibragimov.setter.ui.screen.main.components.DynamicIsland
import aziz.ibragimov.setter.utils.MyLifecycleOwner
import azizjon.ibragimov.setter.utils.StateDynamicIsland
import azizjon.ibragimov.setter.utils.StateDynamicIslandStack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DynamicIslandService(
) : Service() {
    override fun onBind(p0: Intent?): IBinder? = null

    private val dynamicIsland by lazy {
        ComposeView(this)
    }
    private val layoutFlag: Int =
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY

    private val params = WindowManager.LayoutParams(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
        layoutFlag,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        PixelFormat.TRANSLUCENT
    ).apply {
        gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
    }


    private val windowManager by lazy {
        getSystemService(WindowManager::class.java)
    }
    private val isPlaying = mutableStateOf(false)
    private val currentMusic = mutableStateOf<Music?>(null)
    private val currentAudioProgress = mutableStateOf(0f)

    override fun onCreate() {
        super.onCreate()




        val lifecycleOwner = MyLifecycleOwner()
        lifecycleOwner.performRestore(null)
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        ViewTreeLifecycleOwner.set(dynamicIsland, lifecycleOwner)
//        ViewTreeSavedStateRegistryOwner.set(dynamicIsland, lifecycleOwner)
        dynamicIsland.setViewTreeSavedStateRegistryOwner(lifecycleOwner)
        val viewModelStore = ViewModelStore()
        ViewTreeViewModelStoreOwner.set(dynamicIsland) { viewModelStore }

        val coroutineContext = AndroidUiDispatcher.CurrentThread
        val runRecomposeScope = CoroutineScope(coroutineContext)
        val recomposer = Recomposer(coroutineContext)
        dynamicIsland.compositionContext = recomposer
        runRecomposeScope.launch {
            recomposer.runRecomposeAndApplyChanges()
        }

        dynamicIsland.setContent {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(1f)
                    .padding(top = 20.dp)
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                if (currentMusic.value != null) {
                    DynamicIslandWidget(
                        progress = currentAudioProgress,
                        audio = currentMusic.value!!,
                        isPlaying = isPlaying.value,
                    )
                }
            }

        }


        windowManager.addView(dynamicIsland, params)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isPlaying.value = intent?.getBooleanExtra("isAudioPlaying", false) == true
        currentMusic.value = intent?.getParcelableExtra("currentMusic")
        currentAudioProgress.value = intent?.getFloatExtra("currentAudioProgress", 0f)!!

        return super.onStartCommand(intent, flags, startId)
    }


    override fun onDestroy() {
        super.onDestroy()
        dynamicIsland.removeAllViews()
    }

    @Composable
    private fun DynamicIslandWidget(
        progress: MutableState<Float>,
        audio: Music,
        isPlaying: Boolean,
    ) {

        var dynamicIslandState by remember {
            mutableStateOf<StateDynamicIsland>(StateDynamicIsland.Hide())
        }

        DynamicIsland(
            progress = progress,
            onProgressChangeFinished = {

            },
            onProgressChange = {

            },
            audio = audio,
            isAudioPlaying = isPlaying,
            onStart = { },
            onNext = { /*TODO*/ },
            onPrevious = { /*TODO*/ },
            onClickDynamicIsland = {
                dynamicIslandState = if (it) {
                    StateDynamicIsland.FullExpand()
                } else {
                    StateDynamicIsland.Expand()
                }
            },
            state = dynamicIslandState,
            onShuffled = {

            },
            onRepeat = {

            },
            dynamicIslandStack = StateDynamicIslandStack.Expand,
            onLongClick = {

            }
        )

    }

}