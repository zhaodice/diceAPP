package org.mirai.zhao.dice.component

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView

/*
 * Reference Source: https://github.com/M66B/FairEmail/blob/master/app/src/main/java/eu/faircode/email/FixedTextView.java
 */

class FixedTextView : AppCompatTextView {
    object Log{
        fun w(e:Throwable){
            android.util.Log.w("FixedTextView",e)
        }
        fun e(e:Throwable){
            android.util.Log.e("FixedTextView",e.message,e)
        }
    }
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        try {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        } catch (ex: Throwable) {
            Log.w(ex)
            /*
        java.lang.ArrayIndexOutOfBoundsException: length=...; index=...
                at android.text.TextLine.measure(TextLine.java:316)
                at android.text.TextLine.metrics(TextLine.java:271)
                at android.text.Layout.measurePara(Layout.java:2056)
                at android.text.Layout.getDesiredWidth(Layout.java:164)
                at android.widget.TextView.onMeasure(TextView.java:8291)
                at androidx.appcompat.widget.AppCompatTextView.onMeasure(SourceFile:554)
                at android.view.View.measure(View.java:22360)
            */
            setMeasuredDimension(0, 0)
        }
    }

    override fun onPreDraw(): Boolean {
        return try {
            super.onPreDraw()
        } catch (ex: Throwable) {
            Log.w(ex)
            /*
                java.lang.ArrayIndexOutOfBoundsException: length=54; index=54
                at android.text.TextLine.measure(TextLine.java:316)
                at android.text.TextLine.metrics(TextLine.java:271)
                at android.text.Layout.getLineExtent(Layout.java:1374)
                at android.text.Layout.getLineStartPos(Layout.java:700)
                at android.text.Layout.getHorizontal(Layout.java:1175)
                at android.text.Layout.getHorizontal(Layout.java:1144)
                at android.text.Layout.getPrimaryHorizontal(Layout.java:1115)
                at android.widget.TextView.bringPointIntoView(TextView.java:8944)
                at android.widget.TextView.onPreDraw(TextView.java:6475)
            */
            true
        }
    }

    override fun onDraw(canvas: Canvas) {
        try {
            super.onDraw(canvas)
        } catch (ex: Throwable) {
            Log.w(ex)
            /*
        java.lang.ArrayIndexOutOfBoundsException: length=74; index=74
                at android.text.TextLine.draw(TextLine.java:241)
                at android.text.Layout.drawText(Layout.java:545)
                at android.text.Layout.draw(Layout.java:289)
                at android.widget.TextView.onDraw(TextView.java:6972)
                at android.view.View.draw(View.java:19380)
            */
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        // https://issuetracker.google.com/issues/37068143
        if (event.actionMasked == MotionEvent.ACTION_DOWN && Build.VERSION.RELEASE == "6.0" && hasSelection()) {
            // Remove selection
            val text = text
            setText(null)
            setText(text)
        }
        return try {
            super.dispatchTouchEvent(event)
        } catch (ex: Throwable) {
            /*
                    Attempt to fix
                    java.lang.IndexOutOfBoundsException: setSpan (-1 ... -1) starts before 0
                      at android.text.SpannableStringInternal.checkRange(SpannableStringInternal.java:434)
                      at android.text.SpannableStringInternal.setSpan(SpannableStringInternal.java:155)
                      at android.text.SpannableString.setSpan(SpannableString.java:46)
                      at android.text.Selection.setSelection(Selection.java:76)
                      at android.widget.Editor$SelectionHandleView.updateSelection(Editor.java:4687)
                      at android.widget.Editor$HandleView.positionAtCursorOffset(Editor.java:4262)
                      at android.widget.Editor$SelectionHandleView.positionAtCursorOffset(Editor.java:4870)
                      at android.widget.Editor$SelectionHandleView.positionAndAdjustForCrossingHandles(Editor.java:4918)
                      at android.widget.Editor$SelectionHandleView.updatePosition(Editor.java:4863)
                      at android.widget.Editor$HandleView.onTouchEvent(Editor.java:4407)
                      at android.widget.Editor$SelectionHandleView.onTouchEvent(Editor.java:4876)
                      at android.view.View.dispatchTouchEvent(View.java:10024)
                      at android.view.ViewGroup.dispatchTransformedTouchEvent(ViewGroup.java:2632)
                      at android.view.ViewGroup.dispatchTouchEvent(ViewGroup.java:2321)
                      at android.widget.PopupWindow$PopupDecorView.dispatchTouchEvent(PopupWindow.java:2277)
                      at android.view.View.dispatchPointerEvent(View.java:10244)
                      at android.view.ViewRootImpl$ViewPostImeInputStage.processPointerEvent(ViewRootImpl.java:4468)
                      at android.view.ViewRootImpl$ViewPostImeInputStage.onProcess(ViewRootImpl.java:4336)
                      at android.view.ViewRootImpl$InputStage.deliver(ViewRootImpl.java:3883)
                      at android.view.ViewRootImpl$InputStage.onDeliverToNext(ViewRootImpl.java:3936)
                      at android.view.ViewRootImpl$InputStage.forward(ViewRootImpl.java:3902)
                      at android.view.ViewRootImpl$AsyncInputStage.forward(ViewRootImpl.java:4029)
                      at android.view.ViewRootImpl$InputStage.apply(ViewRootImpl.java:3910)
                      at android.view.ViewRootImpl$AsyncInputStage.apply(ViewRootImpl.java:4086)
                      at android.view.ViewRootImpl$InputStage.deliver(ViewRootImpl.java:3883)
                      at android.view.ViewRootImpl$InputStage.onDeliverToNext(ViewRootImpl.java:3936)
                      at android.view.ViewRootImpl$InputStage.forward(ViewRootImpl.java:3902)
                      at android.view.ViewRootImpl$InputStage.apply(ViewRootImpl.java:3910)
                      at android.view.ViewRootImpl$InputStage.deliver(ViewRootImpl.java:3883)
                      at android.view.ViewRootImpl.deliverInputEvent(ViewRootImpl.java:6284)
                      at android.view.ViewRootImpl.doProcessInputEvents(ViewRootImpl.java:6258)
                      at android.view.ViewRootImpl.enqueueInputEvent(ViewRootImpl.java:6219)
                      at android.view.ViewRootImpl$WindowInputEventReceiver.onInputEvent(ViewRootImpl.java:6387)
                      at android.view.InputEventReceiver.dispatchInputEvent(InputEventReceiver.java:185)
                      at android.view.InputEventReceiver.nativeConsumeBatchedInputEvents(Native Method)
                      at android.view.InputEventReceiver.consumeBatchedInputEvents(InputEventReceiver.java:176)
                      at android.view.ViewRootImpl.doConsumeBatchedInput(ViewRootImpl.java:6358)
                      at android.view.ViewRootImpl$ConsumeBatchedInputRunnable.run(ViewRootImpl.java:6410)
                      at android.view.Choreographer$CallbackRecord.run(Choreographer.java:874)
                      at android.view.Choreographer.doCallbacks(Choreographer.java:686)
                      at android.view.Choreographer.doFrame(Choreographer.java:615)
                      at android.view.Choreographer$FrameDisplayEventReceiver.run(Choreographer.java:860)
                      at android.os.Handler.handleCallback(Handler.java:751)
                 */
            false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return try {
            super.onTouchEvent(event)
        } catch (ex: Throwable) {
            Log.w(ex)
            false
            /*
            java.lang.IllegalArgumentException
                    at com.android.internal.util.Preconditions.checkArgument(Preconditions.java:33)
                    at android.widget.SelectionActionModeHelper$TextClassificationHelper.init(SelectionActionModeHelper.java:640)
                    at android.widget.SelectionActionModeHelper.resetTextClassificationHelper(SelectionActionModeHelper.java:203)
                    at android.widget.SelectionActionModeHelper.invalidateActionModeAsync(SelectionActionModeHelper.java:104)
                    at android.widget.Editor.invalidateActionModeAsync(Editor.java:2028)
                    at android.widget.Editor.showFloatingToolbar(Editor.java:1419)
                    at android.widget.Editor.updateFloatingToolbarVisibility(Editor.java:1397)
                    at android.widget.Editor.onTouchEvent(Editor.java:1367)
                    at android.widget.TextView.onTouchEvent(TextView.java:9701)
            */
        }
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        return try {
            super.onKeyPreIme(keyCode, event)
        } catch (ex: Throwable) {
            Log.w(ex)
            false
        }
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        try {
            super.onFocusChanged(focused, direction, previouslyFocusedRect)
        } catch (ex: Throwable) {
            /*
            java.lang.ClassCastException: android.text.SpannedString cannot be cast to android.text.Spannable
              at android.widget.Editor.onFocusChanged(Editor.java:1058)
              at android.widget.TextView.onFocusChanged(TextView.java:9262)
              at android.view.View.handleFocusGainInternal(View.java:5388)
              at android.view.View.requestFocusNoSearch(View.java:8131)
              at android.view.View.requestFocus(View.java:8110)
              at android.view.View.requestFocus(View.java:8077)
              at android.view.View.requestFocus(View.java:8056)
              at android.view.View.onTouchEvent(View.java:10359)
              at android.widget.TextView.onTouchEvent(TextView.java:9580)
              at android.view.View.dispatchTouchEvent(View.java:8981)
            */
            Log.w(ex)
        }
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        try {
            super.onWindowFocusChanged(hasWindowFocus)
        } catch (ex: Throwable) {
            Log.w(ex)
            /*
                java.lang.IndexOutOfBoundsException: setSpan (-1 ... -1) starts before 0
                        at android.text.SpannableStringInternal.checkRange(SpannableStringInternal.java:434)
                        at android.text.SpannableStringInternal.setSpan(SpannableStringInternal.java:155)
                        at android.text.SpannableString.setSpan(SpannableString.java:46)
                        at android.text.Selection.setSelection(Selection.java:76)
                        at android.widget.Editor$SelectionModifierCursorController.resetDragAcceleratorState(Editor.java:5613)
                        at android.widget.Editor$SelectionModifierCursorController.resetTouchOffsets(Editor.java:5603)
                        at android.widget.Editor$SelectionModifierCursorController.<init>(Editor.java:5286)
                        at android.widget.Editor.getSelectionController(Editor.java:2253)
                        at android.widget.Editor.refreshTextActionMode(Editor.java:1922)
                        at android.widget.Editor.onWindowFocusChanged(Editor.java:1314)
                        at android.widget.TextView.onWindowFocusChanged(TextView.java:8735)
                        at android.view.View.dispatchWindowFocusChanged(View.java:10246)
                        at android.view.ViewGroup.dispatchWindowFocusChanged(ViewGroup.java:1196)
             */
        }
    }

    override fun performClick(): Boolean {
        return try {
            super.performClick()
        } catch (ex: Throwable) {
            /*
                    android.database.sqlite.SQLiteException: not an error (code 0 SQLITE_OK[0]): Could not open the database in read/write mode.
                    android.database.sqlite.SQLiteException: not an error (code 0 SQLITE_OK[0]): Could not open the database in read/write mode.
                      at android.database.sqlite.SQLiteConnection.nativeOpen(Native Method)
                      at android.database.sqlite.SQLiteConnection.open(SQLiteConnection.java:284)
                      at android.database.sqlite.SQLiteConnection.open(SQLiteConnection.java:215)
                      at android.database.sqlite.SQLiteConnectionPool.openConnectionLocked(SQLiteConnectionPool.java:705)
                      at android.database.sqlite.SQLiteConnectionPool.open(SQLiteConnectionPool.java:272)
                      at android.database.sqlite.SQLiteConnectionPool.open(SQLiteConnectionPool.java:239)
                      at android.database.sqlite.SQLiteDatabase.openInner(SQLiteDatabase.java:1292)
                      at android.database.sqlite.SQLiteDatabase.open(SQLiteDatabase.java:1247)
                      at android.database.sqlite.SQLiteDatabase.openDatabase(SQLiteDatabase.java:903)
                      at android.database.sqlite.SQLiteDatabase.openDatabase(SQLiteDatabase.java:893)
                      at android.database.sqlite.SQLiteOpenHelper.getDatabaseLocked(SQLiteOpenHelper.java:365)
                      at android.database.sqlite.SQLiteOpenHelper.getReadableDatabase(SQLiteOpenHelper.java:322)
                      at com.androidx.galaxy.route.SpeedGps_MainActivity.readRadars(SpeedGps_MainActivity.java:2210)
                      at com.androidx.galaxy.route.SpeedGps_MainActivity.onClick(SpeedGps_MainActivity.java:1753)
                      at android.view.View.performClick(View.java:7357)
                      at android.widget.TextView.performClick(TextView.java:14263)
                      at android.view.View.performClickInternal(View.java:7323)
                      at android.view.View.access$3200(View.java:849)
                      at android.view.View$PerformClick.run(View.java:27884)
                      at android.os.Handler.handleCallback(Handler.java:873)
                 */
            Log.w(ex)
            false
        }
    }

    override fun performLongClick(): Boolean {
        return try {
            super.performLongClick()
        } catch (ex: Throwable) {
            /*
                java.lang.IllegalStateException: Drag shadow dimensions must be positive
                        at android.view.View.startDragAndDrop(View.java:27316)
                        at android.widget.Editor.startDragAndDrop(Editor.java:1340)
                        at android.widget.Editor.performLongClick(Editor.java:1374)
                        at android.widget.TextView.performLongClick(TextView.java:13544)
                        at android.view.View.performLongClick(View.java:7928)
                        at android.view.View$CheckForLongPress.run(View.java:29321)
            */
            /*
                java.lang.NullPointerException: Attempt to invoke virtual method 'int android.widget.Editor$SelectionModifierCursorController.getMinTouchOffset()' on a null object reference
                        at android.widget.Editor.touchPositionIsInSelection(Unknown:36)
                        at android.widget.Editor.performLongClick(Unknown:72)
                        at android.widget.TextView.performLongClick(Unknown:24)
            */
            Log.w(ex)
            false
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return try {
            super.onKeyDown(keyCode, event)
        } catch (ex: Throwable) {
            /*
                java.lang.IllegalArgumentException
                  at com.android.internal.util.Preconditions.checkArgument(Preconditions.java:33)
                  at android.widget.SelectionActionModeHelper$TextClassificationHelper.init(SelectionActionModeHelper.java:641)
                  at android.widget.SelectionActionModeHelper.resetTextClassificationHelper(SelectionActionModeHelper.java:204)
                  at android.widget.SelectionActionModeHelper.startActionModeAsync(SelectionActionModeHelper.java:88)
                  at android.widget.Editor.startSelectionActionModeAsync(Editor.java:2021)
                  at android.widget.Editor.refreshTextActionMode(Editor.java:1966)
                  at android.widget.TextView.spanChange(TextView.java:9525)
                  at android.widget.TextView$ChangeWatcher.onSpanChanged(TextView.java:11973)
                  at android.text.SpannableStringBuilder.sendSpanChanged(SpannableStringBuilder.java:1292)
                  at android.text.SpannableStringBuilder.setSpan(SpannableStringBuilder.java:748)
                  at android.text.SpannableStringBuilder.setSpan(SpannableStringBuilder.java:672)
                  at android.text.Selection.extendSelection(Selection.java:102)
                  at android.text.Selection.extendLeft(Selection.java:324)
                  at android.text.method.ArrowKeyMovementMethod.left(ArrowKeyMovementMethod.java:72)
                  at android.text.method.BaseMovementMethod.handleMovementKey(BaseMovementMethod.java:165)
                  at android.text.method.ArrowKeyMovementMethod.handleMovementKey(ArrowKeyMovementMethod.java:65)
                  at android.text.method.BaseMovementMethod.onKeyDown(BaseMovementMethod.java:42)
                  at android.widget.TextView.doKeyDown(TextView.java:7367)
                  at android.widget.TextView.onKeyDown(TextView.java:7117)
                  at android.view.KeyEvent.dispatch(KeyEvent.java:2707)
                  */
            Log.w(ex)
            false
        }
    }

    override fun setText(text: CharSequence, type: BufferType) {
        try {
            super.setText(text, type)
        } catch (ex: Throwable) {
            Log.w(ex)
            /*
                java.lang.IndexOutOfBoundsException:
                  at android.text.PackedIntVector.getValue (PackedIntVector.java:71)
                  at android.text.DynamicLayout.getLineTop (DynamicLayout.java:602)
                  at android.text.Layout.getLineBottom (Layout.java:1260)
                  at android.widget.TextView.invalidateRegion (TextView.java:5379)
                  at android.widget.TextView.invalidateCursor (TextView.java:5348)
                  at android.widget.TextView.spanChange (TextView.java:8351)
                  at android.widget.TextView$ChangeWatcher.onSpanAdded (TextView.java:10550)
                  at android.text.SpannableStringInternal.sendSpanAdded (SpannableStringInternal.java:315)
                  at android.text.SpannableStringInternal.setSpan (SpannableStringInternal.java:138)
                  at android.text.SpannableString.setSpan (SpannableString.java:46)
                  at android.text.Selection.setSelection (Selection.java:76)
                  at android.text.Selection.setSelection (Selection.java:87)
                  at android.text.method.ArrowKeyMovementMethod.initialize (ArrowKeyMovementMethod.java:336)
                  at android.widget.TextView.setText (TextView.java:4555)
                  at android.widget.TextView.setText (TextView.java:4424)
                  at android.widget.TextView.setText (TextView.java:4379)
             */
        }
    }
}