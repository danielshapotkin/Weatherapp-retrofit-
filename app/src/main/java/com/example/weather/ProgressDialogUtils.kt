package com.example.weather


import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager

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
