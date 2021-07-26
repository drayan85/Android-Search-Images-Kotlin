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

import androidx.appcompat.widget.SearchView
import java.util.concurrent.atomic.AtomicBoolean

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * @author Paramanathan Ilanthirayan <theebankala@gmail.com>
 * @version 1.0.0
 * @since 25th of July 2021
 */
open class RxSearchObservable {

    companion object {
        fun fromView(searchView: SearchView, searchImagesActivity: SearchImagesActivity): Observable<String> {

            val subject = PublishSubject.create<String>()
            val isSubmitButtonClicked = AtomicBoolean(false)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(s: String): Boolean {
                    subject.onComplete()
                    searchImagesActivity.hideSoftKeyboard()
                    isSubmitButtonClicked.set(true)
                    return true
                }

                override fun onQueryTextChange(text: String): Boolean {
                    if (isSubmitButtonClicked.get()) {
                        isSubmitButtonClicked.set(false)
                        searchImagesActivity.setUpSearchObservable()
                    }
                    subject.onNext(text)
                    return true
                }
            })
            return subject
        }
    }
}