package com.hermanowicz.mypantry.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class ViewStateDelegateImpl<UIState, Event>(
    initialUIState: UIState,
    singleLiveEventCapacity: Int = Channel.BUFFERED,
) : ViewStateDelegate<UIState, Event> {

    /**
     * The source of truth that drives our app.
     */
    private val stateFlow = MutableStateFlow(initialUIState)

    override val uiState: Flow<UIState>
        get() = stateFlow.asStateFlow()

    override val stateValue: UIState
        get() = stateFlow.value

    private val singleEventsChannel = Channel<Event>(singleLiveEventCapacity)

    override val singleEvents: Flow<Event>
        get() = singleEventsChannel.receiveAsFlow()

    private val mutex = Mutex()

    override suspend fun ViewStateDelegate<UIState, Event>.reduce(action: (state: UIState) -> UIState) {
        mutex.withLock {
            stateFlow.emit(action(stateValue))
        }
    }

    override suspend fun ViewStateDelegate<UIState, Event>.sendEvent(event: Event) {
        singleEventsChannel.send(event)
    }

    override fun CoroutineScope.asyncReduce(action: (state: UIState) -> UIState) {
        launch {
            reduce(action)
        }
    }
}
