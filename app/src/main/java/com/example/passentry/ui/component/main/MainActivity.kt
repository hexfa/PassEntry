package com.example.passentry.ui.component.main

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.passentry.R
import com.example.passentry.data.remote.tap.TapList
import com.example.passentry.data.remote.tap.TapResponse
import com.example.passentry.databinding.ActivityMainBinding
import com.example.passentry.databinding.ItemViewBinding
import com.example.passentry.databinding.LoginActivityBinding
import com.example.passentry.ui.base.BaseActivity
import com.example.passentry.ui.component.login.LoginActivity
import com.example.passentry.ui.component.login.LoginViewModel
import com.example.passentry.ui.component.splash.SplashActivity
import com.example.passentry.utils.AUTH_TOKEN
import com.example.passentry.utils.PASSWORD
import com.example.passentry.utils.USERNAME
import dagger.hilt.android.AndroidEntryPoint
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
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
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.dark_blue)

        val recyclerView = binding.listRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        loginViewModel.taps("hello@passentry.com", "securepass")?.observe(this) {
            recyclerView.adapter = RecyclerViewAdapter(it)
            binding.emptyView.visibility=View.GONE

        }

        binding.exitTxtView.setOnClickListener {
            appInfo.edit().apply {
                putString(AUTH_TOKEN,"")

            }.apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}

class RecyclerViewAdapter(private val items: List<TapResponse>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    class ViewHolder(private val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n")
        fun bind(item: TapResponse) {
            val zonedDateTime = ZonedDateTime.parse(item.tappedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)

            binding.readerIdTextView.text = "Reader ID: "+item.readerId
            binding.tappedAtTextView.text = "Date: "+zonedDateTime.year +'/'+zonedDateTime.monthValue+'/'+zonedDateTime.dayOfMonth
            binding.tappedAtTime.text = "Time: "+zonedDateTime.hour+':'+zonedDateTime.minute
            binding.statusTextView.text = "Status: "+item.status
        }
    }

    // And in your adapter's onCreateViewHolder:
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item:TapResponse = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size


}