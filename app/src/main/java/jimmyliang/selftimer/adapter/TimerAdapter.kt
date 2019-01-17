package jimmyliang.selftimer.adapter

import android.app.AlertDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import jimmyliang.selftimer.Model.CardModel
import jimmyliang.selftimer.R
import jimmyliang.selftimer.R.id.edit
import kotlinx.android.synthetic.main.item_row.view.*

class TimerAdapter(val context: Context, val timerCards: MutableList<CardModel>, val clickListener: (CardModel) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_row, p0, false)
        return TimerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return timerCards.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        (p0 as TimerViewHolder).bind(timerCards[p1], clickListener)
    }

    fun addNewTimer(cardModel: CardModel){
        timerCards.add(cardModel)
        notifyItemInserted(timerCards.size-1)
    }


    inner class TimerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var del = false

        private fun createNewModelDialog() {

        }

        private fun showDeleteRowDialog() {
            val builder = AlertDialog.Builder(context)
                    .setTitle(R.string.deleteRowDialog)
                    .setMessage(R.string.deleteRowMsg)
                    .setPositiveButton("Yes") { dialog, which ->
                        layoutPosition.also { currentPosition ->
                            timerCards.removeAt(currentPosition)
                            notifyItemRemoved(currentPosition)
                        }
                    }
                    .setNegativeButton("Cancel") { dialog, which ->
                        Toast.makeText(itemView.context, "Didn't delete at $layoutPosition", Toast.LENGTH_SHORT).show()
                    }
            builder.create().show()
        }


        override fun onClick(v: View?) {
            when (v?.id) {
                editButton.id -> Toast.makeText(itemView.context, "Editing at $adapterPosition", Toast.LENGTH_SHORT).show()
            }
        }

        private fun remove(): (View) -> Unit = {
            showDeleteRowDialog()
        }

        val editButton: ImageButton = itemView.findViewById(R.id.edit)
        val deleteButton: ImageButton = itemView.delete

        fun bind(cardModel: CardModel, clickListener: (CardModel) -> Unit) {
            editButton.setOnClickListener(this)
            deleteButton.setOnClickListener(remove())
            itemView.setOnClickListener { clickListener(cardModel) }
            itemView.timerName.text = cardModel.name
            itemView.timeRemaining.text = cardModel.longTimeToString(cardModel.startTime)


        }

    }

}