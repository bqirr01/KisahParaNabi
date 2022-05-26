package com.basya.kisahparanabi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.basya.kisahparanabi.data.KisahResponse
import com.basya.kisahparanabi.databinding.ActivityMainBinding
import com.basya.kisahparanabi.ui.detail.DetailActivity
import com.basya.kisahparanabi.utils.OnItemClickCallback

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding as ActivityMainBinding

    private var _viewModel: MainViewModel? = null
    private val viewModel get() = _viewModel as MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getDataForView()

        viewModel.kisahResponse.observe(this){showData(it)}
        viewModel.isLoading.observe(this){showLoading(it)}
        viewModel.isError.observe(this){showError(it)}

    }

    private fun showError(isError: Throwable?) {
        Toast.makeText(this, isError?.localizedMessage, Toast.LENGTH_SHORT).show()
        Log.e("Tag",isError?.localizedMessage.toString())

    }

    private fun showLoading(isLoading: Boolean?) {
        if (isLoading == true) {
            binding.progressMain.visibility = View.VISIBLE
        }else{
            binding.progressMain.visibility = View.GONE
        }
    }

    private fun showData(data: List<KisahResponse>?) {
        val mAdapter = KisahAdapter()
        binding.recyclerMain.apply{
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            mAdapter.setData(data)
            adapter = mAdapter
            mAdapter.setOnItemClickCallback(object : OnItemClickCallback{
                override fun onItemClicked(item: KisahResponse) {
                    startActivity(
                        Intent(this@MainActivity, DetailActivity::class.java)
                            .putExtra(DetailActivity.EXTRA_DATA, item)
                    )
                }
            })
        }
    }
}