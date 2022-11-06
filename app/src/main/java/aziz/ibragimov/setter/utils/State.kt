package azizjon.ibragimov.setter.utils

sealed class StateDynamicIsland(
    val size: Float
) {
    class Hide(size: Float = 0f): StateDynamicIsland(size)
    class Expand(size: Float = 0.3f): StateDynamicIsland(size)
    class FullExpand(size: Float = 1f): StateDynamicIsland(size)
    class SearchInputOpen(size: Float = 0.1f): StateDynamicIsland(size)
}


sealed class StateDynamicIslandStack {
    object Expand : StateDynamicIslandStack()
    object SearchInputOpen : StateDynamicIslandStack()
    object Hide : StateDynamicIslandStack()
}