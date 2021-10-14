package com.ruru.gasolinaoulcool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.ruru.gasolinaoulcool.databinding.ActivityMainBinding
import java.text.DecimalFormat

class EtanolActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_etanol)
        exibirCampos()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
    }

    fun exibirCampos(){
        val extras: Bundle? = intent.extras
        if (extras != null){
            val txtEconomia = findViewById<TextView>(R.id.textEtanolEconomia)
            val txtPorcetagem = findViewById<TextView>(R.id.textEtanolPorcentagem)
            val economia = extras.getDouble("economia")
            val decimal = DecimalFormat("0.00")
            val economiaFormatada = decimal.format(economia)

            //recuperar litros
            val valorAbastecimento = extras.getDouble("abastecimento")
            val abastecimentoFormatado = decimal.format(valorAbastecimento)

            //recuperar porcentagem
            val porcentagem: Double? = extras.getDouble("porcetagem")
            val porcentagemFormatada = decimal.format(porcentagem)

            txtEconomia.text = "R$$economiaFormatada economizados a cada R$$abastecimentoFormatado abastecidos"
            txtPorcetagem.text = "Economia de $porcentagemFormatada%"
        }
    }

    fun calcularNovamente(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        binding.editValorGasolina.text.clear()
        binding.editValorEtanol.text.clear()
        binding.editValorAbastecimento.text?.clear()
        finish()
    }
}