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

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.search.images.presenter.core.platform.BaseActivity
import com.search.images.presenter.model.ImageModelParcelable
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @author Paramanathan Ilanthirayan <theebankala@gmail.com>
 * @version 1.0.0
 * @since 22nd of July 2021
 */
class ImageDetailsActivity : BaseActivity(){


    companion object {
        private const val INTENT_EXTRA_PARAM_MOVIE = "com.search.image.INTENT_PARAM_IMAGE"

        fun callingIntent(context: Context, imageModelParcelable: ImageModelParcelable) =
            Intent(context, ImageDetailsActivity::class.java).apply {
                putExtra(INTENT_EXTRA_PARAM_MOVIE, imageModelParcelable)
            }
    }

    override fun fragment() = ImageDetailsFragment.forImageModel(intent.getParcelableExtra(INTENT_EXTRA_PARAM_MOVIE))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowCustomEnabled(true) //disable a custom view inside the actionbar
            it.setDisplayShowTitleEnabled(false) //show the title in the action bar
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}