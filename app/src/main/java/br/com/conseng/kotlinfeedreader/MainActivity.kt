package br.com.conseng.kotlinfeedreader

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pkmmte.pkrss.Article
import com.pkmmte.pkrss.Callback
import com.pkmmte.pkrss.PkRSS

class MainActivity : AppCompatActivity(), Callback {

    val listItens = arrayListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PkRSS.with(this).load("https://rss.tecmundo.com.br/feed").callback(this).async()
    }

    override fun onLoadFailed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPreload() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Assim que termina de carregar o feed, retorna uma lista de artigos.
     * @param [newArticles] Lista de artigos recebida
     */
    override fun onLoaded(newArticles: MutableList<Article>?) {
        newArticles?.mapTo(listItens) {
            Item(it.title, it.author, it.date, it.source, it.enclosure.url)
        }
    }

    /**
     * Salva as informações básicas de um artigo.
     * @param [titulo] Título do artigo.
     * @param [autor] Nome do autor.
     * @param [data] Data que a RSS foi postada.
     * @param [link] URI onde informações adicionais estão disponíveis.
     * @param [imagem] URL da foto ilustrativa.
     */
    data class Item(val titulo: String, val autor: String, val data: Long, val link: Uri, val imagem: String)
}
