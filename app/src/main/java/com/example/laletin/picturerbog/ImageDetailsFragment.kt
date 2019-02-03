package com.example.laletin.picturerbog

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.laletin.picturerbog.R.string.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content.view.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "image"

/**
 * A simple [Fragment] subclass.
 * Use the [UserDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ImageDetailsFragment : Fragment() {
    private lateinit var image: Images

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            image = it.getParcelable(ARG_PARAM1)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.content, container, false).apply { ->
        Picasso.with(context)
                .load(image.urlL)
                .placeholder(R.drawable.ic_home_black_24dp)
                .error(R.drawable.ic_home_black_24dp)
                .into(imageView)

        val id = image.id.toLong()

        val db = App.instance.database
        val dao = db?.imageDao()
        val imageById = dao?.getById(id)

        if (imageById == null) {
            addFavor.text = resources.getString(add_to_favorites)
        } else {
            addFavor.text = resources.getString(remove_from_favorites)
        }

        addFavor.setOnClickListener {
            if (addFavor.text == resources.getString(add_to_favorites)) {
                addFavor.text = resources.getString(remove_from_favorites)

                val db = App.instance.database
                val imageDao = db?.imageDao()
                val imageSQL = ImageEntity()
                imageSQL.id = image.id.toLong()
                imageSQL.title = image.title
                imageSQL.urlS = image.urlS
                imageSQL.urlL = image.urlL

                imageDao?.delete(imageSQL)
                imageDao?.insert(imageSQL)
            } else {
                addFavor.text = resources.getString(add_to_favorites)

                val db = App.instance.database
                val imageDao = db?.imageDao()
                val imageSQL = ImageEntity()
                imageSQL.id = image.id.toLong()

                imageDao?.delete(imageSQL)
            }
        }

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment UserDetailsFragment.
         */
        @JvmStatic
        fun newInstance(param1: Images) =
                ImageDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_PARAM1, param1)
                    }
                }
    }
}
