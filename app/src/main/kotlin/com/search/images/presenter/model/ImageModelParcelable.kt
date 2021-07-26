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
package com.search.images.presenter.model

import android.os.Parcel
import com.search.images.presenter.core.platform.KParcelable
import com.search.images.presenter.core.platform.parcelableCreator

/**
 * @author Paramanathan Ilanthirayan <theebankala@gmail.com>
 * @version 1.0.0
 * @since 24th of July 2021
 */
data class ImageModelParcelable(val url: String,
                                val height: Int,
                                val width: Int,
                                val thumbnail: String,
                                val thumbnailHeight: Int,
                                val thumbnailWidth: Int,
                                val name: String,
                                val title: String,
                                val imageWebSearchUrl: String,
                                val webpageUrl: String) : KParcelable {

    companion object {
        @JvmField val CREATOR = parcelableCreator(::ImageModelParcelable)
    }


    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!! )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeString(url)
            writeInt(height)
            writeInt(width)
            writeString(thumbnail)
            writeInt(thumbnailHeight)
            writeInt(thumbnailWidth)
            writeString(name)
            writeString(title)
            writeString(imageWebSearchUrl)
            writeString(webpageUrl)
        }
    }
}
