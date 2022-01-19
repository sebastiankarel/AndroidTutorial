package de.sebastiankarel.tutorialapplication.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.sebastiankarel.tutorialapplication.R
import java.io.File

class CameraFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startCamera(view)
    }

    private fun startCamera(view: View) {
        val previewView = view.findViewById<androidx.camera.view.PreviewView>(R.id.camera_preview)
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also { it.setSurfaceProvider(previewView.surfaceProvider) }
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val imageCapture = ImageCapture.Builder()
                .setTargetRotation(view.display.rotation)
                .build()

            val fab = view.findViewById<FloatingActionButton>(R.id.fab)
            fab.setOnClickListener {
                val outputFile = getOutputFile()
                val outputFileOptions = ImageCapture.OutputFileOptions.Builder(outputFile).build()
                val callback = object : ImageCapture.OnImageSavedCallback {
                    override fun onError(error: ImageCaptureException) {
                        Toast.makeText(requireContext(), "Unable to take photo.", Toast.LENGTH_SHORT).show()
                        Log.e(CameraFragment::class.java.simpleName, "Unable to take photo.", error)
                    }
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        findNavController().navigate(
                            CameraFragmentDirections.actionCameraFragmentToCreateUserFragment(outputFile.absolutePath)
                        )
                    }
                }
                imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(requireContext()), callback)
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

    private fun getOutputFile(): File {
        val path = requireActivity().externalMediaDirs.first()
        return File(path, "photo.jpg")
    }
}