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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.search.images.domain.interactor.image.search.GetSearchImagesUseCase
import com.search.images.domain.model.ImageSearchResponse
import com.search.images.presenter.core.platform.BaseViewModel
import com.search.images.presenter.model.ImageModelParcelable
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Paramanathan Ilanthirayan <theebankala@gmail.com>
 * @version 1.0.0
 * @since 24th of July 2021
 */
@HiltViewModel
open class SearchImagesViewModel
@Inject constructor(private val getSearchImagesUseCase: GetSearchImagesUseCase,
                    private val savedStateHandle: SavedStateHandle) : BaseViewModel() {

    private val _images: MutableLiveData<List<ImageModelParcelable>> = MutableLiveData()
    val images: LiveData<List<ImageModelParcelable>> = _images

    private var page: Int = 0

    var mTotalItemCount = 0

    fun loadImages(forceUpdate: Boolean, query: String, isInternetAvailable: Boolean) {

        page = if (forceUpdate) {
            0
        } else {
            page
        }

        getSearchImagesUseCase(GetSearchImagesUseCase.Params(query,
            ++page, Companion.PER_PAGE, isInternetAvailable)) {
            it.fold(::handleFailure, ::handleImageList)
        }
    }



    private fun handleImageList(imageSearchResponse: ImageSearchResponse) {
        mTotalItemCount = imageSearchResponse.totalCount
        _images.value = imageSearchResponse.value.map {
            ImageModelParcelable(
                it.url,
                it.height,
                it.width,
                it.thumbnail,
                it.thumbnailHeight,
                it.thumbnailWidth,
                it.name,
                it.title,
                it.imageWebSearchUrl,
                it.webpageUrl)
        }
    }

    companion object {
        private const val PER_PAGE = 30
    }
}