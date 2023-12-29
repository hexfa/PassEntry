package com.example.passentry.ui.component.main

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.passentry.R
import com.example.passentry.data.remote.tap.TapList
import com.example.passentry.databinding.ActivityMainBinding
import com.example.passentry.databinding.ItemViewBinding
import com.example.passentry.databinding.LoginActivityBinding
import com.example.passentry.ui.base.BaseActivity
import com.example.passentry.ui.component.login.LoginViewModel
import com.example.passentry.utils.PASSWORD
import com.example.passentry.utils.USERNAME
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    @Inject
    lateinit var appInfo: SharedPreferences
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val recyclerView = binding.listRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        var username = appInfo.getString(USERNAME, null)
        var pass = appInfo.getString(PASSWORD, null)
        loginViewModel.taps("hello@passentry.com", "securepass")?.observe(this) {
           // recyclerView.adapter = RecyclerViewAdapter(it)
Log.d("maiiiiiiiiiiiiiiiiiin",it.readerId)
        }
    }
}

class RecyclerViewAdapter(private val items: TapList) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    class ViewHolder(private val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.itemTextView.text = item
        }
    }

    // And in your adapter's onCreateViewHolder:
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item.readerId)
    }

    override fun getItemCount(): Int = items.size


}