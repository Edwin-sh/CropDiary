package com.example.cropdiary.ui.viewmodel.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeCropsFragmentViewModel @Inject constructor(private val state: SavedStateHandle) :
    ViewModel() {
    val btnAdd: MutableLiveData<Boolean> = state.getLiveData("btnAdd", true)
    val newCropForm: MutableLiveData<Boolean> = state.getLiveData("newCropForm", false)
    val checkBoxDescriptionCrop: MutableLiveData<Boolean> = state.getLiveData("checkBoxDescriptionCrop", false)
    //val editTextDescriptionCrop: MutableLiveData<Boolean> = state.getLiveData("editTextDescriptionCrop", false)
    val switch: MutableLiveData<Boolean> = state.getLiveData("switch", false)
    val listWorksContainer: MutableLiveData<Boolean> = state.getLiveData("listWorksContainer", false)
    //val addWorksContainer: MutableLiveData<Boolean> = state.getLiveData("addWorksContainer", false)
    val cropName: MutableLiveData<String?> = state.getLiveData("cropName", null)
    val workName: MutableLiveData<String?> = state.getLiveData("workName", null)

    fun showBtnAddCrop() {
        btnAdd.value = true
    }

    fun hideBtnAddCrop() {
        btnAdd.value = false
    }

    fun showNewCropForm() {
        newCropForm.value = true
        //addWorksContainer.value = false
    }

    fun hideNewCropForm() {
        newCropForm.value = false
    }

    fun setCheckBoxCropStateOn() {
        checkBoxDescriptionCrop.value = true
    }

    fun setCheckBoxCropStateOff() {
        checkBoxDescriptionCrop.value = false
    }

    /*fun showEditTextDescriptionCrop() {
        editTextDescriptionCrop.value = true
    }

    fun hideEditTextDescriptionCrop() {
        editTextDescriptionCrop.value = false
    }*/

    fun setSwitchStateOn() {
        switch.value = true
    }

    fun setSwitchStateOff() {
        switch.value = false
    }

    fun showListWorks(){
        listWorksContainer.value=true
    }

    fun hideListWorks(){
        listWorksContainer.value=false
    }

    /*fun showAddWorksContainer() {
        addWorksContainer.value = true
    }

    fun hideAddWorksContainer() {
        addWorksContainer.value = false
    }*/

    fun saveCropName(cropName: String) {
        this.cropName.value = cropName
    }

    fun clearCropName() {
        cropName.value = null
    }

    fun saveWorkName(workName: String) {
        this.workName.value = workName
    }

    fun clearWorkName() {
        workName.value = null
    }

    fun restoreInitialState() {
        showBtnAddCrop()
        hideNewCropForm()
        setSwitchStateOff()
        //hideAddWorksContainer()
    }
}