package com.ruru.gasolinaoulcool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.ruru.gasolinaoulcool.databinding.ActivityMainBinding
import com.ruru.gasolinaoulcool.databinding.ActivityMainBinding.inflate
import java.text.DecimalFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)

        val locale = Locale("pt", "br")
        binding.editValorEtanol.locale = locale
        binding.editValorGasolina.locale = locale
        binding.editValorAbastecimento.locale = locale
    }

    fun calcularPreco(view: View) {
        val precoEtanol = binding.editValorEtanol.rawValue.toDouble()
        val precoGasolina = binding.editValorGasolina.rawValue.toDouble()
        val valorAbastecimento = binding.editValorAbastecimento.rawValue.toDouble()

        val validaCampos = validarCampos(precoEtanol, precoGasolina, valorAbastecimento)
        if (validaCampos) {
            calcularMelhorPreco(precoEtanol, precoGasolina, valorAbastecimento)
        } else {
            Toast.makeText(this, R.string.txt_preencher, Toast.LENGTH_SHORT).show()
        }
    }

   private fun validarCampos(
        precoEtanol: Double,
        precoGasolina: Double,
        valorAbastecimento: Double
    ): Boolean {
        var camposValidados = true
        if (precoEtanol.toString() == "0" || precoEtanol.toString() == "") {
            camposValidados = false
        } else if (precoGasolina.toString() == "0" || precoGasolina.toString() == "") {
            camposValidados = false
        } else if (valorAbastecimento.toString() == "0" || valorAbastecimento.toString() == "") {
            camposValidados = false
        }
        return camposValidados
    }

    private fun calcularMelhorPreco(
        precoEtanol: Double,
        precoGasolina: Double,
        valorAbastecimento: Double
    ) {

        val resultadoPreco = precoEtanol / precoGasolina
        val abastecimentoFormatado = valorAbastecimento / 100

        if (resultadoPreco >= 0.7) {
            val resultadoPorcentagem = ((1 - resultadoPreco) * 100)
            val resultadoEconomia =
                ((abastecimentoFormatado / precoGasolina) - (abastecimentoFormatado / precoEtanol) * 0.7) * precoGasolina

            abrirTelaGasolina(resultadoEconomia, resultadoPorcentagem, abastecimentoFormatado)

        } else{
            val resultadoPorcentagem = (1-(abastecimentoFormatado / precoGasolina)/(abastecimentoFormatado / precoEtanol*0.7))*100
            val resultadoEconomia = (resultadoPorcentagem*abastecimentoFormatado)/100
            abrirTelaEtanol(resultadoEconomia, resultadoPorcentagem, abastecimentoFormatado)
        }

    }

    private fun abrirTelaEtanol(resultadoEconomia: Double, resultadoPorcentagem: Double, valorAbastecimento: Double) {
        val intent = Intent(this, EtanolActivity::class.java)
        intent.putExtra("economia", resultadoEconomia)
        intent.putExtra("porcetagem", resultadoPorcentagem)
        intent.putExtra("abastecimento", valorAbastecimento)
        startActivity(intent)
    }


    private fun abrirTelaGasolina(resultadoEconomia: Double, resultadoPorcentagem: Double, valorAbastecimento: Double){
        val intent = Intent(this, GasolinaActivity::class.java)
        intent.putExtra("economia", resultadoEconomia)
        intent.putExtra("porcetagem", resultadoPorcentagem)
        intent.putExtra("abastecimento", valorAbastecimento)
        startActivity(intent)
    }
}
