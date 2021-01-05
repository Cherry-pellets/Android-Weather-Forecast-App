package com.example.weatherforecastapp.ui.user

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.weatherforecastapp.MainActivity
import com.example.weatherforecastapp.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val prefs = getSharedPreferences("userData", Context.MODE_PRIVATE)
        val name = prefs.getString("name", "")
//        val nickname = prefs.getString("nickname", "无名氏")
//        val profile = prefs.getString("profile", "这个人很懒，什么也没留下")

        if (name == "") {// 未登录
            replaceFragment(R.id.loginFragment, LoginFragment())
        } else { // 已登录
            val intent = Intent(this, MainActivity::class.java)
             startActivity(intent)
        }
    }

    fun replaceFragment(fragmentId: Int, fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()  // 获取FragmentManager并开启事务
        transaction.replace(fragmentId, fragment)  // 动态添加fragment
        transaction.addToBackStack(null)  // tip: 按Back键时返回上一个fragment, 而不是直接退出APP
        transaction.commit() // 提交事务
    }
}
