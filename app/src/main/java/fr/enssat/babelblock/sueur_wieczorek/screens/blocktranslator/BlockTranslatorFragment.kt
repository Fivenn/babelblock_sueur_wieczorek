package fr.enssat.babelblock.sueur_wieczorek.screens.blocktranslator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.enssat.babelblock.sueur_wieczorek.R
import fr.enssat.babelblock.sueur_wieczorek.databinding.BlockTranslatorFragmentBinding
import fr.enssat.babelblock.sueur_wieczorek.tools.ui.BlockTranslatorChainAdapter
import fr.enssat.babelblock.sueur_wieczorek.tools.ui.BlockTranslatorChainMoveHelper

class BlockTranslatorFragment : Fragment() {

    private lateinit var viewModel: BlockTranslatorViewModel
    private lateinit var binding: BlockTranslatorFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(BlockTranslatorViewModel::class.java)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.block_translator_fragment,
            container,
            false
        )

        // Add an observer on workManager to get the result of a TranslateWorker
        viewModel.outputWorkInfos.observe(viewLifecycleOwner, viewModel.workInfosObserver())

        // Adapter of the block translator chain
        val adapter = BlockTranslatorChainAdapter(viewModel.blockTranslatorChain)

        // Dedicated drag and drop move helper
        val moveHelper = BlockTranslatorChainMoveHelper.create(adapter)
        moveHelper.attachToRecyclerView(binding.blockTranslator)

        // Block translator chain
        binding.blockTranslator.adapter = adapter

        // Languages list
        binding.languagesList.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            viewModel.availableLanguages.sorted()
        )
        binding.languagesList.setOnItemClickListener { _, _, position, _ ->
            viewModel.blockTranslatorChain.add(viewModel.createBlockTranslatorAt(position))
            viewModel.translateBlockTranslatorChain()
        }

        return binding.root
    }
}