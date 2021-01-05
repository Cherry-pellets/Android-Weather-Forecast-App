package com.example.weatherforecastapp.ui.user

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.logic.model.User
import com.example.weatherforecastapp.logic.network.UserService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserInfoFragment : Fragment() {
    val TAG = "userInfoFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_user_info, container, false)
        val userActivity = activity as UserActivity
        // 显示用户信息
        val prefs = userActivity.getSharedPreferences("userData", Context.MODE_PRIVATE)
        val name = prefs.getString("name", "")
        val password = prefs.getString("password", "")
        var nickname = prefs.getString("nickname", "无名氏")
        var profile = prefs.getString("profile", "这个人很懒，什么也没留下")

        val userName: TextView = layout.findViewById(R.id.userName)
        val modifyNickName: EditText = layout.findViewById(R.id.modifyNickName)
        val modifyProfile: EditText = layout.findViewById(R.id.modifyProfile)

        userName.text = name
        modifyNickName.hint = nickname
        modifyProfile.hint= profile

        val submitBtn: Button = layout.findViewById(R.id.submitBtn)
        submitBtn.setOnClickListener {
            if (modifyNickName.text.toString() == nickname && modifyProfile.text.toString() == profile || (TextUtils.isEmpty(modifyNickName.text.toString()) && TextUtils.isEmpty(modifyProfile.text.toString()))) {
                Toast.makeText(userActivity, "未做任何修改", Toast.LENGTH_SHORT).show()
            } else {
                if (modifyNickName.text.toString() != nickname &&  !TextUtils.isEmpty(modifyNickName.text.toString())) {
                    nickname = modifyNickName.text.toString()
                }
                if (modifyProfile.text.toString() != profile &&  !TextUtils.isEmpty(modifyProfile.text.toString())) {
                    profile = modifyProfile.text.toString()
                }
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://175.24.117.117:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val userService = retrofit.create(UserService::class.java)
                Log.d(TAG, "$nickname---${modifyNickName.text.toString()}")
                val user = User("$password", "$name","$nickname", "$profile")
                userService.modifyUserInfo(user).enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        when(response.code()) {
                            200 -> {
                                Toast.makeText(userActivity, "修改成功！", Toast.LENGTH_SHORT).show()
                                val editor = prefs.edit()
                                editor.putString("nickname", nickname)
                                editor.putString("profile", profile)
                                editor.apply()
                                userActivity.returnWeatherActivity()
                            }
                            else -> {
                                Toast.makeText(userActivity, response.message(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(userActivity, "修改失败，请稍后重试", Toast.LENGTH_SHORT).show()
                        t.printStackTrace()
                    }
                })
            }
        }
        return layout
    }
}
