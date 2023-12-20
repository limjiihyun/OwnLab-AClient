package com.ownlab.ownlab_client.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ownlab.ownlab_client.R
import com.ownlab.ownlab_client.databinding.FragmentMainBinding
import com.ownlab.ownlab_client.utils.ApiResponse
import com.ownlab.ownlab_client.viewmodels.MainViewModel
import com.ownlab.ownlab_client.viewmodels.TokenViewModel
import com.ownlab.ownlab_client.viewmodels.`interface`.CoroutinesErrorHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val tokenViewModel: TokenViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var navController: NavController
    private var token: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        tokenViewModel.token.observe(viewLifecycleOwner) { token ->
            this.token = token

            if (token == null)
                navController.navigate(R.id.main_2_login)
        }

        mainViewModel.userResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ApiResponse.Failure -> Toast.makeText(activity, "Failed", Toast.LENGTH_LONG).show()
                is ApiResponse.Success -> Toast.makeText(activity, "${it.data.email}", Toast.LENGTH_LONG).show()
            }
        }

        binding.logoutBtn.setOnClickListener {
            tokenViewModel.delete()
        }

        binding.getUserBtn.setOnClickListener {
            mainViewModel.getUser(token, object: CoroutinesErrorHandler {
                override fun onError(message: String) {
                    Toast.makeText(activity, "Error $message", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}