package com.arkan.seeflix.presentation.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.arkan.seeflix.R
import com.arkan.seeflix.data.model.Bookmark
import com.arkan.seeflix.databinding.FragmentBookmarkBinding
import com.arkan.seeflix.presentation.bookmark.adapter.BookmarkAdapter
import com.arkan.seeflix.presentation.bookmark.adapter.BookmarkListener
import com.arkan.seeflix.presentation.detail.DetailActivity
import com.arkan.seeflix.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarkFragment : Fragment() {
    private lateinit var binding: FragmentBookmarkBinding

    private val viewModel: BookmarkViewModel by viewModel()

    private val adapter: BookmarkAdapter by lazy {
        BookmarkAdapter(
            object : BookmarkListener {
                override fun onDeleteFavoriteClicked(item: Bookmark) {}

                override fun onItemClicked(movieId: String?) {
                    if (movieId != null) navigateToDetail(movieId)
                }
            },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBookmarkBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setAdapter()
    }

    private fun observeData() {
        viewModel.getAllBookmark().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnLoading = {
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.rvBookmark.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.rvBookmark.isVisible = true
                    result.payload?.let {
                        adapter.submitData(it)
                    }
                },
                doOnError = {
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.rvBookmark.isVisible = false
                    binding.layoutState.tvError.text = it.exception?.message.orEmpty()
                },
                doOnEmpty = {
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.rvBookmark.isVisible = false
                    binding.layoutState.tvError.text = getString(R.string.text_bookmark_empty)
                },
            )
        }
    }

    private fun setAdapter() {
        binding.rvBookmark.adapter = this@BookmarkFragment.adapter
    }

    private fun navigateToDetail(movieId: String) {
        DetailActivity.startActivity(requireContext(), movieId)
    }
}
