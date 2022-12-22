/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.wordsapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {
    companion object {
        const val LETTER = "letter"
        const val LAYOUT = "layout"
        const val SEARCH_PREFIX = "https://www.google.com/search?q="
    }

    val APP_LAYOUT_MANAGER_TYPE_LINEAR = 0
    val APP_LAYOUT_MANAGER_TYPE_GRID2 = 1
    val APP_LAYOUT_MANAGER_TYPE_GRID3 = 2
    val APP_LAYOUT_MANAGER_TYPE_GRID4 = 3
    val APP_LAYOUTS_AMOUNT = 4

    fun setLayoutManager(recyclerView: RecyclerView) {
        val application_layout_type = (intent?.extras?.getInt(LAYOUT))!!

        if (application_layout_type == APP_LAYOUT_MANAGER_TYPE_LINEAR) {
            recyclerView.layoutManager = LinearLayoutManager(this)
        } else if (application_layout_type < APP_LAYOUTS_AMOUNT) {
            recyclerView.layoutManager = GridLayoutManager(this, application_layout_type+1)
        } else {
            throw Exception("Attempt to use a layout manager of an unknown type!")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve a binding object that allows you to refer to views by id name
        // Names are converted from snake case to camel case.
        // For example, a View with the id word_one is referenced as binding.wordOne
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the LETTER from the Intent extras
        // intent.extras.getString returns String? (String or null)
        // so toString() guarantees that the value will be a String
        val letterId = intent?.extras?.getString(LETTER).toString()

        val recyclerView = binding.recyclerView
        setLayoutManager(recyclerView)

        recyclerView.adapter = WordAdapter(letterId, this)

        // Adds a [DividerItemDecoration] between items
        recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        title = getString(R.string.detail_prefix) + " " + letterId
    }
}