package com.example.testapi2.ui.details

//import kotlinx.android.synthetic.main.activity_single_movie.*
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.testapi2.R
import com.example.testapi2.data.api.POSTER_BASE_URL
import com.example.testapi2.data.api.TheMovieDBClient
import com.example.testapi2.data.api.TheMovieDBInterface
import com.example.testapi2.data.repository.NetworkState
import com.example.testapi2.data.vo.MovieDetails
import java.text.NumberFormat
import java.util.*


class SingleMovie : Fragment() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository

    private lateinit var progress_bar: ProgressBar
    private lateinit var txt_error: TextView

    private lateinit var movie_title: TextView
    private lateinit var movie_tagline: TextView
    private lateinit var movie_release_date: TextView
    private lateinit var movie_rating: TextView
    private lateinit var movie_runtime: TextView
    private lateinit var movie_overview: TextView

    private lateinit var movie_budget: TextView
    private lateinit var movie_revenue: TextView
    private lateinit var iv_movie_poster: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.activity_single_movie, container, false)
        progress_bar = root.findViewById(R.id.progress_bar)
        txt_error = root.findViewById(R.id.txt_error)

        //val intent = Intent(R.layout.activity_single_movie, SingleMovie::class.java)
        //val movieId: Int = intent.getIntExtra("id", 1)

        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(viewLifecycleOwner, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })

    }

        @SuppressLint("SetTextI18n")
        fun bindUI(it: MovieDetails){
            movie_title.text = it.title
            movie_tagline.text = it.tagline
            movie_release_date.text = it.releaseDate
            movie_rating.text = it.rating.toString()
            movie_runtime.text = it.runtime.toString() + " minutes"
            movie_overview.text = it.overview

            val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
            movie_budget.text = formatCurrency.format(it.budget)
            movie_revenue.text = formatCurrency.format(it.revenue)

            val moviePosterURL = POSTER_BASE_URL + it.posterPath
            Glide.with(this)
                .load(moviePosterURL)
                .into(iv_movie_poster);
        }


        private fun getViewModel(movieId: Int): SingleMovieViewModel {
            return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return SingleMovieViewModel(movieRepository, movieId) as T
                }
            })[SingleMovieViewModel::class.java]
        }
}
