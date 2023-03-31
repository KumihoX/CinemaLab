package com.example.cinema.presentation.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.domain.usecase.main.GetCoverUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getCoverUseCase: GetCoverUseCase
): ViewModel() {
    private val _cover = MutableLiveData("")
    val cover: LiveData<String> = _cover

    private val _inTrendsList: MutableLiveData<List<String>> = MutableLiveData(emptyList())
    val inTrendsList: LiveData<List<String>> = _inTrendsList

    private val _youWatchedCover =  MutableLiveData("")
    val youWatchedCover: LiveData<String> = _youWatchedCover

    private val _youWatchedText =  MutableLiveData("")
    val youWatchedText: LiveData<String> = _youWatchedText

    private val _newList: MutableLiveData<List<String>> = MutableLiveData(emptyList())
    val newList: LiveData<List<String>> = _newList

    private val _forYouList: MutableLiveData<List<String>> = MutableLiveData(emptyList())
    val forYouList: LiveData<List<String>> = _forYouList

    init {
        getCover()
        getInTrendsList()
        getYouWatched()
        getNewList()
        getForYouList()
    }

    private fun getCover() {
        viewModelScope.launch {
            try {
                val coverImage = getCoverUseCase(context)
                _cover.value = coverImage.backgroundImage
            } catch (rethrow: CancellationException) {
                throw rethrow
            } catch (ex: Exception) {

            }
        }
    }

    private fun getInTrendsList() {
        _inTrendsList.value = arrayListOf("https://s3-alpha-sig.figma.com/img/8272/4ed0/eda7fe8907d35f0a458f0ded7762c3a0?Expires=1681084800&Signature=V0I4nvFp9UDlRwAdeqtDxz-kXjjfhgSpROoPJwJrLHnEw03y0XKbnSxZF6dxVZo2hg82olFNmMH941f6vUdBV8NY-Vq-XQKDHp3hhu4Nr9GEFi1Aqkwt80oC~ACICrdm9RWfuVOQupyzVTI1oK2DYUfZ9oGGj8qwRmcDQxF5vWfplAdSUzQm29SmJWvjC1hiAQdf6ZosOl~NDHcagvZy9Cp-CgluRUrVHuie9cJQQC~Ev~Pz5z7q9bK9S94DZ4eoMzJcwzYe0XlzKahlz65kv530d4iwZIrTV27n24jkkTn2eeOim9zfP~40HE571npvnBJUG1GNFN2cosK~1ubhpA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4",
            "https://s3-alpha-sig.figma.com/img/2c76/645e/30cd86069084b1ad779589fec7beba8d?Expires=1681084800&Signature=Nkp1UdbBGIZ0Op2HvZxmiy6jh6BUZt5~B-fz~oC1HS0DNMos39yW-6PwtG4umhVz6XMXRUtnvZPDxrlYhEUmk2pCUddiRyP2wZAtZRAX0heIyQoGrDAgdRAoRPyyC5fre4STk-stGCe-1jmmLGjz6AOipGUmOwuurX3~65pyIBm5HiYEM-hHPr-jzM0BlhmnKH-WCeVsFd0S7mv9ix8I-Ilo-tW-j0jmMTK9Oe5iwYWQLvFo5oCLIwlQySb1LUvqF-38hXgLTALrdt0LEkMXnmKuvBPbcUlfeieQWedcAfxBZ3T8NCLsSK9Q1FQjSWKj0z9GCg9wKqtGclGTS2gdqg__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4","https://s3-alpha-sig.figma.com/img/8272/4ed0/eda7fe8907d35f0a458f0ded7762c3a0?Expires=1681084800&Signature=V0I4nvFp9UDlRwAdeqtDxz-kXjjfhgSpROoPJwJrLHnEw03y0XKbnSxZF6dxVZo2hg82olFNmMH941f6vUdBV8NY-Vq-XQKDHp3hhu4Nr9GEFi1Aqkwt80oC~ACICrdm9RWfuVOQupyzVTI1oK2DYUfZ9oGGj8qwRmcDQxF5vWfplAdSUzQm29SmJWvjC1hiAQdf6ZosOl~NDHcagvZy9Cp-CgluRUrVHuie9cJQQC~Ev~Pz5z7q9bK9S94DZ4eoMzJcwzYe0XlzKahlz65kv530d4iwZIrTV27n24jkkTn2eeOim9zfP~40HE571npvnBJUG1GNFN2cosK~1ubhpA__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4",
            "https://s3-alpha-sig.figma.com/img/2c76/645e/30cd86069084b1ad779589fec7beba8d?Expires=1681084800&Signature=Nkp1UdbBGIZ0Op2HvZxmiy6jh6BUZt5~B-fz~oC1HS0DNMos39yW-6PwtG4umhVz6XMXRUtnvZPDxrlYhEUmk2pCUddiRyP2wZAtZRAX0heIyQoGrDAgdRAoRPyyC5fre4STk-stGCe-1jmmLGjz6AOipGUmOwuurX3~65pyIBm5HiYEM-hHPr-jzM0BlhmnKH-WCeVsFd0S7mv9ix8I-Ilo-tW-j0jmMTK9Oe5iwYWQLvFo5oCLIwlQySb1LUvqF-38hXgLTALrdt0LEkMXnmKuvBPbcUlfeieQWedcAfxBZ3T8NCLsSK9Q1FQjSWKj0z9GCg9wKqtGclGTS2gdqg__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4")
    }

    private fun getYouWatched() {
        _youWatchedCover.value = "https://s3-alpha-sig.figma.com/img/bcb7/6b25/d983eab5c50cf5435bde3e2e8308b96b?Expires=1681084800&Signature=dfQc8MIbqKi7W37xeY-aPjH~y2~XXs2~ELG1NMfDaA3rQ-e65BzlHT2nOn0Tm3zlPfGUK-txYB-NS4yIBkU6fXdLblgIS6hyF~KfOD-wYntaW6LsvvuqABpJvrCvUtbekcN5XRa~A47f0YCeXdA1ds0u57R~10yaoaSHnkL6pgoyFkHVZdz212J7HqQKZALCwsEUBU-qZphfgcpYB3R7MOWNW69dJlzTmMOakU-2f3FirIho~r~12ulUf2PpBjZLDNk~1DYiVAPkZtN~Fsmd6zNFUca4oXeG63gQ-h4wp6w7RaiS1KiKrDypW6tJpYrgr07ssDKCfdHEYfosvw664A__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4"
        _youWatchedText.value = "Altered Carbon"
        }

    private fun getNewList() {
        _newList.value = arrayListOf("https://s3-alpha-sig.figma.com/img/0090/1670/f29ab23e473eeef23aecaca0df3e0931?Expires=1681084800&Signature=JrY2iZE3OQxJSBUbaaJ2boby1xPVhtkWrPtqz4Y~GIiUk0XFGY~0bqNjPQycgqGPTTqXe0W2PEB4vX1EOPmy20puk1eVBvH5PKDGjq13Suh~4J5iw5PKDcBXskB8jbHI67Er3GHV9v5Vziti36EG~JAYYnfXxKeO-Ku8-lbSjfpD0YCqI3EZFvDL8USKBvOCoeQaxznLP6s5LDYZ9Go9eQcyNWvVhcjkK7QjmLIsdgh9rae8ASRLm~cM7G5d17rF6ZG9bLv~4s59oJ4XQY9vc14ckPklq~Ys4krpfWSDKPi7jA~ucCAae8eKtwMc2GJxvaT0rMR~rYmVFqTQqiAOnw__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4",
            "https://s3-alpha-sig.figma.com/img/0090/1670/f29ab23e473eeef23aecaca0df3e0931?Expires=1681084800&Signature=JrY2iZE3OQxJSBUbaaJ2boby1xPVhtkWrPtqz4Y~GIiUk0XFGY~0bqNjPQycgqGPTTqXe0W2PEB4vX1EOPmy20puk1eVBvH5PKDGjq13Suh~4J5iw5PKDcBXskB8jbHI67Er3GHV9v5Vziti36EG~JAYYnfXxKeO-Ku8-lbSjfpD0YCqI3EZFvDL8USKBvOCoeQaxznLP6s5LDYZ9Go9eQcyNWvVhcjkK7QjmLIsdgh9rae8ASRLm~cM7G5d17rF6ZG9bLv~4s59oJ4XQY9vc14ckPklq~Ys4krpfWSDKPi7jA~ucCAae8eKtwMc2GJxvaT0rMR~rYmVFqTQqiAOnw__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4",
            "https://s3-alpha-sig.figma.com/img/0090/1670/f29ab23e473eeef23aecaca0df3e0931?Expires=1681084800&Signature=JrY2iZE3OQxJSBUbaaJ2boby1xPVhtkWrPtqz4Y~GIiUk0XFGY~0bqNjPQycgqGPTTqXe0W2PEB4vX1EOPmy20puk1eVBvH5PKDGjq13Suh~4J5iw5PKDcBXskB8jbHI67Er3GHV9v5Vziti36EG~JAYYnfXxKeO-Ku8-lbSjfpD0YCqI3EZFvDL8USKBvOCoeQaxznLP6s5LDYZ9Go9eQcyNWvVhcjkK7QjmLIsdgh9rae8ASRLm~cM7G5d17rF6ZG9bLv~4s59oJ4XQY9vc14ckPklq~Ys4krpfWSDKPi7jA~ucCAae8eKtwMc2GJxvaT0rMR~rYmVFqTQqiAOnw__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4")
    }

    private fun getForYouList() {
        _forYouList.value = arrayListOf("https://s3-alpha-sig.figma.com/img/55de/6afa/1dddd54aaf5450b5a6ea7cc0ae9acbd2?Expires=1681084800&Signature=nIldsNTXQX0O~1nBu2MnE3~nTLxGLLpHDW3MiaHyG41jo1XJ~sJQ25jg7NqmAHktIQldzjFxrf61rE3CFvwuRgerziWBjSl3HSYIzRravfBY3KzJIxS3om8HggRhgTPchMkeNu77i~WYBl6LAI4eWRrDc36-rwIEVvWLFZHFwuvSR1y09yMVev0rn1B8PPy9Y2oKgw3ODMmidz5hV3oIYmKWblDtycdZDQLdHwWA073Nc9~hbJX1AH9irPiRoosHNi-wDBWp2RX2h~MaAS3~oRL0KfU6u7ilBB3sN-rDrKuBVCJ57eyKeUs3djhzbAcXN8cMNMR0sOuuZ498mnPDsw__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4",
            "https://s3-alpha-sig.figma.com/img/55de/6afa/1dddd54aaf5450b5a6ea7cc0ae9acbd2?Expires=1681084800&Signature=nIldsNTXQX0O~1nBu2MnE3~nTLxGLLpHDW3MiaHyG41jo1XJ~sJQ25jg7NqmAHktIQldzjFxrf61rE3CFvwuRgerziWBjSl3HSYIzRravfBY3KzJIxS3om8HggRhgTPchMkeNu77i~WYBl6LAI4eWRrDc36-rwIEVvWLFZHFwuvSR1y09yMVev0rn1B8PPy9Y2oKgw3ODMmidz5hV3oIYmKWblDtycdZDQLdHwWA073Nc9~hbJX1AH9irPiRoosHNi-wDBWp2RX2h~MaAS3~oRL0KfU6u7ilBB3sN-rDrKuBVCJ57eyKeUs3djhzbAcXN8cMNMR0sOuuZ498mnPDsw__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4",
            "https://s3-alpha-sig.figma.com/img/55de/6afa/1dddd54aaf5450b5a6ea7cc0ae9acbd2?Expires=1681084800&Signature=nIldsNTXQX0O~1nBu2MnE3~nTLxGLLpHDW3MiaHyG41jo1XJ~sJQ25jg7NqmAHktIQldzjFxrf61rE3CFvwuRgerziWBjSl3HSYIzRravfBY3KzJIxS3om8HggRhgTPchMkeNu77i~WYBl6LAI4eWRrDc36-rwIEVvWLFZHFwuvSR1y09yMVev0rn1B8PPy9Y2oKgw3ODMmidz5hV3oIYmKWblDtycdZDQLdHwWA073Nc9~hbJX1AH9irPiRoosHNi-wDBWp2RX2h~MaAS3~oRL0KfU6u7ilBB3sN-rDrKuBVCJ57eyKeUs3djhzbAcXN8cMNMR0sOuuZ498mnPDsw__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4",
            "https://s3-alpha-sig.figma.com/img/55de/6afa/1dddd54aaf5450b5a6ea7cc0ae9acbd2?Expires=1681084800&Signature=nIldsNTXQX0O~1nBu2MnE3~nTLxGLLpHDW3MiaHyG41jo1XJ~sJQ25jg7NqmAHktIQldzjFxrf61rE3CFvwuRgerziWBjSl3HSYIzRravfBY3KzJIxS3om8HggRhgTPchMkeNu77i~WYBl6LAI4eWRrDc36-rwIEVvWLFZHFwuvSR1y09yMVev0rn1B8PPy9Y2oKgw3ODMmidz5hV3oIYmKWblDtycdZDQLdHwWA073Nc9~hbJX1AH9irPiRoosHNi-wDBWp2RX2h~MaAS3~oRL0KfU6u7ilBB3sN-rDrKuBVCJ57eyKeUs3djhzbAcXN8cMNMR0sOuuZ498mnPDsw__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4")
    }
}