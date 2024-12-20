package com.example.majika.twibbon

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.majika.databinding.FragmentTwibbonBinding
import com.google.common.util.concurrent.ListenableFuture

class TwibbonFragment : Fragment() {


    private lateinit var binding: FragmentTwibbonBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var viewModel: TwibbonViewModel
    private var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>? = null
    private var cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTwibbonBinding.inflate(
            inflater,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(TwibbonViewModel::class.java)

        if (viewModel.bitMap.value != null) {
            binding.cameraPreview.visibility = View.GONE
            binding.pictureImage.visibility = View.VISIBLE
            binding.pictureImage.setImageBitmap(viewModel.bitMap.value)
            binding.takePictBtn.visibility = View.GONE
            binding.retakePictBtn.visibility = View.VISIBLE
        }

        cameraProviderFuture = ProcessCameraProvider
            .getInstance(requireContext())
        if (allPermissionsGranted()) {
            Toast.makeText(requireContext(), "We Have Permissions", Toast.LENGTH_SHORT).show()
            startCamera(cameraSelector)
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                Constants.REQUIRED_PERMISSIONS,
                Constants.REQUEST_CODE_PERMISSIONS
            )
        }

        binding.changeCamera.setOnCheckedChangeListener({_, isChecked ->
            cameraSelector = if (isChecked) {
                CameraSelector.DEFAULT_FRONT_CAMERA
            } else {
                CameraSelector.DEFAULT_BACK_CAMERA
            }
            startCamera(cameraSelector)
        })

        binding.takePictBtn.setOnClickListener({
            takePicture()
        })

        binding.retakePictBtn.setOnClickListener({
            reTakePicute()
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun takePicture() {
        binding.cameraPreview.visibility = View.GONE
        binding.pictureImage.visibility = View.VISIBLE
        val bitmap = binding.cameraPreview.bitmap
        binding.pictureImage.setImageBitmap(bitmap)
        viewModel.setData(bitmap!!)
        Toast.makeText(requireContext(), "Picture Captured", Toast.LENGTH_LONG).show()
        binding.takePictBtn.visibility = View.GONE
        binding.retakePictBtn.visibility = View.VISIBLE
        binding.changeCamera.visibility = View.GONE
    }

    private fun reTakePicute() {
        binding.cameraPreview.visibility = View.VISIBLE
        binding.pictureImage.visibility = View.GONE
        binding.pictureImage.setImageBitmap(null)
        Toast.makeText(requireContext(), "Retake Picture", Toast.LENGTH_LONG).show()
        binding.takePictBtn.visibility = View.VISIBLE
        binding.retakePictBtn.visibility = View.GONE
        binding.changeCamera.visibility = View.VISIBLE
    }

    private fun startCamera(cameraSelector : CameraSelector) {
        cameraProviderFuture?.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture!!.get()

            val preview = Preview.Builder()
                .build()
                .also { mPreview ->
                    mPreview.setSurfaceProvider(
                        binding.cameraPreview.surfaceProvider
                    )
                }

            imageCapture = ImageCapture.Builder()
                .build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
            } catch (e: Exception) {
                Log.d(Constants.TAG, "startCamera Fail")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun allPermissionsGranted() =
        Constants.REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }

    override fun onDestroy() {
        super.onDestroy()
        cameraProviderFuture?.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture!!.get()
            cameraProvider.unbindAll()
        }, ContextCompat.getMainExecutor(requireContext()))
    }
}