package com.example.cinema.presentation.compilation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.cinema.R
import com.example.cinema.data.remote.dto.MovieDto
import com.example.cinema.databinding.FragmentCollectionsBinding
import com.example.cinema.databinding.FragmentCompilationBinding
import com.example.cinema.presentation.signup.SignUpViewModel
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.Duration
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting
import com.yuyakaido.android.cardstackview.SwipeableMethod
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompilationFragment : Fragment(), CardStackListener {
    private lateinit var binding: FragmentCompilationBinding
    private lateinit var layoutManager: CardStackLayoutManager

    private val viewModel: CompilationViewModel by viewModels()

    private val listString = listOf("https://s3-alpha-sig.figma.com/img/117a/eed2/5cea8f76240f581a3c492f4548be3cec?Expires=1681689600&Signature=TidIa85ETd5V645moZ3WmJpGrMFpz98aHusn8eYiywP9RqVY5Sf~Rjsac0ftIpKN6m41BIELeQiVPD89helfofTeR084XnGUnDfNCNFQClJDfPHoSUOpz0zxMNrucrx8ByrnLyhDIhqLluX4bXFNNdRbY5VUlM28D4Tm3GQOzZ04nRAXC2guI0p2S2Wz9FDdU5vxjA3heRjl2HGNzgxYPJ3PMJOHwLUObJ27mNero3G34v4hVQV90oA70p~9cQx2lHexvw-fPBP4T6Lbey57V9a63qIEeuDMdoRaiNIdfWAF-iT0ZGP-jufaqJq89CFw3g7TcBd7FrhGMGIEiiZ9Ig__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4",
    "https://s3-alpha-sig.figma.com/img/22c3/9318/d751d23990efb5c3222af2eca4c5877f?Expires=1681689600&Signature=LlBK72V~ahODkHgite6kt5~taDWxyxrChBl6uFSqyCKOk2Ucn7aVEkXgNCvDbBWLnR3LYO1XbdSKVl6fA7PjPcyLLDhg0uFr310Ng762GPNXboH57haW3YCfZgahkejV-NmWxqmL5t1WM7U6iuff75FRl2R~768d1OWJdstD-nt~yuHFqOj~Wu4qUgIKRtNz5-~r-w-u5uEH7SoFiCeYAdGx3PqE3I8CFFXrKaXQe0wOwpNUz~rYDFpuqkHLUApsE2g9W3bbN5OACJPign9lwJ5-nNRAZl4HzYbhatKLaq6JRVHg2K3XC-iFRDhlq4mwlbITQlpNtGQcgV0zmo87fQ__&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainView = inflater.inflate(R.layout.fragment_compilation, container, false)
        binding = FragmentCompilationBinding.bind(mainView)

        viewModel.getCompilation()

        return binding.root
    }

    override fun onStart() {
        createCardStackView()
        setOnButtonsClickListeners()

        super.onStart()
    }

    private fun createCardStackView() {
        layoutManager = CardStackLayoutManager(context,this).apply {
            setMaxDegree(-10.0f)
        }
        binding.cardStackView.layoutManager = layoutManager

        val compilationListObserver = Observer<List<MovieDto>> { newList ->
            binding.cardStackView.adapter = CardStackViewAdapter(newList, this)
        }
        viewModel.compilationList.observe(viewLifecycleOwner, compilationListObserver)

    }

    private fun setOnButtonsClickListeners() {
        setOnLikeButtonClickListener()
        setOnPlayButtonClickListener()
        setOnDislikeButtonClickListener()
    }

    private fun setOnLikeButtonClickListener() {
        binding.likeButton.setOnClickListener {
            val swipeSetting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(LinearInterpolator())
                .build()
            layoutManager.setSwipeAnimationSetting(swipeSetting)
            binding.cardStackView.swipe()
        }
    }

    private fun setOnPlayButtonClickListener() {
        binding.playCompilationButton.setOnClickListener {

        }
    }

    private fun setOnDislikeButtonClickListener() {
        binding.dislikeButton.setOnClickListener {
            val swipeSetting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(LinearInterpolator())
                .build()
            layoutManager.setSwipeAnimationSetting(swipeSetting)
            binding.cardStackView.swipe()
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