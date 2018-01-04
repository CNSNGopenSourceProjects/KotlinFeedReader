package br.com.conseng.kotlinfeedreader

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Qin on 04/01/2018.
 */
class ItemAdapter(val list: ArrayList<MainActivity.Item>, val context: Context) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    /**
     * Preserva os dados de um artigo para agilizar no "scroll", enquanto reciclas as views.
     * @param [view] Artigo que será preservado durante a reciclagem.
     */
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo = view.findViewById(R.id.post_titulo) as TextView
        val autor = view.findViewById(R.id.post_autor) as TextView
        val data = view.findViewById(R.id.post_data) as TextView
        val imagem = view.findViewById(R.id.post_imagem) as ImageView
        val bthVerMais = view.findViewById(R.id.post_ver_detalhes) as Button

        /**
         * Dados básicos da classe para ajudar na visualização do debug.
         * @return Dados preservados.
         */
        override fun toString(): String {
            return "titulo:$titulo; autor:$autor; imagem:$imagem"
        }
    }

    /**
     * Called when RecyclerView needs a new [ItemViewHolder] of the given type to represent
     * an item.   Create a new View inflating it from an XML layout file.
     * The new ViewHolder will be used to display items of the adapter using
     * [onBindViewHolder].
     * @param [parent] The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param [viewType] The view type of the new View.
     * @return The new ViewHolder that holds the View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
        val vh = LayoutInflater.from(parent?.context).inflate(R.layout.item_list, parent, false)
        val ivh = ItemViewHolder(vh)
        return ivh
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ItemViewHolder] to reflect the item at the given position.
     * @param [holder] The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param [position] The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ItemViewHolder?, position: Int) {
        holder?.titulo?.text = list[position].titulo
        holder?.autor?.text = list[position].autor
        holder?.data?.text = list[position].getDataAsString()       // Utiliza os parâmetros default para formatar a data
// TODO: carregar a imagem -->       holder?.imagem?.setImageURI() = list[position].titulo
        holder?.bthVerMais?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, list[position].link)
            context.startActivity(intent)
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int = list.size

    /**
     * Dados básicos da classe para ajudar na visualização do debug.
     * @return Número de artigos disponíveis na lista.
     */
    override fun toString(): String {
        return "ItemAdapter: list com ${list.size} artigos"
    }
}