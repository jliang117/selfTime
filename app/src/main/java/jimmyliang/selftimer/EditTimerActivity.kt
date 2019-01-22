package jimmyliang.selftimer

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatImageButton
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView


/**
 * EditTimerActivity is used during creation edit.
 * User must be able to edit existing label and create a new one using the grid numpad
 */
class EditTimerActivity : AppCompatActivity(), View.OnClickListener, View.OnLongClickListener {


    val LABEL_KEY = "label_key"
    val FIELD_LENGTH = 2

    lateinit var fieldsLayout: ViewGroup
    lateinit var label: TextView
    lateinit var hour: EditText
    lateinit var hourLabel: TextView
    lateinit var minute: EditText
    lateinit var minuteLabel: TextView
    lateinit var second: EditText
    lateinit var secondLabel: TextView
    lateinit var focucGrab: View

    lateinit var one: AppCompatButton
    lateinit var two: AppCompatButton
    lateinit var three: AppCompatButton
    lateinit var four: AppCompatButton
    lateinit var five: AppCompatButton
    lateinit var six: AppCompatButton
    lateinit var seven: AppCompatButton
    lateinit var eight: AppCompatButton
    lateinit var nine: AppCompatButton
    lateinit var zero: AppCompatButton
    lateinit var backSpace: AppCompatImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_timer)
        if (savedInstanceState != null) {
            label.setText(savedInstanceState.getCharSequence(LABEL_KEY))
        }
        initLabelView()
        initGrid()
    }


    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putCharSequence(LABEL_KEY, label.getText())

    }

    /*
    Listeners
     */

    override fun onLongClick(v: View?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.backspace -> onClickBack()
            else -> onClickNumber(v)
        }
    }

    /*
    private helpers
     */

    private fun initGrid() {
        one = findViewById(R.id.one)
        one.setOnClickListener(this)
        two = findViewById(R.id.two)
        two.setOnClickListener(this)
        three = findViewById(R.id.three)
        three.setOnClickListener(this)
        four = findViewById(R.id.four)
        four.setOnClickListener(this)
        five = findViewById(R.id.five)
        five.setOnClickListener(this)
        six = findViewById(R.id.six)
        six.setOnClickListener(this)
        seven = findViewById(R.id.seven)
        seven.setOnClickListener(this)
        eight = findViewById(R.id.eight)
        eight.setOnClickListener(this)
        nine = findViewById(R.id.nine)
        nine.setOnClickListener(this)
        zero = findViewById(R.id.zero)
        zero.setOnClickListener(this)

        backSpace = findViewById(R.id.backspace)
        backSpace.setOnClickListener(this)
    }

    private fun initLabelView() {
        fieldsLayout = findViewById(R.id.edit_fields_layout)
        label = findViewById(R.id.label)
        hour = findViewById(R.id.hour)
        hourLabel = findViewById(R.id.hour_label)
        minute = findViewById(R.id.minute)
        minuteLabel = findViewById(R.id.minute_label)
        second = findViewById(R.id.second)
        secondLabel = findViewById(R.id.second_label)
        focucGrab = findViewById(R.id.focus_grabber)

        hour.setOnTouchListener { v, event -> onTouchListener(v as EditText, event) }
        minute.setOnTouchListener { v, event -> onTouchListener(v as EditText, event) }
        second.setOnTouchListener { v, event -> onTouchListener(v as EditText, event) }

    }


    private fun getChronoLabelFocus(): EditText {
        return fieldsLayout.findFocus() as EditText
    }

    private fun onClickNumber(v: View?) {
        val txtView = v as TextView
        if (focucGrab.isFocused) {
            return
        }
        if(!focucGrab.isFocused){
            hour.requestFocus()
        }
        val currFocus: EditText = getChronoLabelFocus()
        val atPos = currFocus.selectionStart

        currFocus.text.replace(atPos, atPos + 1, txtView.text)
        currFocus.setSelection(atPos + 1)
        if (currFocus.selectionStart == FIELD_LENGTH) { //check if we're at the end
            //focus on next field
            val next = currFocus.focusSearch(View.FOCUS_RIGHT)
            if (next != null) {
                next.requestFocus()
                if (next is EditText) {
                    next.setSelection(0)
                }
            }

        }
    }

    private fun onClickBack() {
        if (focucGrab.isFocused) {
            fieldsLayout.focusSearch(focucGrab, View.FOCUS_LEFT).requestFocus()
        }
        var field: EditText = getChronoLabelFocus()
        val atPos = field.selectionStart
        if (atPos == 0) {
            val prev = field.focusSearch(View.FOCUS_LEFT)
            if (prev.requestFocus()) {
                if (prev is EditText) {
                    prev.setSelection(FIELD_LENGTH)
                }
                onClickBack()
            }
        } else {
            field.text.replace(atPos - 1, atPos, "0")
            field.setSelection(atPos - 1)
        }
    }

    private fun onTouchListener(field: EditText, event: MotionEvent): Boolean {
        val type = field.inputType
        field.inputType = InputType.TYPE_NULL
        val touchResult = field.onTouchEvent(event)
        field.inputType = type
        return touchResult
    }


}