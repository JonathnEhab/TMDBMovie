package com.example.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.presentation.databinding.FragmentDetailsBinding
import com.example.presentation.viewmodels.MovieDetailsViewModel
import com.example.state.detailsstate.DetailsIntent
import dagger.hilt.android.AndroidEntryPoint
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.domain.models.MovieDetailsDomainModel
import com.example.presentation.R
import com.example.state.detailsstate.DetailsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arguments?.getInt("id") ?: return
        movieDetailsViewModel.handleIntent(DetailsIntent.FetchMovieDetails(movieId))

        observeMovieDetails()
        binding.back.setOnClickListener {
            val options = NavOptions.Builder()
                .setEnterAnim(R.anim.enter_anim)
                .setExitAnim(R.anim.exit_anim)
                .setPopEnterAnim(R.anim.pop_enter_anim)
                .setPopExitAnim(R.anim.pop_exit_anim)
                .setLaunchSingleTop(true)
                .setPopUpTo(R.id.moviesFragment, true)
                .build()

            findNavController().navigate(
                DetailsFragmentDirections.actionDetailsFragmentToMoviesFragment(),
                options
            )
        }


    }

    private fun observeMovieDetails() {
        lifecycleScope.launch(Dispatchers.Main) {
            movieDetailsViewModel.detailsState.collectLatest { state ->
                when (state) {
                    is DetailsState.Loading -> {
                        Toast.makeText(requireContext(),"loading..",Toast.LENGTH_SHORT).show()
                    }
                    is DetailsState.Success -> {
                        displayMovieDetails(state.movies)
                    }
                    is DetailsState.Error -> {
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayMovieDetails(movie: MovieDetailsDomainModel) {
        binding.titleText.text = movie.title
        binding.voteAverageText.text = " Rating : ${movie.voteAverage}"
        binding.voteCountText.text = " ${movie.voteCount} votes"
        binding.releaseDateText.text = " ${movie.releaseDate}"
        binding.popularityText.text = " Popularity: ${movie.popularity}"
        binding.runTimeText.text= " Run Time : ${movie.runtime} min"
        binding.overviewText.text = movie.overview ?: "No overview available"

        Glide.with(requireContext())
            .load(movie.posterPath)
            .into(binding.backdropImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}