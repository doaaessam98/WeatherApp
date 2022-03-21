package com.example.weatherApp.favorite

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherApp.R
import com.example.weatherApp.databinding.FragmentFavoriteBinding
import com.example.weatherApp.home.HomeViewModel
import com.example.weatherApp.map.MapFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteFragment : Fragment() ,OnFavMenuClick{

    private val favViewModel: FavouriteViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    lateinit var binding:FragmentFavoriteBinding
    lateinit var favAdapter:FavouriteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_favorite, container, false)
        binding.lifecycleOwner=this
        binding.viewModel=favViewModel
        favAdapter=FavouriteAdapter(this, arrayListOf(),requireContext())
        initUi()
        favViewModel.getAllFavouriteFromDataBase().observe(viewLifecycleOwner,
            Observer {
                it?.let {
                    if(it.isEmpty()){
                        binding.favIamge.visibility=View.VISIBLE
                    }
                    else{
                        binding.favIamge.visibility=View.GONE
                    }
                    favAdapter.setData(it)
                }
            })

         binding.addFloutBtn.setOnClickListener {
             val bundle= Bundle()
             bundle.putString("mapId","fav")
             val mapFragment=MapFragment()
             mapFragment.arguments=bundle
             Navigation.findNavController(it).navigate(R.id.action_favoriteFragment_to_mapFragment,bundle);

         }


        return binding.root
    }

    private fun initUi() {

        binding.favRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = favAdapter
        }
    }

    companion object {


    }



    override fun onClick(view: View,timezone:String) {
        var popup = PopupMenu(requireContext(),view);
        popup.inflate(R.menu.more_menu);
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete -> {
                    favViewModel.deleteFromFav(timezone)
                    favAdapter.notifyDataSetChanged()
                    showSnakBar(view)
                }

            }
            false
        }
        popup.show();


    }

    private fun showSnakBar(view: View) {
//        val snack = Snackbar.make(view.findViewById(R.id.delete),R.string.deleted,Snackbar.LENGTH_LONG)
//        snack.setAction(R.string.dismiss, View.OnClickListener {
//
//        })
//        snack.show()

    }
}