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
package com.search.images.presenter.features.details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.search.images.R
import com.search.images.presenter.core.extension.loadFromUrl
import com.search.images.presenter.core.extension.loadUrlAndPostponeEnterTransition
import com.search.images.presenter.core.platform.BaseFragment
import com.search.images.presenter.model.ImageModelParcelable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_image_details.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

/**
 * @author Paramanathan Ilanthirayan <theebankala@gmail.com>
 * @version 1.0.0
 * @since 24th of July 2021
 */
@AndroidEntryPoint
class ImageDetailsFragment : BaseFragment() {

    companion object {
        private const val  PARAM_IMAGE_MODEL = "param_image_model"

        fun forImageModel(imageModelParcelable: ImageModelParcelable?) = ImageDetailsFragment().apply {
            arguments = bundleOf(PARAM_IMAGE_MODEL to imageModelParcelable)
        }
    }

    @Inject
    lateinit var imageDetailsAnimator: ImageDetailsAnimator

    override fun layoutId() = R.layout.fragment_image_details

    private lateinit var imageModelParcelable: ImageModelParcelable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let { imageDetailsAnimator.postponeEnterTransition(it) }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstTimeCreated(savedInstanceState)) {
            imageModelParcelable = (arguments?.get(PARAM_IMAGE_MODEL) as ImageModelParcelable)
        } else {
            imageDetailsAnimator.cancelTransition(imageThumbnail)
            imageThumbnail.loadFromUrl(imageModelParcelable.url)
        }
        renderImageDetails()
    }

    override fun onBackPressed() {
        imageDetailsAnimator.fadeInvisible(scrollView, imageDetails)
    }

    private fun renderImageDetails() {
        imageModelParcelable.let {
            with(imageModelParcelable) {
                activity?.let {
                    imageThumbnail.loadUrlAndPostponeEnterTransition(imageModelParcelable.url, it)
                    it.toolbar.title = title
                }
                imageName.text = name
                imageTitle.text = title
                imageWebSearchUrlTxt.text = it.imageWebSearchUrl
                imageWebPageUrl.text = webpageUrl
            }
        }
        imageDetailsAnimator.fadeVisible(scrollView, imageDetails)
    }
}