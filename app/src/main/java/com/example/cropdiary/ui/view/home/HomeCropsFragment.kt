package com.example.cropdiary.ui.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcont.adapter.MyItemRecyclerViewAdapter
import com.example.cropdiary.core.adapter.WorkNewCropAdapter
import com.example.cropdiary.core.util.CropsUtilities
import com.example.cropdiary.core.view.ViewHelper
import com.example.cropdiary.data.model.WorkModel
import com.example.cropdiary.databinding.FragmentHomeCropsBinding
import com.example.cropdiary.ui.viewmodel.CropsViewModel
import com.example.cropdiary.ui.viewmodel.views.HomeCropsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeCropsFragment : Fragment() {
    private lateinit var binding: FragmentHomeCropsBinding
    private val homeCropsFragmentViewModel: HomeCropsFragmentViewModel by viewModels()
    private val cropsViewModel: CropsViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var worksList: List<WorkModel>

    @Inject
    lateinit var adapter: MyItemRecyclerViewAdapter
    lateinit var listAdapter: WorkNewCropAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeCropsBinding.inflate(layoutInflater)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        listAdapter = WorkNewCropAdapter(requireContext(), cropsViewModel)
        binding.listWorksCrops.adapter = listAdapter
        worksList = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        cropsViewModel.getData()
        setup()
        loadViewStates()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        with(binding) {
            with(homeCropsFragmentViewModel) {
                if (includeFormInfoNewCrop.editTextNameNewCrop.text.toString().isNotEmpty()) {
                    saveCropName(includeFormInfoNewCrop.editTextNameNewCrop.text.toString())
                } else {
                    clearCropName()
                }

                if (addWorkForm.editTextNameNewCrop.text.toString().isNotEmpty()) {
                    saveWorkName(addWorkForm.editTextNameNewCrop.text.toString())
                } else {
                    clearWorkName()
                }
            }
        }
    }

    private fun loadViewStates() {
        with(binding) {
            with(homeCropsFragmentViewModel) {
                with(ViewHelper) {
                    with(includeFormInfoNewCrop) {
                        checkBoxDescriptionCrop.observe(viewLifecycleOwner) {
                            setVisibility(editTextNameDescriptioCrop, it)
                        }
                        cropName.observe(viewLifecycleOwner) {
                            if (it != null) {
                                editTextSetText(editTextNameNewCrop, it)
                            } else {
                                clearEditText(editTextNameNewCrop)
                            }
                        }

                    }
                    listWorksContainer.observe(viewLifecycleOwner) {
                        setVisibility(cardviewWorksCrops, it)
                    }
                    btnAdd.observe(viewLifecycleOwner) {
                        setVisibility(btnAddCultivo, it)
                    }
                    newCropForm.observe(viewLifecycleOwner) {
                        setVisibility(formNewCrop, it)
                    }

                    switch.observe(viewLifecycleOwner) {
                        setVisibility(containerAddWorksNewCrop, it)
                    }
                    with(addWorkForm) {
                        workName.observe(viewLifecycleOwner) {
                            if (it != null) {
                                editTextSetText(editTextNameNewCrop, it)
                            } else {
                                clearEditText(editTextNameNewCrop)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setup() {
        with(cropsViewModel) {
            cropsData.observe(viewLifecycleOwner) {
                if (it.isSuccess) {
                    adapter.setData(it.getOrNull())
                }
            }

            cropsResult.observe(viewLifecycleOwner) {
                if (it.isSuccess) {
                    Log.w("Tag Crop", "Se creó")
                    initStateForms()
                    cropsViewModel.getData()
                }
            }
            with(worksViewModel) {
                worksData.observe(viewLifecycleOwner) {
                    worksList = it
                    with(homeCropsFragmentViewModel) {
                        if (it.isEmpty()) {
                            hideListWorks()
                        } else {
                            showListWorks()
                        }
                    }
                    listAdapter.setData(it, binding.listWorksCrops)
                }

                worksResult.observe(viewLifecycleOwner) {
                    if (it.isSuccess) {
                        worksViewModel.getListWork()
                        ViewHelper.clearEditText(binding.addWorkForm.editTextNameNewCrop)
                    }
                }
            }

        }

        with(binding) {
            with(homeCropsFragmentViewModel) {
                btnAddCultivo.setOnClickListener {
                    hideBtnAddCrop()
                    showNewCropForm()
                }

                with(includeFormInfoNewCrop) {
                    checkBox.setOnClickListener {
                        if (includeFormInfoNewCrop.checkBox.isChecked) {
                            setCheckBoxCropStateOn()
                        } else {
                            setCheckBoxCropStateOff()
                        }
                    }

                    switchAddCategory.setOnClickListener {
                        if (includeFormInfoNewCrop.switchAddCategory.isChecked) {
                            setSwitchStateOn()
                        } else {
                            setSwitchStateOff()
                        }
                    }
                }
            }
            btnRegistreCrop.setOnClickListener {
                addCrop()
            }

            addWorkForm.btnAddWork.setOnClickListener {
                addWork()
            }

            btnCancelRegistreCrop.setOnClickListener {
                initStateForms()
            }
        }
    }

    private fun addCrop() {
        with(binding) {
            with(includeFormInfoNewCrop){
                CropsUtilities.validateCrop(editTextNameNewCrop, requireActivity(), worksList, checkBox, editTextNameDescriptioCrop) {
                    if (it != null) {
                        cropsViewModel.createCrop(it)
                    }
                }
            }
        }
    }


    private fun addWork() {
        with(binding) {
            CropsUtilities.validateWork(addWorkForm.editTextNameNewCrop, requireActivity(), worksList) {
                if (it != null) {
                    cropsViewModel.worksViewModel.addWork(it)
                }
            }
        }
    }



    private fun initStateForms() {
        ViewHelper.clearEditText(binding.includeFormInfoNewCrop.editTextNameNewCrop)
        homeCropsFragmentViewModel.restoreInitialState()
        cropsViewModel.worksViewModel.clearListWork()
    }
}
