package com.hermanowicz.mypantry.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface ViewStateDelegate<UIState, Event> {

    /**
     * Declarative description of the UI based on the current state.
     */
    val uiState: Flow<UIState>

    val singleEvents: Flow<Event>

    /**
     * State is read-only
     * The only way to change the state is to emit[reduce] an action,
     * an object describing what happened.
     */
    val stateValue: UIState

    /**
     * Reduce are functions that take the current state and an action as arguments,
     * and changed a new state result. In other words, (state: ViewState) => newState.
     */
    suspend fun ViewStateDelegate<UIState, Event>.reduce(action: (state: UIState) -> UIState)

    fun CoroutineScope.asyncReduce(action: (state: UIState) -> UIState)

    suspend fun ViewStateDelegate<UIState, Event>.sendEvent(event: Event)
}
