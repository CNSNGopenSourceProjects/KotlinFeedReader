package br.com.conseng.kotlinfeedreader

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.pkmmte.pkrss.Article
import com.pkmmte.pkrss.Callback
import com.pkmmte.pkrss.PkRSS
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), Callback {

    lateinit var listView: RecyclerView
    lateinit var adapter: RecyclerView.Adapter<ItemAdapter.ItemViewHolder>

    val listItens = arrayListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Define o gerenciador de leiaute para que o RecyclerView consiga construir o leiaute
        val layout = LinearLayoutManager(this)
        listView = findViewById(R.id.rv) as RecyclerView
        listView.layoutManager = layout

        // Define o adapter a ser utilizado pelo RecyclerView para apresentar os dados
        adapter = ItemAdapter(listItens, this)
        listView.adapter = adapter

        // Obtem os artigos do site do tecmundo
        PkRSS.with(this).load(getString(R.string.rss_feed_url)).callback(this).async()
    }

    /**
     * Assim que termina de carregar o feed, retorna uma lista de artigos.
     * @param [newArticles] Lista de artigos recebida
     */
    override fun onLoaded(newArticles: MutableList<Article>?) {
        listItens.clear()                                       // Purga os dados antigos
        newArticles?.mapTo(listItens) {
            Item(it.title, it.author, it.date, it.source, it.enclosure.url)
        }

        adapter.notifyDataSetChanged()                          // Força a atualização da tela com os novos artigos
    }

    override fun onLoadFailed() {
        // Não será utilizada nessa implementação
    }

    override fun onPreload() {
        // Não será utilizada nessa implementação
    }

    /**
     * Salva as informações básicas de um artigo.
     * @param [titulo] Título do artigo.
     * @param [autor] Nome do autor.
     * @param [data] Data que a RSS foi postada.
     * @param [link] URI onde informações adicionais estão disponíveis.
     * @param [imagem] URL da foto ilustrativa.
     */
    data class Item(val titulo: String, val autor: String, val data: Long, val link: Uri, val imagem: String) {
        /**
         * Disponibiliza a data formatada como String.
         * @param [language] Idioma a ser utilizado na conversão.  Default: "pt" (Português)
         * @param [country] País a ser considerado na conversão. Default: "BR" (Brasil)
         * @param [pattern] Como a data deverá ser formatada. Default: "dd/MMMM/yyyy"
         * @return String com a data no formato
         * @throws NullPointerException It doees not allow null parameter.
         * @throws IllegalArgumentException Invalid parameter.
         * @see [additional_information] https://developer.android.com/reference/java/text/SimpleDateFormat.html
         */
        fun getDataAsString(language: String = "pt", country: String = "BR", pattern: String = "dd/MMM/yyyy"): String {
            return SimpleDateFormat(pattern, Locale(language, country)).format(Date(data))
        }
    }
}
