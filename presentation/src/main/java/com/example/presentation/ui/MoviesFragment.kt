package com.example.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.presentation.R
import com.example.presentation.adapter.MoviesAdapter
import com.example.presentation.adapter.ShimmerAdapter
import com.example.presentation.databinding.FragmentMoviesBinding
import com.example.presentation.viewmodels.MoviesViewModel
import com.example.state.moviesstate.MoviesIntent
import com.example.state.moviesstate.MoviesState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var nowPlayingAdapter: MoviesAdapter
    private lateinit var shimmerAdapter: ShimmerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeMovies()
        viewModel.handleIntent(MoviesIntent.FetchNowPlayingMovies)
    }

    private fun setupRecyclerView() {
        nowPlayingAdapter = MoviesAdapter { id ->
            val navController = findNavController()
            val options = NavOptions.Builder()
                .setEnterAnim(R.anim.enter_anim)
                .setExitAnim(R.anim.exit_anim)
                .setPopEnterAnim(R.anim.pop_enter_anim)
                .setPopExitAnim(R.anim.pop_exit_anim)
                .setLaunchSingleTop(true)
                .setPopUpTo(R.id.moviesFragment, false)
                .build()
            navController.navigate(
                MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(id),
                options
            )
        }
        shimmerAdapter = ShimmerAdapter(10)

        binding.nowPlay.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = shimmerAdapter
        }
    }

    private fun observeMovies() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.nowPlayingMoviesState.collect { state ->
                when (state) {
                    is MoviesState.Loading -> {
                        binding.errorMassages.visibility=View.GONE
                        binding.nowPlay.visibility=View.VISIBLE
                        binding.nowPlay.adapter = shimmerAdapter
                    }
                    is MoviesState.Success -> {
                        binding.nowPlay.visibility=View.VISIBLE
                        nowPlayingAdapter.submitList(state.movies)
                        binding.nowPlay.adapter = nowPlayingAdapter
                    }
                    is MoviesState.Error -> {
                        binding.errorMassages.visibility=View.VISIBLE
                        binding.nowPlay.visibility=View.GONE
                        binding.errorMassages.text=state.message

                        Log.d("MoviesActivity", "Error: ${state.message}")
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
