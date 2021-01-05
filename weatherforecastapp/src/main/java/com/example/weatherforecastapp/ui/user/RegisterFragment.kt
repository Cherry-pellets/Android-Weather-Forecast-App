package com.example.weatherforecastapp.ui.user

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

import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.logic.model.Holder
import com.example.weatherforecastapp.logic.model.User
import com.example.weatherforecastapp.logic.network.UserService
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_register.*
import okhttp3.FormBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterFragment : Fragment() {
    val TAG = "registerFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_register, container, false)
        // 从注册页切换到登录页
        val loginActivity = activity as LoginActivity
        val toLoginBtn:Button = layout.findViewById(R.id.toLoginBtn)
        toLoginBtn.setOnClickListener {
            loginActivity.replaceFragment(R.id.loginFragment, LoginFragment())
        }
        // 点击注册
        val registerUserName: EditText = layout.findViewById(R.id.registerUserName)
        val registerNickName: EditText = layout.findViewById(R.id.registerNickName)
        val registerProfile:EditText = layout.findViewById(R.id.registerProfile)
        val registerPassword: EditText = layout.findViewById(R.id.registerPassword)
        // 密码不可见
        registerPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        val registerBtn: Button = layout.findViewById(R.id.registerBtn)
        registerBtn.setOnClickListener {
            if (TextUtils.isEmpty(registerUserName.text) || TextUtils.isEmpty(registerNickName.text)
                || TextUtils.isEmpty(registerPassword.text)) {
                Toast.makeText(loginActivity, "请填写姓名、昵称以及密码", Toast.LENGTH_SHORT).show()
            } else {
                val user = User( registerPassword.text.toString(), registerUserName.text.toString(),
                    registerNickName.text.toString(), registerProfile.text.toString())

                val retrofit = Retrofit.Builder()
                    .baseUrl("http://175.24.117.117:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val userService = retrofit.create(UserService::class.java)
                userService.register(user).enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        val data = response.code()
                        Log.d(TAG, "----- $data")
                        when(response.code()) {
                            200 -> {
                                Toast.makeText(loginActivity, "注册成功！请登录", Toast.LENGTH_SHORT).show()
                                loginActivity.replaceFragment(R.id.loginFragment, LoginFragment())
                            }
                            else -> Toast.makeText(loginActivity, response.message(), Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(loginActivity, "注册失败！请重试", Toast.LENGTH_SHORT).show()
                        t.printStackTrace()
                    }
                })
            }
        }
        return layout
    }
}
