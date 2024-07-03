package com.fintech.quotify

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.fintech.quotify.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(applicationContext)
        )[MainViewModel::class.java]


        setQuote(viewModel.getQuote())
        binding.tvPrevious.setOnClickListener {
            setQuote(viewModel.previousQuote())
        }
        binding.tvNext.setOnClickListener {
            setQuote(viewModel.nextQuote())
        }
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(
                Intent.EXTRA_TEXT,
                viewModel.getQuote().text + "- " + viewModel.getQuote().author
            )
            startActivity(intent)
        }

    }

    private fun setQuote(quote: Quote) {
        binding.tvPrevious.visibility =
            if (viewModel.showPrevious()) View.INVISIBLE else View.VISIBLE
        binding.tvNext.visibility = if (viewModel.showNext()) View.INVISIBLE else View.VISIBLE

        binding.quoteText.text = quote.text
        binding.quoteAuthor.text = quote.author
    }
}