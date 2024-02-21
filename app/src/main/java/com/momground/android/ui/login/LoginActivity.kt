package com.momground.android.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.momground.android.MainActivity
import com.momground.android.R
import com.momground.android.common.BaseActivity
import com.momground.android.data.User
import com.momground.android.databinding.ActivityLoginBinding


class LoginActivity: BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private val googleSignInClient: GoogleSignInClient by lazy { getGoogleClient() }
    private val googleAuthLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

        try {
            val account = task.getResult(ApiException::class.java)

            // 이름, 이메일 등이 필요하다면 아래와 같이 account를 통해 각 메소드를 불러올 수 있다.
            val userName = account.givenName
            val serverAuth = account.serverAuthCode

            moveMainActivity()

        } catch (e: ApiException) {
            moveMainActivity()
        }
    }

    private fun moveMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun requestGoogleLogin() {
        googleSignInClient.signOut()
        val signInIntent = googleSignInClient.signInIntent
        googleAuthLauncher.launch(signInIntent)
    }

    private fun getGoogleClient(): GoogleSignInClient {
        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestScopes(Scope("https://www.googleapis.com/auth/pubsub"))
            .requestServerAuthCode(getString(R.string.google_login_client_id)) // string 파일에 저장해둔 client id 를 이용해 server authcode를 요청한다.
            .requestEmail() // 이메일도 요청할 수 있다.
            .build()

        return GoogleSignIn.getClient(this, googleSignInOption)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.signInButton.setOnClickListener(this)
        binding.buttonLogin.setOnClickListener(this)
        binding.buttonSignup.setOnClickListener(this)
        binding.buttonLoginLater.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view){
            binding.signInButton ->{
                requestGoogleLogin()
            }
            binding.buttonLogin ->{
                if(binding.email.text.toString() == "a") {
                    adminLogin()
                }
                else if(binding.email.text.toString() == "heejae@inha.edu"){
                    adminLogin()
                }
                else{
                    if(binding.email.text.toString().isNullOrBlank())  {
                        Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_LONG).show()
                    }
                    else if(binding.password.text.toString().isNullOrBlank())  {
                        Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this, "이메일 혹은 비밀번호가 잘못되었습니다", Toast.LENGTH_LONG).show()
                    }
                }
            }
            binding.buttonSignup ->{
                Toast.makeText(this, "아직 지원되지 않는 기능이에요", Toast.LENGTH_LONG).show()
                finish()
            }
            binding.buttonLoginLater->{
                finish()
            }
        }
    }

    private fun adminLogin(){
        User.isLogin = true
        User.name = "정희재"
        Toast.makeText(this, "로그인 되었습니다", Toast.LENGTH_LONG).show()
        finish()
    }
}