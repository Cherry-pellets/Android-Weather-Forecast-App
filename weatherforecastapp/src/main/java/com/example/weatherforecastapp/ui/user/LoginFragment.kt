package com.example.weatherforecastapp.ui.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.weatherforecastapp.MainActivity

import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.logic.model.Holder
import com.example.weatherforecastapp.logic.model.User
import com.example.weatherforecastapp.logic.network.UserService
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_login.*
import okhttp3.ResponseBody
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginFragment : Fragment() {
    val TAG = "loginFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_login, container, false)
        // 从登录页面切换到注册页面
        val loginActivity = activity as LoginActivity
        val toRegisterBtn: Button = layout.findViewById(R.id.toRegisterBtn)
        toRegisterBtn.setOnClickListener {
            loginActivity.replaceFragment(R.id.loginFragment, RegisterFragment())
        }
        // 点击登录
        val userName:EditText = layout.findViewById(R.id.userName)
        val password: EditText = layout.findViewById(R.id.password)
        // 密码不可见
        password.transformationMethod = PasswordTransformationMethod.getInstance()
        val loginBtn:Button = layout.findViewById(R.id.loginBtn)
        loginBtn.setOnClickListener {
            if (TextUtils.isEmpty(userName.text) || TextUtils.isEmpty(password.text)) {
                Toast.makeText(loginActivity, "请填写姓名以及密码", Toast.LENGTH_SHORT).show()
            } else {
//                Log.d(TAG, "${userName.text}--${password.text}")
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://175.24.117.117:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val userService = retrofit.create(UserService::class.java)
                val user = User( password.text.toString(),userName.text.toString(),"", "")
                userService.login(user).enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        when (response.code()) {
                            200 -> {
                                val jsonStr = response.body()?.string()
                                val gson = Gson()
                                val userInfo = gson.fromJson(jsonStr,Holder::class.java).data
                                Log.d(TAG, "---${userInfo?.name}")
                                // 把用户信息存储到本地
                                val editor = loginActivity.getSharedPreferences("userData", Context.MODE_PRIVATE).edit()
                                editor.putString("name", userInfo?.name)
                                editor.putString("nickname", userInfo?.nickname)
                                editor.putString("profile", userInfo?.profile)
                                editor.putString("password", userInfo?.password)
                                editor.apply()
//                                editor.putString("head_portait", )
                                val intent = Intent(loginActivity, MainActivity::class.java)
                                startActivity(intent)
                            }
                            else -> {
                                Toast.makeText(loginActivity, response.message(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(loginActivity, "登录失败，请重试", Toast.LENGTH_SHORT).show()
                        t.printStackTrace()
                    }
                })
            }
        }
        return layout
    }
}
