package com.example.wedservicedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.UserHandle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wedservicedemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    private  var users=ArrayList<User>()
    private  var usersAdapter=UserAdapter(users)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        initListener()
    }

    private fun initListener() {
        binding.btnSimpleRequest.setOnClickListener {
            WebThread(null).execute(
                *arrayOf(binding.edtTargetUrl.text.toString())
            )
        }
        binding.btnGetUsers.setOnClickListener {
         WebThread(UsersHandler()).execute(null)
        }
    }

    private fun initRecyclerView() {
        binding.recyclerUsers.adapter=usersAdapter
        binding.recyclerUsers.layoutManager=LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,
            false)

    }
    inner class UsersHandler:Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg!=null){
                var users=msg.obj as ArrayList<User>
                this@MainActivity.users.addAll(users)
                usersAdapter.notifyDataSetChanged()
            }
        }
    }
}