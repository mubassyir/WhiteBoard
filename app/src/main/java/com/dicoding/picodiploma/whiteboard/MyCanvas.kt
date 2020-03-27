package com.dicoding.picodiploma.whiteboard

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat


private const val STROKE_WITH = 12f
class MyCanvas (context: Context) : View(context){
    private var path = Path()
    private val drawColor = ResourcesCompat.getColor(resources,R.color.drawColor,null)
    private val backgroundColor = ResourcesCompat.getColor(resources,R.color.backgroundColor,null)

    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap
    private lateinit var frame:Rect

    private val paint = Paint().apply{
        color = drawColor
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeWidth = STROKE_WITH
        strokeCap = Paint.Cap.ROUND
    }

    private val touchTolerance = ViewConfiguration.get(context).scaledPagingTouchSlop

    private var currentX = 0f
    private var currentY = 0f

    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        if (::extraBitmap.isInitialized) extraBitmap.recycle()
        extraBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(backgroundColor)

        val insert = 10
        frame= Rect(insert,insert,w-insert,h-insert)
    }

    override fun onDraw(canvas: Canvas?) {

        canvas?.drawBitmap(extraBitmap,0f,0f,null)
        extraCanvas.drawRect(frame,paint)
    }
    private fun touchStart(){
        path.reset()
        path.moveTo(motionTouchEventX,motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY
    }
    private fun touchMove(){
        val dx = Math.abs(motionTouchEventX-currentX)
        val dy = Math.abs(motionTouchEventY-currentY)


        if (dx>=touchTolerance|| dy>touchTolerance){
            path.quadTo(
                currentX,currentY,
                (motionTouchEventX+currentX)/2,
                (motionTouchEventY+currentY)/2
            )
            currentX = motionTouchEventX
            currentY = motionTouchEventY
            extraCanvas.drawPath(path,paint)
        }
        invalidate()
    }
    private fun touchUp(){
        path.reset()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        motionTouchEventX = event!!.x
        motionTouchEventY = event!!.y

        when (event.action){
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }
        return true
    }

    fun eraseDraw(){
        paint.color = Color.WHITE
    }
    fun drawCanvas(){
        paint.color = Color.BLACK
    }
}