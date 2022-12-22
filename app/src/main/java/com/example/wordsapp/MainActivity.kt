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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.databinding.ActivityMainBinding

/**
 * Main Activity and entry point for the app. Displays a RecyclerView of letters.
 */
class MainActivity : AppCompatActivity() {
    val APP_LAYOUT_MANAGER_TYPE_LINEAR = 0
    val APP_LAYOUT_MANAGER_TYPE_GRID2 = 1
    val APP_LAYOUT_MANAGER_TYPE_GRID3 = 2
    val APP_LAYOUT_MANAGER_TYPE_GRID4 = 3
    val APP_LAYOUTS_AMOUNT = 4

    private var application_layout_type = APP_LAYOUT_MANAGER_TYPE_LINEAR

    fun getLayoutType(): Int {
        return application_layout_type
    }

    private lateinit var recyclerView: RecyclerView


    private fun chooseLayout() {
        if (application_layout_type == APP_LAYOUT_MANAGER_TYPE_LINEAR) {
            recyclerView.layoutManager = LinearLayoutManager(this)
        } else if (application_layout_type < APP_LAYOUTS_AMOUNT) {
            recyclerView.layoutManager = GridLayoutManager(this, application_layout_type+1)
        } else {
            throw Exception("Attempt to use a layout manager of an unknown type!")
        }
    }

    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null) return

        menuItem.icon = when (application_layout_type) {
            APP_LAYOUT_MANAGER_TYPE_LINEAR -> ContextCompat.getDrawable(this, R.drawable.ic_linear_layout)
            APP_LAYOUT_MANAGER_TYPE_GRID2 -> ContextCompat.getDrawable(this, R.drawable.ic_grid_2x2_layout)
            APP_LAYOUT_MANAGER_TYPE_GRID3 -> ContextCompat.getDrawable(this, R.drawable.ic_grid_3x3_layout)
            APP_LAYOUT_MANAGER_TYPE_GRID4 -> ContextCompat.getDrawable(this, R.drawable.ic_grid_4x4_layout)
            else -> throw Exception("Attempt to use a layout manager of an unknown type!")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.layout_menu, menu)

        val layoutButton = menu?.findItem(R.id.action_switch_layout)
        setIcon(layoutButton)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_layout -> {
                application_layout_type = (application_layout_type+1)%(APP_LAYOUTS_AMOUNT)
                chooseLayout()
                setIcon(item)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        // Sets the LinearLayoutManager of the recyclerview
        chooseLayout()
        recyclerView.adapter = LetterAdapter(recyclerView)
    }

}
