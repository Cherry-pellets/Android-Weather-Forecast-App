package com.example.weatherforecastapp.ui.user

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.ui.weather.WeatherActivity
import com.example.weatherforecastapp.ui.weather.WeatherViewModel
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.fragment_config_left.*

class UserActivity : AppCompatActivity() {
//    val viewModel by lazy { ViewModelProviders.of(this).get(WeatherViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        replaceFragment(R.id.rightFragment, UserInfoFragment())
        // 返回天气信息界面
        backHomeBtn.setOnClickListener {
            returnWeatherActivity()
        }
        // 退出登录
        logoutBtn.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage("确定退出登录？")
                .setTitle("提示")
                .setPositiveButton("确定", DialogInterface.OnClickListener { dialogInterface, i ->
//                    System.exit(0)
                    val prefs = getSharedPreferences("userData", Context.MODE_PRIVATE)
                    prefs.edit().clear().commit()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                })
                .setNeutralButton("取消", null)
                .create()
                .show()
        }
        // App帮助信息
        aboutBtn.setOnClickListener {
            replaceFragment(R.id.rightFragment, AboutAppFragment())
        }
        // 修改用户信息
        modifyUserInfo.setOnClickListener {
            replaceFragment(R.id.rightFragment, UserInfoFragment())
        }
    }

    fun replaceFragment(fragmentId: Int, fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()  // 获取FragmentManager并开启事务
        transaction.replace(fragmentId, fragment)  // 动态添加fragment
        transaction.addToBackStack(null)  // tip: 按Back键时返回上一个fragment, 而不是直接退出APP
        transaction.commit() // 提交事务
    }

    fun returnWeatherActivity() {
        val locationLng = intent.getStringExtra("location_lng")
        val locationLat = intent.getStringExtra("location_lat")
        val placeName = intent.getStringExtra("place_name")
        val intent = Intent(this, WeatherActivity::class.java)
        intent.putExtra("location_lng", locationLng)
        intent.putExtra("location_lat", locationLat)
        intent.putExtra("place_name", placeName)
        startActivity(intent)
    }
}
