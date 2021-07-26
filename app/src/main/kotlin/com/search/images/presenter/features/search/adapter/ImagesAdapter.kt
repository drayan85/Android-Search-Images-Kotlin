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
package com.search.images.presenter.features.search.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.search.images.R
import com.search.images.presenter.core.extension.inflate
import com.search.images.presenter.core.extension.loadFromUrl
import com.search.images.presenter.core.navigation.Navigator
import com.search.images.presenter.model.ImageModelParcelable
import kotlinx.android.synthetic.main.row_images.view.*

/**
 * @author Paramanathan Ilanthirayan <theebankala@gmail.com>
 * @version 1.0.0
 * @since 25th of July 2021
 */
class ImagesAdapter(listItems: List<ImageModelParcelable>) : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {

    private var collection: MutableList<ImageModelParcelable> = mutableListOf()

    init {
        collection.addAll(listItems)
    }

    internal var clickListener: (ImageModelParcelable, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.row_images))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(imageModelParcelable: ImageModelParcelable, clickListener: (ImageModelParcelable, Navigator.Extras) -> Unit) {
            itemView.imageThumbnail.loadFromUrl(imageModelParcelable.thumbnail)
            itemView.setOnClickListener { clickListener(imageModelParcelable, Navigator.Extras(itemView.imageThumbnail)) }
        }
    }

    fun addMoreItemsAtTheBottomOfTheList(imageModelParcelableList: List<ImageModelParcelable>) {
        //val start: Int = collection.size - 1
        collection.addAll(imageModelParcelableList)
        //notifyItemRangeInserted(start, collection.size)
        notifyDataSetChanged()
    }
}

