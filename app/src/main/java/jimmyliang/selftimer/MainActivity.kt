package jimmyliang.selftimer

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import jimmyliang.selftimer.Model.CardModel
import jimmyliang.selftimer.adapter.TimerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*


interface AddTimerListener{
    fun onDialogComplete(cardModel: CardModel){

    }
}

class MainActivity : AppCompatActivity(), AddTimerListener {

    lateinit var timerList: ArrayList<CardModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerList = createTestData()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = TimerAdapter(this, timerList) { cardModel -> itemRowListener(cardModel) }

        fab.setOnClickListener(addNewItemListener())

    }

    private fun createTestData(): ArrayList<CardModel> {
        return mutableListOf(CardModel("Test", 360000)) as ArrayList<CardModel>
    }

    private fun itemRowListener(cardModel: CardModel){

    }

    override fun onDialogComplete(cardModel: CardModel) {
        timerList.add(cardModel)
        recyclerView.adapter!!.notifyItemInserted(timerList.size-1)
    }

    private fun addNewItemListener(): View.OnClickListener {
        return View.OnClickListener {
            val intent = Intent(this,EditTimerActivity::class.java)
            startActivity(intent)
//            showAddDialog(this)
        }
    }

    private fun showAddDialog(addTimerListener: AddTimerListener) {
        //build layout
        var layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val name = EditText(this)
        name.setHint("Name")
        val duration = EditText(this)
        duration.setHint("HH:MM:SS")
        duration.inputType = InputType.TYPE_DATETIME_VARIATION_TIME

        layout.addView(name)
        layout.addView(duration)

        //build dialog
        val builder = AlertDialog.Builder(this)
                .setTitle(R.string.createRowDialog)
                .setView(layout)
                .setPositiveButton("Create") { dialog, which ->
                    val model =  CardModel(name.text.toString(), duration.text.toString().toLong())
                    addTimerListener.onDialogComplete(model)
                }
                .setNegativeButton("Cancel") { dialog, which ->

                }
        builder.create().show()
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


}
