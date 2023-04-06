package com.example.cinema.presentation.compilation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.cinema.R
import com.example.cinema.databinding.FragmentCollectionsBinding
import com.example.cinema.databinding.FragmentCompilationBinding
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.SwipeableMethod
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompilationFragment : Fragment(), CardStackListener {
    private lateinit var binding: FragmentCompilationBinding
    private lateinit var layoutManager: CardStackLayoutManager

    private val listString = listOf("https://s3-alpha-sig.figma.com/img/117a/eed2/5cea8f76240f581a3c492f4548be3cec?Expires=1681689600&Signature=TidIa85ETd5V645moZ3WmJpGrMFpz98aHusn8eYiywP9RqVY5Sf~Rjsac0ftIpKN6m41BIELeQiVPD89helfofTeR084XnGUnDfNCNFQClJDfPHoSUOpz0zxMNrucrx8ByrnLyhDIhqLluX4bXFNNdRbY5VUlM28D4Tm3GQOzZ04nRAXC2guI0p2S2Wz9FDdU5vxjA3heRjl2HGNzgxYPJ3PMJOHwLUObJ27mNero3G34v4hVQV90oA70p~9cQx2lHexvw-fPBP4T6Lbey57V9a63qIEeuDMdoRaiNIdfWAF-iT0ZGP-jufaqJq89CFw3g7TcBd7FrhGMGIEiiZ9Ig__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4",
    "https://s3-alpha-sig.figma.com/img/22c3/9318/d751d23990efb5c3222af2eca4c5877f?Expires=1681689600&Signature=LlBK72V~ahODkHgite6kt5~taDWxyxrChBl6uFSqyCKOk2Ucn7aVEkXgNCvDbBWLnR3LYO1XbdSKVl6fA7PjPcyLLDhg0uFr310Ng762GPNXboH57haW3YCfZgahkejV-NmWxqmL5t1WM7U6iuff75FRl2R~768d1OWJdstD-nt~yuHFqOj~Wu4qUgIKRtNz5-~r-w-u5uEH7SoFiCeYAdGx3PqE3I8CFFXrKaXQe0wOwpNUz~rYDFpuqkHLUApsE2g9W3bbN5OACJPign9lwJ5-nNRAZl4HzYbhatKLaq6JRVHg2K3XC-iFRDhlq4mwlbITQlpNtGQcgV0zmo87fQ__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_compilation, container, false)
        binding = FragmentCompilationBinding.bind(mainView)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = CardStackLayoutManager(context,this).apply {
            setMaxDegree(5.0f)
            setDirections(Direction.HORIZONTAL)
            setOverlayInterpolator(LinearInterpolator())
        }
        binding.cardStackView.layoutManager = layoutManager
        binding.cardStackView.adapter = CardStackViewAdapter(listString, this)
        binding.cardStackView.itemAnimator.apply {
            if(this is DefaultItemAnimator){
                supportsChangeAnimations = false
            }
        }
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {

    }

    override fun onCardRewound() {

    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {

    }

    override fun onCardDisappeared(view: View?, position: Int) {

    }
}