package com.example.laletin.picturerbog

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val FRAGMENT_TEXT = "fragment_text"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentWithText.FragmentWithTextListener] interface
 * to handle interaction events.
 * Use the [FragmentWithText.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FragmentWithText : Fragment() {
    private var textToDisplay: String? = null
    private var listener: FragmentWithTextListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            textToDisplay = it.getString(FRAGMENT_TEXT)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return TextView(activity).apply {
            text = textToDisplay
            textSize = 40.0f
            setOnClickListener { listener?.onTextClicked() }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentWithTextListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement FragmentWithTextListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface FragmentWithTextListener {
        fun onTextClicked()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param fragmentText Parameter 1.
         * @return A new instance of fragment FragmentWithText.
         */
        @JvmStatic
        fun newInstance(fragmentText: String) =
                FragmentWithText().apply {
                    arguments = Bundle().apply {
                        putString(FRAGMENT_TEXT, fragmentText)
                    }
                }
    }
}
