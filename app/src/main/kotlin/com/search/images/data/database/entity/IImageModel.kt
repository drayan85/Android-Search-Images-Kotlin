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
package com.search.images.data.database.entity

import android.provider.BaseColumns

/**
 * @author Paramanathan Ilanthirayan <theebankala@gmail.com>
 * @version 1.0.0
 * @since 24th of July 2021
 */
interface IImageModel {

    companion object {
        var TABLE_NAME = "tbl_image"
    }

    interface Columns : BaseColumns {
        companion object {
            const val URL = "url"
            const val HEIGHT = "height"
            const val WIDTH = "width"
            const val THUMBNAIL = "thumbnail"
            const val THUMBNAIL_HEIGHT = "thumbnail_height"
            const val THUMBNAIL_WIDTH = "thumbnail_width"
            const val NAME = "name"
            const val TITLE = "title"
            const val PROVIDER = "provider"
            const val IMAGE_WEB_SEARCH_URL = "image_web_search_url"
            const val WEB_PAGE_URL = "web_page_url"
        }
    }
}