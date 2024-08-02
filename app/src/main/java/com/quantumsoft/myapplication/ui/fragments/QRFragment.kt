package com.quantumsoft.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.quantumsoft.myapplication.R
import com.quantumsoft.myapplication.databinding.FragmentQrBinding
import com.quantumsoft.myapplication.viewmodel.MuseoViewModel
import com.quantumsoft.myapplication.viewmodel.MuseoViewModelFactory
import com.quantumsoft.myapplication.data.repository.ExposicionRepository
import com.quantumsoft.myapplication.viewmodel.FragmentChanger
import java.util.concurrent.Executors

class QRFragment : Fragment() {

    private lateinit var binding: FragmentQrBinding
    private val cameraExecutor = Executors.newSingleThreadExecutor()

    private lateinit var museoViewModel: MuseoViewModel
    private var fragmentChanger: FragmentChanger? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = ExposicionRepository(requireContext()) // Ajusta esto según tu implementación de repositorio
        val viewModelFactory = MuseoViewModelFactory(repository)
        museoViewModel = ViewModelProvider(this, viewModelFactory).get(MuseoViewModel::class.java)

        fragmentChanger = activity as? FragmentChanger // Obtén la referencia al FragmentChanger desde la actividad
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQrBinding.inflate(inflater, container, false)
        startCamera()
        return binding.root
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = androidx.camera.core.Preview.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val imageAnalysis = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, QRCodeAnalyzer { qrCodes ->
                        qrCodes.forEach { barcode ->
                            handleQRCode(barcode.rawValue ?: "")
                        }
                    })
                }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalysis
                )
                preview.setSurfaceProvider(binding.previewView.surfaceProvider)
            } catch (exc: Exception) {
                // Handle exceptions
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun handleQRCode(qrCode: String) {
        val id = qrCode.toLongOrNull() // Ajusta esto según el formato del QR
        id?.let {
            museoViewModel.setPinturaActual(it)
            // Navegar al fragmento de detalles del cuadro
            val fragment = CuadroDetalleFragment.newInstance("", "")
            fragmentChanger?.changeFragment(fragment) // Usa fragmentChanger si no es nulo
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        @JvmStatic
        fun newInstance() = QRFragment()
    }
}

class QRCodeAnalyzer(private val onQRCodeDetected: (qrCodes: List<Barcode>) -> Unit) : ImageAnalysis.Analyzer {
    private val scanner = BarcodeScanning.getClient()

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    onQRCodeDetected(barcodes)
                }
                .addOnFailureListener {
                    // Handle failure
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }
}
