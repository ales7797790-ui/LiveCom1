package com.livecom.app.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.livecom.app.core.util.Result
import com.livecom.app.databinding.ActivityMainBinding
import com.livecom.app.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val authVm: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                authVm.state.collect { s ->
                    when (s) {
                        is Result.Loading -> binding.status.text = "جاري الدخول..."
                        is Result.Success -> binding.status.text = "UID: " + s.data
                        is Result.Error -> {
                            binding.status.text = "فشل الدخول"
                            Toast.makeText(this@MainActivity, s.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}
