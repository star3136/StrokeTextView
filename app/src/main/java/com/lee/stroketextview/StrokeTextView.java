package com.lee.stroketextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by Allen on 2017/3/16.
 */

public class StrokeTextView extends TextView {
    private static final int HORIZENTAO = 0;
    private static final int VERTICAL = 1;

    private int[] mGradientColor;
    private int mStrokeWidth;
    private int mStrokeColor = Color.BLACK;
    private LinearGradient mGradient;
    private boolean gradientChanged;
    private int mTextColor;
    private TextPaint mPaint;
    private int mGradientOrientation;


    public StrokeTextView(Context context) {
        super(context);
        init(context, null);
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = getPaint();
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StrokeTextView);
            mStrokeColor = a.getColor(R.styleable.StrokeTextView_strokeColor, Color.BLACK);
            mStrokeWidth = a.getDimensionPixelSize(R.styleable.StrokeTextView_strokeWidth, 0);
            mGradientOrientation = a.getInt(R.styleable.StrokeTextView_gradientOrientation, HORIZENTAO);

            setStrokeColor(mStrokeColor);
            setStrokeWidth(mStrokeWidth);
            setGradientOrientation(mGradientOrientation);
            a.recycle();
        }
    }

    public void setGradientOrientation(int orientation) {
        if (mGradientOrientation != orientation) {
            mGradientOrientation = orientation;
            gradientChanged = true;
            invalidate();
        }
    }

    public void setGradientColor(int[] gradientColor) {
        if (!Arrays.equals(gradientColor, mGradientColor)) {
            mGradientColor = gradientColor;
            gradientChanged = true;
            invalidate();
        }
    }

    public void setStrokeColor(int color) {
        if (mStrokeColor != color) {
            mStrokeColor = color;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        CharSequence text = getText();
        if (mStrokeWidth > 0) {
            /**
             * 绘制描边
             */
            mTextColor = getCurrentTextColor(); //保存文本的颜色
            mPaint.setStrokeWidth(mStrokeWidth); //设置描边宽度
            mPaint.setFakeBoldText(true); //设置粗体
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            setColor(mStrokeColor); // 设置描边颜色
            mPaint.setShader(null);  //清空shader
            canvas.drawText(text, 0, text.length(), 0, getHeight() - fm.descent, mPaint);

            /**
             * 绘制文本
             */
            if (gradientChanged) { //如果有渐变颜色，则设置LinearGradient
                if (mGradientColor != null) {
                    mGradient = getGradient();
                }
                gradientChanged = false;
            }
            if (mGradient != null) { //设置渐变Shader
                mPaint.setShader(mGradient);
                mPaint.setColor(Color.WHITE);
            } else {
                setColor(mTextColor);
            }

            mPaint.setStrokeWidth(0);
            canvas.drawText(text, 0, text.length(), 0, getHeight() - fm.descent, mPaint);
        } else {
            super.onDraw(canvas);
        }
    }

    public void setStrokeWidth(int width) {
        mStrokeWidth = width;
        invalidate();
    }

    private LinearGradient getGradient() {
        LinearGradient gradient;
        if (mGradientOrientation == HORIZENTAO) {
            gradient = new LinearGradient(0, 0, getWidth(), 0, mGradientColor, null, Shader.TileMode.CLAMP);
        } else {
            gradient = new LinearGradient(0, 0, 0, getHeight(), mGradientColor, null, Shader.TileMode.CLAMP);
        }
        return gradient;
    }

    private void setColor(int color) {
        mPaint.setColor(color);
    }
}
