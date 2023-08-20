package com.chplalex.taxi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.chplalex.taxi.data.ChooseTaxiViewModel
import com.chplalex.taxi.data.ChooseTaxiViewModelFactory
import com.chplalex.taxi.databinding.FragmentChooseTaxiBinding
import com.chplalex.taxi.utils.Constants
import io.reactivex.rxjava3.disposables.Disposable

class ChooseTaxiFragment : Fragment() {

    private var _binding: FragmentChooseTaxiBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ChooseTaxiViewModel
    private var disposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentChooseTaxiBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pickUpPointText = requireArguments().getString(Constants.KEY_PICK_UP_POINT)!!
        val destinationPointText = requireArguments().getString(Constants.KEY_DESTINATION_POINT)!!

        with(binding) {
            pickUpPointChooseTaxi.text = pickUpPointText

            destinationPointChooseTaxi.text = destinationPointText

            recyclerViewChooseTaxi.layoutManager = LinearLayoutManager(context).also { it.orientation = VERTICAL }
        }

        viewModel = ViewModelProvider(
            this,
            ChooseTaxiViewModelFactory(pickUpPoint = pickUpPointText, destinationPoint = destinationPointText)
        )[ChooseTaxiViewModel::class.java]

        disposable = viewModel.getData().subscribe { taxiUiModels ->
            binding.recyclerViewChooseTaxi.adapter = ChooseTaxiAdapter(taxiUiModels)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.dispose()
        disposable?.dispose()
    }
}