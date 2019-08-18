package org.rohk.humanityinbusiness.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import org.rohk.humanityinbusiness.http.ServiceAPI
import org.rohk.humanityinbusiness.http.model.RegisterResponseModel
import org.rohk.humanityinbusiness.http.model.RequestRegisterModel
import org.rohk.humanityinbusiness.ui.viewmodel.CompanyModel
import org.rohk.humanityinbusiness.utils.PreferenceUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    var spinnerArray = ArrayList<String>()
    var companies: List<CompanyModel> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(org.rohk.humanityinbusiness.R.layout.activity_register)
        getAllCompanies()
        btnRegister.setOnClickListener {
            register()
        }
    }

    private fun getAllCompanies() {
        ServiceAPI().getAllCompanies(object : Callback<List<CompanyModel>> {
            override fun onResponse(call: Call<List<CompanyModel>>, response: Response<List<CompanyModel>>) {

                companies = response.body() ?: listOf()
                companies.forEach { company ->
                    company.name?.let { companyName -> spinnerArray.add(companyName) }
                }

                val adapter = ArrayAdapter(
                    this@RegisterActivity, android.R.layout.simple_spinner_item, spinnerArray
                )

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                companySpinner.adapter = adapter

                response.body()
            }

            override fun onFailure(call: Call<List<CompanyModel>>, t: Throwable) {
                Toast.makeText(applicationContext, "Oops, could not fetch companies!", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun register() {
        if(!validateFields()) {
            val request = RequestRegisterModel(
                etFullname.text.toString(),
                etPassword.text.toString(),
                etEmail.text.toString(),
                companies[companySpinner.selectedItemPosition].id,
                etTitle.text.toString()
            )

            ServiceAPI().register(
                request,
                object : Callback<RegisterResponseModel> {
                    override fun onResponse(
                        call: Call<RegisterResponseModel>,
                        response: Response<RegisterResponseModel>
                    ) {
                        response.body()?.id?.let {
                            PreferenceUtils().setUserId(this@RegisterActivity, it)
                            PreferenceUtils().setLoginEmail(this@RegisterActivity, etEmail.text.toString())
                            startActivity(Intent(applicationContext, GoalsSelectionActivity::class.java))
                            finish()
                        } ?: Toast.makeText(applicationContext, "Oops, registration failed!", Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<RegisterResponseModel>, t: Throwable) {
                        Toast.makeText(applicationContext, "Oops, registration failed!", Toast.LENGTH_LONG).show()
                    }
                })
        } else {
            Toast.makeText(applicationContext, "Please enter all details!", Toast.LENGTH_LONG).show()
        }
    }

    private fun validateFields(): Boolean =
        (etFullname.text.isNullOrBlank() ||
                etPassword.text.isNullOrBlank() ||
                etEmail.text.isNullOrBlank() ||
                etTitle.text.isNullOrBlank())

}
