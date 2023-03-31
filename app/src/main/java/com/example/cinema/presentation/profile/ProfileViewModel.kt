package com.example.cinema.presentation.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.usecase.profile.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getProfileUseCase: GetProfileUseCase
) : ViewModel() {
    private val _userName = MutableLiveData("")
    val userName: LiveData<String> = _userName

    private val _userEmail = MutableLiveData("")
    val userEmail: LiveData<String> = _userEmail

    private val _avatarUrl = MutableLiveData("")
    val avatarUrl: LiveData<String> = _avatarUrl

    init {
        getProfileData()
    }
    private fun getProfileData() {
        viewModelScope.launch {
            try {
                val userData = getProfileUseCase(context)
                _userName.value = "${userData.firstName} ${userData.lastName}"
                _userEmail.value = userData.email
                //_avatarUrl.value = userData.avatar
                _avatarUrl.value = "https://s3-alpha-sig.figma.com/img/8272/4ed0/eda7fe8907d35f0a458f0ded7762c3a0?Expires=1681084800&Signature=V0I4nvFp9UDlRwAdeqtDxz-kXjjfhgSpROoPJwJrLHnEw03y0XKbnSxZF6dxVZo2hg82olFNmMH941f6vUdBV8NY-Vq-XQKDHp3hhu4Nr9GEFi1Aqkwt80oC~ACICrdm9RWfuVOQupyzVTI1oK2DYUfZ9oGGj8qwRmcDQxF5vWfplAdSUzQm29SmJWvjC1hiAQdf6ZosOl~NDHcagvZy9Cp-CgluRUrVHuie9cJQQC~Ev~Pz5z7q9bK9S94DZ4eoMzJcwzYe0XlzKahlz65kv530d4iwZIrTV27n24jkkTn2eeOim9zfP~40HE571npvnBJUG1GNFN2cosK~1ubhpA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {

            }
        }

    }
}