package com.example.cinema.presentation.chats.chat

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private fun test() {
        val q = MessageDto(
            messageId = "aa05728c-3799-4af6-9b24-2e595fb804f6",
            creationDateTime = "2023-04-09T06:39:26.101784",
            authorId = "0f4442d6-3777-42b6-9ed3-2e7833d993fb",
            authorName = "Bogdan Bogomdannovich",
            authorAvatar = null,
            text = "Кто-нибудь смотрел?",
        )
        convertMessageDtoToChatItem(q)

        val w = MessageDto(
            messageId = "e05dea60-ed33-437c-b5d6-8e3dab9e662b",
            creationDateTime = "2023-04-09T08:42:47.530293",
            authorId = "1475f8e7-9af0-4151-b7df-81c722544a3b",
            authorName = "Dobruak Positivov",
            authorAvatar = null,
            text = "Неа, надо как-нибудь посмотреть.",
        )
        convertMessageDtoToChatItem(w)

        val e = MessageDto(
            messageId = "092e0bf4-3a55-4435-bf65-727d51205a42",
            creationDateTime = "2023-04-15T05:56:24.399444",
            authorId = "337a3473-b2e3-4736-ba8a-13e035b72d90",
            authorName = "Green Greenpix",
            authorAvatar = "https://ucarecdn.com/6be821fb-0389-494d-b9f3-d76d04b932bd/",
            text = "{\n  \"text\": \"Я смотрел. Мне понравился\"\n}"
        )
        convertMessageDtoToChatItem(e)

        val r = MessageDto(
            messageId = "e5ad9676-7299-49c8-9f44-b396139c830b",
            creationDateTime = "2023-04-15T11:10:54.5185",
            authorId = "337a3473-b2e3-4736-ba8a-13e035b72d90",
            authorName = "Green Greenpix",
            authorAvatar = "https://ucarecdn.com/6be821fb-0389-494d-b9f3-d76d04b932bd/",
            text = "Bla"
        )
        convertMessageDtoToChatItem(r)

        val t = MessageDto(
            messageId = "c4b6fb14-6ba7-4f4d-aa1e-8367bce0f0b9",
            creationDateTime = "2023-04-15T12:08:20.787647",
            authorId = "337a3473-b2e3-4736-ba8a-13e035b72d90",
            authorName = "Green Greenpix",
            authorAvatar = "https://ucarecdn.com/6be821fb-0389-494d-b9f3-d76d04b932bd/",
            text = ""
        )
        convertMessageDtoToChatItem(t)

        val y = MessageDto(
            messageId = "b1f64d94-e1e4-499d-922f-80766088305d",
            creationDateTime = "2023-04-15T13:28:21.587093",
            authorId = "337a3473-b2e3-4736-ba8a-13e035b72d90",
            authorName = "Green Greenpix",
            authorAvatar = "https://ucarecdn.com/6be821fb-0389-494d-b9f3-d76d04b932bd/",
            text = "напишу тутачки"
        )
        convertMessageDtoToChatItem(y)

        val u = MessageDto(
            messageId = "b1f64d94-e1e4-499d-922f-80766088305d",
            creationDateTime = "2023-04-15T13:28:21.587093",
            authorId = "337a3473-b2e3-4736-ba8a-13e035b72d90",
            authorName = "Green Greenpix",
            authorAvatar = "https://ucarecdn.com/6be821fb-0389-494d-b9f3-d76d04b932bd/",
            text = "напишу тутачки"
        )
        convertMessageDtoToChatItem(u)

        val i = MessageDto(
            messageId = "0f663224-7317-4775-9c39-53a73adf3be2",
            creationDateTime = "2023-04-16T17:39:28.555416",
            authorId = "337a3473-b2e3-4736-ba8a-13e035b72d90",
            authorName = "Green Greenpix",
            authorAvatar = "https://ucarecdn.com/6be821fb-0389-494d-b9f3-d76d04b932bd/",
            text = "Здарова"
        )
        convertMessageDtoToChatItem(i)

        val o = MessageDto(
            messageId = "e94146bf-3ef1-41aa-9e0f-27f2c07d9446",
            creationDateTime = "2023-04-16T17:41:15.517841",
            authorId = "337a3473-b2e3-4736-ba8a-13e035b72d90",
            authorName = "Green Greenpix",
            authorAvatar = "https://ucarecdn.com/6be821fb-0389-494d-b9f3-d76d04b932bd/",
            text = "Здарова"
        )
        convertMessageDtoToChatItem(o)

        val p = MessageDto(
            messageId = "27471536-a5fd-40e6-a48a-dc77799bc28a",
            creationDateTime = "2023-04-16T17:56:19.609573",
            authorId = "337a3473-b2e3-4736-ba8a-13e035b72d90",
            authorName = "Green Greenpix",
            authorAvatar = "https://ucarecdn.com/6be821fb-0389-494d-b9f3-d76d04b932bd/",
            text = "Как вам фильм?"
        )
        convertMessageDtoToChatItem(p)

        val a = MessageDto(
            messageId = "b7a82ed9-30f4-4bd6-812a-f7bf7dc46db7",
            creationDateTime = "2023-04-16T18:03:18.188222",
            authorId = "337a3473-b2e3-4736-ba8a-13e035b72d90",
            authorName = "Green Greenpix",
            authorAvatar = "https://ucarecdn.com/6be821fb-0389-494d-b9f3-d76d04b932bd/",
            text = "Бы бы бы"
        )
        convertMessageDtoToChatItem(a)

        val s = MessageDto(
            messageId = "66cce9d0-2611-4357-a8fe-809537f4f04e",
            creationDateTime = "2023-04-17T06:59:53.09072",
            authorId = "81b35a20-2de3-461e-afd1-ff548ecf2543",
            authorName = "John Johnson",
            authorAvatar = "https://ucarecdn.com/7af3b3e4-f04b-4473-848a-d328f106abbd/",
            text = "Привет"
        )
        convertMessageDtoToChatItem(s)

        val d = MessageDto(
            messageId = "7b4bade2-815d-47d9-bf3a-7e36fbb15c9a",
            creationDateTime = "2023-04-17T07:06:52.971366",
            authorId = "81b35a20-2de3-461e-afd1-ff548ecf2543",
            authorName = "John Johnson",
            authorAvatar = "https://ucarecdn.com/7af3b3e4-f04b-4473-848a-d328f106abbd/",
            text = "Тест"
        )
        convertMessageDtoToChatItem(d)

        val f = MessageDto(
            messageId = "0a99dcd0-3908-4c3d-98c2-f25fa4e38f53",
            creationDateTime = "2023-04-18T07:38:50.942356",
            authorId = "020539ca-71ca-4810-9afe-b5f7347cbfaa",
            authorName = "as as",
            authorAvatar = "https://ucarecdn.com/19cfaa5e-c321-4e9d-ac32-8bf032b65500/",
            text = "чтош..."
        )
        convertMessageDtoToChatItem(f)

        val g = MessageDto(
            messageId = "aebcac25-a67f-4361-b8e0-5096b41a5973",
            creationDateTime = "2023-04-18T09:27:32.540718",
            authorId = "020539ca-71ca-4810-9afe-b5f7347cbfaa",
            authorName = "as as",
            authorAvatar = "https://ucarecdn.com/19cfaa5e-c321-4e9d-ac32-8bf032b65500/",
            text = "тузиков, сделай норм бэкенд"
        )
        convertMessageDtoToChatItem(g)

        val h = MessageDto(
            messageId = "54d4dbaf-150b-4f8b-b352-d38462b84304",
            creationDateTime = "2023-04-18T13:02:40.096344",
            authorId = "2346278a-d566-40f4-8691-3895eb270195",
            authorName = "dfgdfgd gdf",
            authorAvatar = "https://ucarecdn.com/5731d216-7d1e-448b-a4d6-f9afeab34782/",
            text = "TuzikoV"
        )
        convertMessageDtoToChatItem(h)

        val j = MessageDto(
            messageId = "67e6f0b0-457a-49c0-9dca-fa61dde1d244",
            creationDateTime = "2023-04-18T14:44:45.099054",
            authorId = "66b6cd41-0b39-43ae-8cf5-a50da6cf07ef",
            authorName = "wer wer",
            authorAvatar = null,
            text = "test 2"
        )
        convertMessageDtoToChatItem(j)

        val k = MessageDto(
            messageId = "e49786d2-2339-40c2-9878-4ce7a1b86c6c",
            creationDateTime = "2023-04-18T14:44:55.85807",
            authorId = "66b6cd41-0b39-43ae-8cf5-a50da6cf07ef",
            authorName = "wer wer",
            authorAvatar = null,
            text = "test 2"
        )
        convertMessageDtoToChatItem(k)
        _state.value = ChatState.Success
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

    private fun convertMessageDtoToChatItem(messageDto: MessageDto) {
        val messageData = messageDto.creationDateTime.split("T")[0]
        if (messageData != lastData) {
            lastData = messageData
            lastUser = ""
            _messageList.value?.add(
                ChatItem(
                    type = "DATA",
                    text = messageData
                )
            )
        }
        var type = "SOMEONES"
        if (messageDto.authorId == userId) {
            type = "MY"
        }
        val messageTime = messageDto.creationDateTime.split("T", ".")[1]
        val hours = messageTime.split(":")[0]
        val minutes = messageTime.split(":")[1]

        var firstMessage = true
        if (messageDto.authorId == lastUser) {
            firstMessage = false
        }
        lastUser = messageDto.authorId

        _messageList.value?.add(
            ChatItem(
                type = type,
                text = messageDto.text,
                author = messageDto.authorName + " • " + hours + ":" + minutes,
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