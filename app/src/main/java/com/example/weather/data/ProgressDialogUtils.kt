package com.example.weather.data


import android.app.ProgressDialog
import android.content.Context

class PogressDialogUtils(private val context: Context) {
    private val progressDialog = ProgressDialog(context).apply{
        setMessage("Получение данных...")
        setCancelable(false)
    }

    fun showProgressDialog() {
        progressDialog.show()
    }
    fun dismissProgressDialog(){
        progressDialog.dismiss()
    }
}
