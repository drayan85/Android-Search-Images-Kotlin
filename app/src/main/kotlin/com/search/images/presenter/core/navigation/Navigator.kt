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
package com.search.images.presenter.core.navigation

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentActivity
import com.search.images.presenter.features.details.ImageDetailsActivity
import com.search.images.presenter.features.search.SearchImagesActivity
import com.search.images.presenter.model.ImageModelParcelable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Paramanathan Ilanthirayan <theebankala@gmail.com>
 * @version 1.0.0
 * @since 22nd of July 2021
 */
@Singleton
class Navigator @Inject constructor() {

    private fun showImageSearch(context: Context) = context.startActivity(SearchImagesActivity.callingIntent(context))

    fun showMain(context: Context) {
        showImageSearch(context)
    }

    fun showImageDetails(
        activity: FragmentActivity,
        imageModelParcelable: ImageModelParcelable,
        navigationExtras: Extras
    ) {
        val intent = ImageDetailsActivity.callingIntent(activity, imageModelParcelable)
        val sharedView = navigationExtras.transitionSharedElement as ImageView
        val activityOptions = ActivityOptionsCompat
            .makeSceneTransitionAnimation(activity, sharedView, sharedView.transitionName)
        activity.startActivity(intent, activityOptions.toBundle())

    }

    class Extras(val transitionSharedElement: View)
}