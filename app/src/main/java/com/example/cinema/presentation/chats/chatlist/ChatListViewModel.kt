package com.example.cinema.presentation.chats.chatlist

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.data.remote.dto.ChatDto
import com.example.cinema.domain.usecase.chats.GetChatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getChatsUseCase: GetChatsUseCase
) : ViewModel() {
    sealed class ChatListState {
        object Loading : ChatListState()
        class Success(val chats: List<ChatDto>) : ChatListState()

        class Failure(val errorMessage: String) : ChatListState()
    }

    private val _state = MutableLiveData<ChatListState>(ChatListState.Loading)
    val state: LiveData<ChatListState> = _state

    fun getChats() {
        _state.value = ChatListState.Loading
        viewModelScope.launch {
            try {
                val chats = getChatsUseCase(context)
                _state.value = ChatListState.Success(chats)
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = ChatListState.Failure(ex.message.toString())
            }
        }
    }
}