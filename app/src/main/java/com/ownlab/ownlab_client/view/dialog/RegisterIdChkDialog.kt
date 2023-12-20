package com.ownlab.ownlab_client.view.dialog

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.ownlab.ownlab_client.databinding.DialogRegisterIdChkBinding
import com.ownlab.ownlab_client.view.RegisterFragmentDirections

class RegisterIdChkDialog : DialogFragment() {
    private var _binding: DialogRegisterIdChkBinding? = null
    private val binding get() = _binding!!

    val args: RegisterIdChkDialogArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogRegisterIdChkBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.statusMessage.text = args.idChkMessage

        binding.closeDialog.setOnClickListener {
            this.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this@RegisterIdChkDialog, 0.9f, 0.55f)
    }

    private fun Context.dialogFragmentResize(dialogFragment: DialogFragment, width: Float, height: Float) {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (Build.VERSION.SDK_INT < 30) {
            val display = windowManager.defaultDisplay
            val size = Point()
            val window = dialogFragment.dialog?.window

            display.getSize(size)

            val x = (size.x * width).toInt()
            val y = (size.y * height).toInt()

            window?.setLayout(x, y)

        } else {
            val rect = windowManager.currentWindowMetrics.bounds
            val window = dialogFragment.dialog?.window

            val x = (rect.width() * width).toInt()
            val y = (rect.height() * height).toInt()

            window?.setLayout(x, y)
        }
    }
}

