package com.example.draftpad.ui.premium

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.draftpad.databinding.FragmentPremiumBinding
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult

class PremiumFragment : Fragment() {

    val publishableKey =
        "pk_test_51MqsCESCz8rZMjh8kM2ElCXyKd8L4HIE3zhVAASQELg8ZLwVYpserzPdxOt5YHAy4FBp33TBH5rMgGKX42m0mcQE004Buf8yVW"
    val secretKey =
        "sk_test_51MqsCESCz8rZMjh8Kwew9NTEiBpxHqEQ9xaqITnSYty7NIsPT831jc46picJ7vjMrqjD0cjy9IPqJikqOVi6i46e00Ko8jRijT"
    val testSCard = "4242424242424242"
    val testFCard = "4000000000009995"
    val ip = "192.168.18.237:5000"
    lateinit var paymentSheet: PaymentSheet
    lateinit var customerConfig: PaymentSheet.CustomerConfiguration
    var paymentIntentClientSecret = publishableKey
    private var _binding: FragmentPremiumBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)
        "http://$ip/api/v1/create-checkout-session".httpPost().responseJson { _, _, result ->
            if (result is Result.Success) {
                val responseJson = result.get().obj()
                paymentIntentClientSecret = responseJson.getString("paymentIntent")
                customerConfig = PaymentSheet.CustomerConfiguration(
                    responseJson.getString("customer"),
                    responseJson.getString("ephemeralKey")
                )
                val publishableKey = responseJson.getString("publishableKey")
                PaymentConfiguration.init(requireContext(), publishableKey)
            } else {
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("Error in creating payment session")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }.show()
            }
        }
    }

    fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        paymentSheet.presentWithPaymentIntent(
            paymentIntentClientSecret,
            PaymentSheet.Configuration(
                merchantDisplayName = "Draftpad",
                customer = customerConfig,
                allowsDelayedPaymentMethods = true
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPremiumBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnGoPremium.setOnClickListener {
                if (btnPremiumOneMonth.isChecked) {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://buy.stripe.com/test_aEU17p6H4f5Gfx63cc")
                    )
                    requireContext().startActivity(intent)
                } else if (btnPremiumSixMonth.isChecked) {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://buy.stripe.com/test_eVaeYf8Pcf5G70A9AB")
                    )
                    requireContext().startActivity(intent)
                } else if (btnPremiumOneYear.isChecked) {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://buy.stripe.com/test_bIY9DV3uSf5G84EaEG")
                    )
                    requireContext().startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "Please select a plan", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

    }

    companion object {

    }
}