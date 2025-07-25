package com.singularity.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

abstract class MVIViewModel<Intent, State, Effect> : ViewModel(){

    private val _intent: MutableSharedFlow<Intent> = MutableSharedFlow()

    private val _uiState: MutableStateFlow<State> by lazy { MutableStateFlow(setInitialState()) }
    val uiState: StateFlow<State> by lazy { _uiState.asStateFlow() }

    private val _effect: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effect: Flow<Effect> = _effect

    protected abstract fun setInitialState(): State

    abstract fun handleIntent(intent: Intent)

    init {
        viewModelScope.launch {
            _intent.collect(::handleIntent)
        }
    }

    fun sendIntent(intent: Intent) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    protected fun setEffect(builder: () -> Effect) {
        viewModelScope.launch {
            _effect.emit(builder())
        }
    }

    inline fun <reified T : State> getStateAs(): T? = uiState.value as? T

    protected fun setState(reducer: State.() -> State) {
        _uiState.update(reducer)
    }

    protected inline fun <reified T : State> setStateAs(crossinline reducer: T.() -> State) {
        getStateAs<T>()?.let {
            setState { reducer(this as T) }
        }
    }
}