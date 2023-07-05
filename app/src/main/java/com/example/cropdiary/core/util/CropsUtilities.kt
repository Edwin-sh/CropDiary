package com.example.cropdiary.core.util

import android.app.Activity
import android.widget.CheckBox
import android.widget.EditText
import com.example.cropdiary.core.view.Dialogs
import com.example.cropdiary.data.model.CropInfoModel
import com.example.cropdiary.data.model.CropModel
import com.example.cropdiary.data.model.WorkModel

object CropsUtilities {
    fun validateCrop(
        nameCrop: EditText,
        activity: Activity,
        worksList: List<WorkModel>,
        checkBox: CheckBox,
        description: EditText,
        callback: (CropModel?) -> Unit
    ) {
        if (!Utilities.noEmpty(Pair(nameCrop, "You must enter the crop name"), activity)) {
            callback(null)
        }
        if (!Utilities.isAlpha(
                Pair(nameCrop, "You must enter a valid crop name"),
                activity
            )
        ) {
            callback(null)
        }
        if (checkBox.isChecked) {
            if (!Utilities.noEmpty(
                    Pair(description, "You must enter the crop description"),
                    activity
                )
            ) {
                callback(null)
            } else {
                callback(
                    CropModel(
                        CropInfoModel(nameCrop.text.toString(), description.text.toString()),
                        worksList
                    )
                )
            }
        } else {
            callback(CropModel(CropInfoModel(nameCrop.text.toString()), worksList))
        }
    }

    fun validateWork(
        nameWork: EditText,
        activity: Activity,
        worksList: List<WorkModel>,
        callback: (WorkModel?) -> Unit
    ) {

        if (!Utilities.noEmpty(Pair(nameWork, "You must enter the work name"), activity)) {
            callback(null)
        }
        if (!Utilities.isAlpha(
                Pair(nameWork, "You must enter a valid work name"),
                activity
            )
        ) {
            callback(null)
        }
        for (value in worksList) {
            if (value.name.equals(nameWork.text.toString(), true)) {
                Dialogs.showErrorAlert(
                    activity,
                    "There is already a work with this name in this crop"
                )
                callback(null)
            }
        }
        callback(WorkModel(nameWork.text.toString(), "Description"))
    }

}