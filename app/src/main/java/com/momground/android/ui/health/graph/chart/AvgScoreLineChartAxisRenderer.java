package com.momground.android.ui.health.graph.chart;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class AvgScoreLineChartAxisRenderer extends XAxisRenderer {
    long reference_timestamp = 1451660400;
    float axisHeight = 150f;
    float textSize = 46f;
    String textColor = "#ffffff";
    public AvgScoreLineChartAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans) {
        super(viewPortHandler, xAxis, trans);
    }
    
    public float getAxisHeight() {
        return axisHeight;
    }
    
    public void setAxisHeight(float axisHeight) {
        this.axisHeight = axisHeight;
    }
    
    public long getReference_timestamp() {
        return reference_timestamp;
    }
    
    public void setReference_timestamp(long reference_timestamp) {
        this.reference_timestamp = reference_timestamp;
    }
    
    @Override
    public void renderAxisLine(Canvas c) {
        if (!mXAxis.isDrawAxisLineEnabled() || !mXAxis.isEnabled())
            return;
        
        mAxisLinePaint.setColor(mXAxis.getAxisLineColor());
        mAxisLinePaint.setStrokeWidth(mXAxis.getAxisLineWidth());
        mAxisLinePaint.setPathEffect(mXAxis.getAxisLineDashPathEffect());
        
        if (mXAxis.getPosition() == XAxis.XAxisPosition.TOP
            || mXAxis.getPosition() == XAxis.XAxisPosition.TOP_INSIDE
            || mXAxis.getPosition() == XAxis.XAxisPosition.BOTH_SIDED) {
            c.drawLine(mViewPortHandler.contentLeft(),
                mViewPortHandler.contentTop(), mViewPortHandler.contentRight(),
                mViewPortHandler.contentTop(), mAxisLinePaint);
        }
        
        if (mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM
            || mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM_INSIDE
            || mXAxis.getPosition() == XAxis.XAxisPosition.BOTH_SIDED) {
            c.drawLine(mViewPortHandler.contentLeft(),
                mViewPortHandler.contentBottom(), mViewPortHandler.contentRight(),
                mViewPortHandler.contentBottom(), mAxisLinePaint);
        }
//        mAxisLinePaint.setShader()

//        mAxisLinePaint.setColorFilter(new PorterDuffColorFilter(COLOR.parseColor("#68ba426e"), PorterDuff.Mode.MULTIPLY));
        mAxisLinePaint.setColorFilter(null);
        mAxisLinePaint.setColor(Color.parseColor("#28445e")); //date label color
        mAxisLinePaint.setStyle(Paint.Style.FILL);
        c.drawRect(mViewPortHandler.contentLeft(), mViewPortHandler.contentBottom(), mViewPortHandler.contentRight(), mViewPortHandler.contentBottom()+axisHeight, mAxisLinePaint);
        
        mAxisLinePaint.setStyle(Paint.Style.STROKE);
        mAxisLinePaint.setColorFilter(null);
//        mAxisLinePaint.setColor(0xffa4a7bf);
        mAxisLinePaint.setColor(0xffafd9e0);
        mAxisLinePaint.setStrokeWidth(4f);
        c.drawLine(mViewPortHandler.contentLeft(),mViewPortHandler.contentBottom(),mViewPortHandler.contentRight(),mViewPortHandler.contentBottom(),mAxisLinePaint);
    }
    
    @Override
    protected void drawLabels(Canvas c, float pos, MPPointF anchor) {
        
        Calendar cal = Calendar.getInstance();
        final float labelRotationAngleDegrees = mXAxis.getLabelRotationAngle();
        boolean centeringEnabled = mXAxis.isCenterAxisLabelsEnabled();
        
        mAxisLabelPaint.setColor(Color.parseColor(textColor));
        mAxisLabelPaint.setTextSize(textSize);
        
        float[] positions = new float[mXAxis.mEntryCount * 2];
        
        float last_month_x = mViewPortHandler.contentLeft() + 67;
        float last_month_y = mViewPortHandler.contentBottom();
        
        for (int i = 0; i < positions.length; i += 2) {
            
            // only fill x values
            if (centeringEnabled) {
                positions[i] = mXAxis.mCenteredEntries[i / 2];
            } else {
                positions[i] = mXAxis.mEntries[i / 2];
            }
        }
        
        mTrans.pointValuesToPixel(positions);
        
        boolean monthChange = true;
        
        for (int i = 0; i < positions.length; i += 2) {
            
            float x = positions[i];
            
            if (mViewPortHandler.isInBoundsX(x)) {
                
                String label = mXAxis.getValueFormatter().getFormattedValue(mXAxis.mEntries[i / 2], mXAxis);
                
                //Value Format을 받아도...후처리가 필요할듯 함. 왜냐하면?? 월을 중간에 끼워야 하기 때문이다.........;;ㅠㅠ;ㅠ;ㅠ;ㅠ;ㅠ;ㅠ;
                float fLabel = Float.parseFloat(label);
                long timestamp_day = (long)fLabel + reference_timestamp+1;
                cal.setTimeInMillis(TimeUnit.DAYS.toMillis(timestamp_day));
                int day = cal.get(Calendar.DAY_OF_MONTH);
                label = String.format("%02d",day);
                int month = cal.get(Calendar.MONTH)+1;
                int year = cal.get(Calendar.YEAR);
                String label_month = String.format("%02d", month);
                
                if(monthChange) {
                    drawMonthLabel(c, last_month_x, last_month_y, label_month);
                    monthChange = !monthChange;
                }
                
                
                if (mXAxis.isAvoidFirstLastClippingEnabled()) {
                    
                    // avoid clipping of the last
                    if (i == mXAxis.mEntryCount - 1 && mXAxis.mEntryCount > 1) {
                        float width = Utils.calcTextWidth(mAxisLabelPaint, label);
                        
                        if (width > mViewPortHandler.offsetRight() * 2
                            && x + width > mViewPortHandler.getChartWidth())
                            x -= width / 2;
                        
                        // avoid clipping of the first
                    } else if (i == 0) {
                        
                        float width = Utils.calcTextWidth(mAxisLabelPaint, label);
                        x += width / 2;
                    }
                }
                
                
                if(day == 1 && i != 0) {
                    drawMonthLabel(c, x - 100f , last_month_y, label_month);
                }
                if(timestamp_day == TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis())) {
                    mAxisLabelPaint.setStyle(Paint.Style.FILL);
                    mAxisLinePaint.setColor(0xffffffff);
                    int triHeight = 13;
                    int triWidthHalf = 12;
                    Point point = new Point((int)x, (int)mViewPortHandler.contentBottom()-triHeight);
                    Path path = new Path();
                    path.moveTo(point.x,point.y);
                    path.lineTo(point.x + triWidthHalf, mViewPortHandler.contentBottom());
                    path.lineTo(point.x - triWidthHalf, mViewPortHandler.contentBottom());
                    path.close();
                    c.drawPath(path,mAxisLabelPaint);
                }
                drawLabel(c, label, x, pos, anchor, labelRotationAngleDegrees);
            }
        }
    }
    
    protected  void drawMonthLabel(Canvas c, float x, float y, String label) {
        float labelSize = 36f;
        Paint monthPaint = new Paint();
        monthPaint.setAntiAlias(true);
        monthPaint.setColor(0xff8075b6);  //월 색상
        c.drawCircle(x, y, 45f, monthPaint);
        monthPaint.setTextSize(labelSize);
        monthPaint.setColor(0xffffffff);
        float textWidthHalf = (float)(Utils.calcTextWidth(monthPaint, label)) / 2;
        float textHeightHalf = (float)(Utils.calcTextWidth(monthPaint, label)) / 2 -5f; // -5f는 위치 보정값 (위로)
        c.drawText(label, x - textWidthHalf, y + textHeightHalf, monthPaint);
    }
    
    @Override
    protected void drawLabel(Canvas c, String formattedLabel, float x, float y, MPPointF anchor, float angleDegrees) {
        super.drawLabel(c, formattedLabel, x, y, anchor, angleDegrees);
    }
    
    @Override
    public void renderAxisLabels(Canvas c) {
        
        if (!mXAxis.isEnabled() || !mXAxis.isDrawLabelsEnabled())
            return;

//        float yoffset = mXAxis.getYOffset();
        float yoffset = axisHeight / 2 - textSize/2;
        
        mAxisLabelPaint.setTypeface(mXAxis.getTypeface());
        mAxisLabelPaint.setTextSize(mXAxis.getTextSize());
        mAxisLabelPaint.setColor(mXAxis.getTextColor());
        
        MPPointF pointF = MPPointF.getInstance(0,0);
        if (mXAxis.getPosition() == XAxis.XAxisPosition.TOP) {
            pointF.x = 0.5f;
            pointF.y = 1.0f;
            drawLabels(c, mViewPortHandler.contentTop() - yoffset, pointF);
            
        } else if (mXAxis.getPosition() == XAxis.XAxisPosition.TOP_INSIDE) {
            pointF.x = 0.5f;
            pointF.y = 1.0f;
            drawLabels(c, mViewPortHandler.contentTop() + yoffset + mXAxis.mLabelRotatedHeight, pointF);
            
        } else if (mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM) {
            pointF.x = 0.5f;
            pointF.y = 0.0f;
            drawLabels(c, mViewPortHandler.contentBottom() + yoffset, pointF);
            
        } else if (mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM_INSIDE) {
            pointF.x = 0.5f;
            pointF.y = 0.0f;
            drawLabels(c, mViewPortHandler.contentBottom() - yoffset - mXAxis.mLabelRotatedHeight, pointF);
            
        } else { // BOTH SIDED
            pointF.x = 0.5f;
            pointF.y = 1.0f;
            drawLabels(c, mViewPortHandler.contentTop() - yoffset, pointF);
            pointF.x = 0.5f;
            pointF.y = 0.0f;
            drawLabels(c, mViewPortHandler.contentBottom() + yoffset, pointF);
        }
        MPPointF.recycleInstance(pointF);
    }
}
