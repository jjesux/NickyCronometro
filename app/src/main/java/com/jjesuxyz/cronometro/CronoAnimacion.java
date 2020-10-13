package com.jjesuxyz.cronometro;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;




/**
 * CronoAnimacion class is used to animate a View widget. This animation change
 * the circles it draws every second of elapsed time. The new values that it
 * uses to do the drawing are obtained from the main activity UI. This animation
 * can be running, paused or stopped. The state where it is depends if the user
 * has started, paused or stopped the animation.
 *
 * Created by jjesu.
 */
public class CronoAnimacion extends View {
                                  //Reference to the MainActivity object.
    private Context context;
                                  //Paint object to define color and width
                                  //of the drew line.
    private Paint paint;
                                  //Vars to hold the different radios of
                                  //circles: second, minutes and hours.
    private RectF rectFSecs, rectFMins, rectFHrs;
                                  //var holds the change of the arcs as one
                                  //second pass.
    private float anguloSecs;
                                  //Var holds the change of the arc as one
                                  //minute time pass.
    private float anguloMins;
                                  //Var holds the change of the arc as one
                                  //hour pass.
    private float anguloHrs;
                                  //Var let know the onDraw function when to
                                  //draw on the View
    private boolean boolChronoStarted = false;



    /**
     * CronoAnimacion(Context, AttributeSet) constructor is used to initialize
     * variables that are used to draw circles that represent the seconds, minutes
     * and hours. The radious of the circles are defined in this constructor. All
     * other variables are set to zero second, zero minutes, and zero hours.
     *
     * @param context type Context
     * @param attrs type AttributeSet
     */
    public CronoAnimacion(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
                                  //Paint object to set line width and color.
        paint = new Paint();
                                  //Setting the radios of the three circles to
                                  //be drawn.
        rectFHrs  = new RectF(0, 0, 0, 0);
        rectFMins = new RectF(0, 0, 0, 0);
        rectFSecs = new RectF(0, 0, 0, 0);
                                  //Initializing to zero the seconds, minutes
                                  //and hours vars.
        anguloSecs = 0.0f;
        anguloMins = 0.0f;
        anguloHrs  = 0.0f;

    }   //End of CronoAnimacion() constructor




    public void setCircleSize(int left, int top, int right, int botton) {
        rectFHrs.set(left, top, right, botton);
        rectFMins.set(left + 25, top + 25, right - 25, botton - 25);
        rectFSecs.set(left + 50, top + 50, right - 50, botton - 50);
    }



    /**
     * onDraw(Canvas) callback function is called by the system, when it has been
     * invalidated, to redraw the circles with new values after a second has
     * elapsed. Those update values are done in another function.
     *
     * @param canvas type Canvas
     */
    @Override
    protected void onDraw(Canvas canvas){
                                  //Setting the width of the line to be drew.
        paint.setStrokeWidth(8.0f);
                                  //Setting the color of the line.
        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorLetrasEnable));
                                  //Setting the style of the hand clock line.
        paint.setStyle(Paint.Style.FILL);
                                  //Drawing the hand clock arc.
        canvas.drawArc(rectFSecs, 270f + anguloSecs, 3.0f, true, paint);
                                  //Resetting the style to draw circles
        paint.setStyle(Paint.Style.STROKE);
                                  //Drawing the circle of seconds.
        canvas.drawArc(rectFSecs, 270f, anguloSecs, false, paint);
                                  //Drawing the circle of minutes.
        canvas.drawArc(rectFMins, 270f, anguloMins, false, paint);
                                  //Drawing the circle of hours.
        canvas.drawArc(rectFHrs, 270f, anguloHrs, false, paint);
                                  //Checking if the user has started the chronometer.
        if (boolChronoStarted == true) {
                                  //Recalling update function to check if values
                                  //have reach 60.
            update();
        }
    }   //End of onDraw() function



    /**
     * update() function is used to check when the values of the second, minutes
     * and hours have reach the values of 60 and the seconds, minutes and hours
     * variables have to be reset to zero. This checking is done after this class
     * receive the new values after one second has elapsed.  These values of the
     * variables are used to draw the three circles that represent seconds,
     * minutes and hours.
     */
    private void update() {
                                  //Setting the new value of the second arc
                                  //after one second.
        anguloSecs = anguloSecs + 6.0f;
                                  //Checking if the second arc has reach the
                                  //60 seconds
        if(anguloSecs >= 360.0f) {
                                  //Resetting second arc value to zero.
            anguloSecs = 0.0f;
                                  //Incrementing one minute the minute arc.
            anguloMins = anguloMins + 6.0f;
                                  //Checking if the minute arc has reach the
                                  //60 minutes.
            if (anguloMins >= 360.0f) {
                                  //Resetting the minute arc to zero.
                anguloMins = 0.0f;
                                  //Incrementing one hour the hour arc.
                anguloHrs = anguloHrs + 6.0f;
            }
        }
    }   //End of update() function



    /**
     * invalidateOnDraw(int, int, int) function is used to readjust the value of
     * the arc circles as one second of time pass. This function is called from
     * the MainActivity every second when it is in the running state. It receives
     * the number of seconds, minutes and hours that have elapsed since the user
     * started the chronometer. It just multiplies those values by six, and it
     * updates the local variables with those new values.
     *
     * @param hrs type int
     * @param mins type int
     * @param secs type int
     */
    public void invalidateOnDraw(int hrs, int mins, int secs) {
                                  //Readjusting the arc dimensions with new values
                                  // after one second of time has passed.
        anguloHrs = (float) hrs * 6.0f;
        anguloMins = (float) mins * 6.0f;
        anguloSecs = (float) secs * 6.0f;
                                  //Calling invalidate to indirectly call the
                                  //onDraw function.
        invalidate();
    }   //End of invalidateOnDraw() function



    /**
     * setBoolChronoStarted(boolean) function is used to set the state of the
     * chronometer animation to started or running.  This state is change when
     * the user click the start or stop button on the main activity UI. So the
     * application is running but the chronometer animation can be running or
     * no running or paused.
     * A parameter with a true value change the state to running/started.
     * A parameter with a false value change the state to stopped or paused.
     *
     * @param status type boolean
     */
    public void setBoolChronoStarted(boolean status) {
        boolChronoStarted = status;
    }   //End of setBoolChronoStarted() function



    /**
     * resetAngulosAnimacion() function is used to set the arc of the curve
     * lines to zero when the user stop the chronometer animation or time
     * counting. There are three circles. One is the circle representing seconds,
     * another represents minutes, another represents hours. The values of
     * this variables change/grow as the time pass, but this function is called
     * only when the chronometer is stopped.
     * It is called from the MainActivity when the stop button is clicked.
     */
    public void resetAnguloAnimacion() {
        anguloSecs = anguloMins = anguloHrs = 0.0f;
    }   //End of resetAngulosAnimacion() function



    /**
     * The l(String) function is used only to debug this class. It uses the
     * Log.d() function to pass the information to the Android Monitor window.
     * This information contains the class name and some information about the
     * error or data about the debugging process.
     *
     * @param str type String
     */
    private void l(String str) {
        Log.d("NICKY", this.getClass().getSimpleName() + " -> " + str);
    }

}   //End of the CronoAnimacion Class


/*********************************END OF FILE CronoAnimacion.java**********************************/


