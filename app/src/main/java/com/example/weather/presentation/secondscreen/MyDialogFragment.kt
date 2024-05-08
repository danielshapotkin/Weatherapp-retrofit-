package com.example.weather.presentation.secondscreen

import ImageDownloader
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.example.weather.R
import com.example.weather.data.ProgressDialogUtils
import com.example.weather.domain.ImageListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class MyDialogFragment : DialogFragment() {
    private val  imageDownloader = ImageDownloader(requireContext())
    private lateinit var btnCancel: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnSelect: Button
    private lateinit var imageView: ImageView
    private lateinit var imageListener: ImageListener

    fun setImageListener(imageListener: ImageListener) {
        this.imageListener = imageListener
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_layout, container, false)

        btnCancel = view.findViewById(R.id.cancelButton)
        btnUpdate = view.findViewById(R.id.updateButton)
        btnSelect = view.findViewById(R.id.selectButton)
        imageView = view.findViewById(R.id.imageView)

        btnCancel.setOnClickListener {
            dismiss()
        }

        btnUpdate.setOnClickListener {
            val progressDialog = ProgressDialogUtils(requireContext())
            progressDialog.showProgressDialog()
            CoroutineScope(MainScope().coroutineContext).launch {
                imageView.setImageBitmap(imageDownloader.downloadImage("https://picsum.photos/200"))
            }
            progressDialog.dismissProgressDialog()
        }

        btnSelect.setOnClickListener {
            if (imageView.drawable!=null)
            {
                imageListener.onImageSelected(imageView.drawable)
                dismiss()
            }
        }

        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setCancelable(false)
        }
    }
}



