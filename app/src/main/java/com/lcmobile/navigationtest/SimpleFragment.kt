package com.lcmobile.navigationtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_simple.*

class SimpleFragment : Fragment() {

    companion object {
        private const val EXTRA_DESCRIPTION = "description"
        fun newInstance(description: String) = SimpleFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_DESCRIPTION, description)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_simple, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        description.text = arguments?.getString(EXTRA_DESCRIPTION)
    }
}