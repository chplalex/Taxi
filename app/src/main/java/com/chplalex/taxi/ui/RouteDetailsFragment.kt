package com.chplalex.taxi.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.chplalex.taxi.R
import com.chplalex.taxi.databinding.FragmentRouteDetailsBinding
import com.chplalex.taxi.utils.Constants


class RouteDetailsFragment : Fragment() {

    private var _binding: FragmentRouteDetailsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRouteDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSearch.setOnClickListener {
            val pickUpPoint = binding.inputPickUpPoint.text.toString().trim()
            val destinationPoint = binding.inputDestinationPoint.text.toString().trim()

            hideKeyboard()

            if (pickUpPoint.isEmpty() && destinationPoint.isEmpty()) {
                showToast(R.string.points_are_empty)
                return@setOnClickListener
            }

            if (pickUpPoint.isEmpty()) {
                showToast(R.string.pick_up_point_is_empty)
                return@setOnClickListener
            }

            if (destinationPoint.isEmpty()) {
                showToast(R.string.destination_point_is_empty)
                return@setOnClickListener
            }

            val bundle = Bundle().also {
                it.putString(Constants.KEY_PICK_UP_POINT, pickUpPoint)
                it.putString(Constants.KEY_DESTINATION_POINT, destinationPoint)
            }

            findNavController().navigate(R.id.action_Route_Details_to_Choose_Taxi, bundle)
        }
    }

    private fun hideKeyboard() {
        try {
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
        } catch (_: Exception) {
            // nothing
        }
    }

    private fun showToast(stringId: Int) {
        Toast.makeText(
            activity,
            resources.getString(stringId),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}