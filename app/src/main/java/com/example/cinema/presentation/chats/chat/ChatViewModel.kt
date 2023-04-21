package com.example.cinema.presentation.chats.chat

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.R
import com.example.cinema.data.remote.api.dto.MessageDto
import com.example.cinema.domain.model.ChatItem
import com.example.cinema.domain.usecase.chats.CloseWebSocketUseCase
import com.example.cinema.domain.usecase.chats.GetMessagesUseCase
import com.example.cinema.domain.usecase.chats.OpenWebSocketUseCase
import com.example.cinema.domain.usecase.chats.SendMessageUseCase
import com.example.cinema.domain.usecase.profile.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getProfileUseCase: GetProfileUseCase,
    private val openWebSocketUseCase: OpenWebSocketUseCase,
    private val getMessagesUseCase: GetMessagesUseCase,
    private val closeWebSocketUseCase: CloseWebSocketUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {

    sealed class ChatState {
        object Loading : ChatState()
        object MessageLoading: ChatState()
        object Success : ChatState()
        class Failure(val errorMessage: String) : ChatState()
    }

    private var userId: String = ""

    private var lastData: String = ""
    private var lastUser: String = ""

    private val _state = MutableLiveData<ChatState>(ChatState.Loading)
    val state: LiveData<ChatState> = _state

    private val _messageList = MutableLiveData<MutableList<ChatItem>>(mutableListOf())
    val messageList: LiveData<MutableList<ChatItem>> = _messageList

    fun openWebSocket(chatId: String) {
        viewModelScope.launch {
            openWebSocketUseCase(context, chatId)
            getProfileData()
            getMessages()
        }

    }

    fun closeWebSocket() {
        viewModelScope.launch {
            closeWebSocketUseCase()
        }
    }

    private fun getProfileData() {
        viewModelScope.launch {
            try {
                userId = getProfileUseCase(context).userId
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {
                _state.value = ChatState.Failure(ex.message.toString())
            }

        }
    }

    private val zoneOffset: Long = getZoneOffset()

    private fun getZoneOffset(): Long {
        val mCalendar: Calendar = GregorianCalendar()
        val mTimeZone = mCalendar.timeZone
        val mGMTOffset = mTimeZone.rawOffset.toLong()
        return TimeUnit.HOURS.convert(mGMTOffset, TimeUnit.MILLISECONDS)
    }

    private fun getDate(dateString: String): String {
        val dateTime = LocalDateTime.parse(dateString).plusHours(zoneOffset)
        return dateTime.format(DateTimeFormatter.ofPattern("dd MMMM"))
    }

    private fun checkIdDateIsToday(dateString: String): Boolean {
        val dateTime = LocalDateTime.parse(dateString).plusHours(zoneOffset)
        return dateTime.toLocalDate() == LocalDateTime.now().toLocalDate()
    }

    private fun getTime(dateString: String): String {
        val dateTime = LocalDateTime.parse(dateString).plusHours(zoneOffset)
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    }


    private fun convertMessageDtoToChatItem(messageDto: MessageDto) {
        val messageData = messageDto.creationDateTime.split("T")[0]
        if (messageData != lastData) {
            if (_messageList.value!!.size > 1){
                _messageList.value?.size?.let { _messageList.value?.get(it - 1)?.avatarIsVisible = true }
            }
            lastData = messageData
            lastUser = ""

            if(checkIdDateIsToday(messageDto.creationDateTime)){
                _messageList.value?.add(
                    ChatItem(
                        type = "DATA",
                        text = context.getString(R.string.today)
                    )
                )
            }
            else{
                _messageList.value?.add(
                    ChatItem(
                        type = "DATA",
                        text = getDate(messageDto.creationDateTime)
                    )
                )
            }

        }
        var type = "SOMEONES"
        if (messageDto.authorId == userId) {
            type = "MY"
        }
        val messageTime = getTime(messageDto.creationDateTime)

        var firstMessage = true
        if (messageDto.authorId == lastUser) {
            firstMessage = false
        }
        else {
            _messageList.value?.size?.let { _messageList.value?.get(it - 1)?.avatarIsVisible = true }
        }
        lastUser = messageDto.authorId

        _messageList.value?.add(
            ChatItem(
                type = type,
                text = messageDto.text,
                author = messageDto.authorName + " • " + messageTime,
                authorAvatar = messageDto.authorAvatar,
                creationTime = messageData,
                firstMessage = firstMessage
            )
        )
    }

    private fun getMessages() {
        viewModelScope.launch {
            getMessagesUseCase().collect {
                if (it != null) {
                    convertMessageDtoToChatItem(it)
                    _state.value = ChatState.Success
                }
            }
        }
    }

    fun sendMessage(message: String) {
        _state.value = ChatState.MessageLoading
        viewModelScope.launch {
            sendMessageUseCase(message)
            _state.value = ChatState.Success
        }
    }
}