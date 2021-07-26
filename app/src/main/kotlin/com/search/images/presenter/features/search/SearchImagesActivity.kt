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

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.SearchView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.search.images.R
import com.search.images.presenter.core.platform.BaseActivity
import com.search.images.presenter.core.platform.NetworkHandler
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.toolbar.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author Paramanathan Ilanthirayan <theebankala@gmail.com>
 * @version 1.0.0
 * @since 22nd of July 2021
 */
@AndroidEntryPoint
class SearchImagesActivity : BaseActivity() {

    private var mQuery: String? = null

    lateinit var searchView: SearchView

    var searchImagesFragment: SearchImagesFragment = SearchImagesFragment()

    @Inject
    lateinit var networkHandler: NetworkHandler

    companion object {
        fun callingIntent(context: Context) = Intent(context, SearchImagesActivity::class.java)
    }

    override fun fragment() = searchImagesFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowCustomEnabled(true) //disable a custom view inside the actionbar
            it.setDisplayShowTitleEnabled(false) //show the title in the action bar
            setSearchActionBarCustomView(it)
        }
    }


    fun hideSoftKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if (currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    private fun setSearchActionBarCustomView(supportActionBar: ActionBar) {
        val customView: View = layoutInflater.inflate(R.layout.actionbar_search, null)
        supportActionBar.customView = customView
        searchView = customView.findViewById<View>(R.id.search_view) as SearchView
        searchView.onActionViewExpanded()

        // Enable/Disable Submit button in the keyboard
        searchView.isSubmitButtonEnabled = false

        // Change search close button image
        val closeButton = searchView.findViewById<View>(R.id.search_close_btn) as ImageView
        val vectorDrawableCompat =
            VectorDrawableCompat.create(resources, R.drawable.avd_ic_close_white_24dp, null)
        closeButton.setImageDrawable(vectorDrawableCompat)

        // set hint and the text colors
        @SuppressLint("CutPasteId") val txtSearch =
            searchView.findViewById<View>(R.id.search_src_text) as EditText
        txtSearch.setTextColor(Color.WHITE)
        txtSearch.hint = resources.getString(R.string.search_hint)
        txtSearch.setHintTextColor(Color.WHITE)

        //set the background with 9 patch image to display the bottom line
        searchView.setBackgroundResource(R.drawable.abc_textfield_search_default_mtrl_alpha)

        // set the cursor
        @SuppressLint("CutPasteId") val searchTextView =
            searchView.findViewById<View>(R.id.search_src_text) as AutoCompleteTextView
        try {
            val mCursorDrawableRes = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            mCursorDrawableRes.isAccessible = true
            //This sets the cursor resource ID to 0 or @null which will make it visible on white background
            mCursorDrawableRes[searchTextView] = R.drawable.search_cursor
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
        val animation = AnimationUtils.loadAnimation(
            applicationContext, R.anim.slide_in
        )
        customView.animation = animation
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                if (!networkHandler.isNetworkAvailable()) {
                    searchView.clearFocus()
                }
            }

            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })
        setUpSearchObservable()
    }

    fun setUpSearchObservable() {
        RxSearchObservable.fromView(searchView, this)
            .debounce(3000, TimeUnit.MILLISECONDS)
            .filter { query ->
                if (!TextUtils.isEmpty(query)) {
                    mQuery = query.trim()
                    runOnUiThread {
                        searchImagesFragment.setImageLoading()
                        searchImagesFragment.getImagesBasedOnQueryString(true, mQuery!!)
                    }
                    return@filter true
                } else {
                    runOnUiThread {
                        searchImagesFragment.setEmptyResultLayout()
                    }
                }
                false
            }
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}