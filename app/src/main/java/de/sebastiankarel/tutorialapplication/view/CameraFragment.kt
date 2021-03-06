package de.sebastiankarel.tutorialapplication.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.sebastiankarel.tutorialapplication.R
import de.sebastiankarel.tutorialapplication.databinding.FragmentCameraBinding
import de.sebastiankarel.tutorialapplication.util.EventObserver
import de.sebastiankarel.tutorialapplication.viewmodel.CreateUserViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CameraFragment : Fragment() {

    private var cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private val viewModel: CreateUserViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentCameraBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.error.observe(viewLifecycleOwner, EventObserver {
            (activity as MainActivity).showErrorSnackbar(it)
        })

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { switchCamera(view) }
        startCamera(view)
    }

    private fun switchCamera(view: View) {
        cameraSelector = when (cameraSelector) {
            CameraSelector.DEFAULT_BACK_CAMERA -> CameraSelector.DEFAULT_FRONT_CAMERA
            else -> CameraSelector.DEFAULT_BACK_CAMERA
        }
        startCamera(view)
    }

    private fun startCamera(view: View) {
        val previewView = view.findViewById<androidx.camera.view.PreviewView>(R.id.camera_preview)
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also { it.setSurfaceProvider(previewView.surfaceProvider) }

            val imageCapture = ImageCapture.Builder().build()

            view.findViewById<Button>(R.id.take_photo_btn).setOnClickListener {
                imageCapture.takePicture(ContextCompat.getMainExecutor(requireContext()), object : ImageCapture.OnImageCapturedCallback() {

                    override fun onCaptureSuccess(image: ImageProxy) {
                        val bitmap = imageProxyToBitmap(image, image.imageInfo.rotationDegrees)
                        image.close()
                        viewModel.setPhoto(bitmap)
                        findNavController().navigate(R.id.action_cameraFragment_to_createUserFragment)
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Log.e(CameraFragment::class.java.simpleName, "Failed to capture image.", exception)
                    }
                })
            }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(viewLifecycleOwner, cameraSelector, imageCapture, preview)

            } catch(e: Exception) {
                Toast.makeText(requireContext(), "Unable to bind camera to preview.", Toast.LENGTH_SHORT).show()
                Log.e(CameraFragment::class.java.simpleName, "Unable to bind camera to preview.", e)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun imageProxyToBitmap(proxy: ImageProxy, degrees: Int): Bitmap {
        val planeProxy = proxy.planes[0]
        val buffer = planeProxy.buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        return rotateImage(BitmapFactory.decodeByteArray(bytes, 0, bytes.size), degrees)
    }

    private fun rotateImage(image: Bitmap, degrees: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degrees.toFloat())
        val scaled = Bitmap.createScaledBitmap(image, image.width / 4, image.height / 4, true)
        return Bitmap.createBitmap(scaled, 0, 0, scaled.width, scaled.height, matrix, true)
    }
}