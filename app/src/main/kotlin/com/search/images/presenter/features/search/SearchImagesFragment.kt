/*
 *    Copyright (C) 2021 Paramanathan Ilanthirayan Open Source Project
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.search.images.presenter.features.search

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.search.images.R
import com.search.images.domain.exception.Failure
import com.search.images.domain.exception.Failure.NetworkConnection
import com.search.images.domain.exception.Failure.ServerError
import com.search.images.presenter.core.extension.failure
import com.search.images.presenter.core.extension.invisible
import com.search.images.presenter.core.extension.observe
import com.search.images.presenter.core.extension.visible
import com.search.images.presenter.core.navigation.Navigator
import com.search.images.presenter.core.platform.BaseFragment
import com.search.images.presenter.core.platform.NetworkHandler
import com.search.images.presenter.features.search.adapter.ImagesAdapter
import com.search.images.presenter.model.ImageFailure.ListNotAvailable
import com.search.images.presenter.model.ImageModelParcelable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_images.*
import javax.inject.Inject

/**
 * @author Paramanathan Ilanthirayan <theebankala@gmail.com>
 * @version 1.0.0
 * @since 22nd of July 2021
 */
@AndroidEntryPoint
class SearchImagesFragment : BaseFragment() {

    @Inject
    lateinit var navigator: Navigator
    var imagesAdapter: ImagesAdapter? = null

    private lateinit var gridLayoutManager: GridLayoutManager

    @Inject
    lateinit var networkHandler: NetworkHandler

    private val searchImagesViewModel: SearchImagesViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_search_images

    private var startDownloading = false
    private var loading = false // True if we are still waiting for the last set of data to load.
    private val visibleThreshold = 1 // The minimum amount of items to have below your current scroll position before loading more.
    private var lastVisibleItem = 0
    private  var totalItemCount:Int = 0

    private var mQuery: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(searchImagesViewModel) {
            observe(images, ::renderImagesList)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gridLayoutManager = GridLayoutManager(requireActivity(), 3)
        searchImageRecyclerView.layoutManager = gridLayoutManager
        searchImageRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy <= 0) return
                totalItemCount = gridLayoutManager.itemCount
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition()

                if (!loading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                    loading = true // End has been reached
                    onLoadMore()
                }
            }
        })
        setImageLoading()
    }

    private fun onLoadMore() {
        if (searchImagesViewModel.mTotalItemCount < getTotalNumberOfItemsInAdapter() || imagesAdapter == null) {
            return
        }

        mQuery?.let {
            searchImagesViewModel.loadImages(false,
                it, networkHandler.isNetworkAvailable())
        }
    }

    private fun getTotalNumberOfItemsInAdapter(): Int {
        return imagesAdapter?.itemCount ?: 0
    }

    private fun renderImagesList(images: List<ImageModelParcelable>?) {
        startDownloading = false
        emptyResultLayout.invisible()
        searchImageRecyclerView.visible()
        searchImageLoadingLayout.invisible()

        if (imagesAdapter == null) {
            imagesAdapter = ImagesAdapter(images.orEmpty())
            searchImageRecyclerView.adapter = imagesAdapter
            imagesAdapter!!.clickListener = { imageModelParcelable, navigationExtras ->
                navigator.showImageDetails(requireActivity(), imageModelParcelable, navigationExtras) }
        } else {
            imagesAdapter!!.addMoreItemsAtTheBottomOfTheList(images.orEmpty())
        }
        loading = false
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is ServerError -> renderFailure(R.string.failure_server_error)
            is ListNotAvailable -> renderFailure(R.string.failure_images_list_unavailable)
            else -> renderFailure(R.string.failure_server_error)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        setEmptyResultLayout()
        notifyWithAction(message, R.string.action_refresh, ::setImageLoading)
    }

    fun setImageLoading() {
        imagesAdapter = null
        emptyResultLayout.invisible()
        searchImageRecyclerView.invisible()
        searchImageLoadingLayout.visible() //showProgress()
    }

    fun getImagesBasedOnQueryString(forceUpdate: Boolean, query: String) {
        mQuery = query
        searchImagesViewModel.loadImages(true, query, networkHandler.isNetworkAvailable())
    }

    fun setEmptyResultLayout() {
        emptyResultLayout.visible()
        searchImageRecyclerView.invisible()
        searchImageLoadingLayout.invisible() // hideProgress()
    }

}