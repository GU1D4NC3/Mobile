package com.momground.android.ui.health.radar;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.renderer.LineRadarRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;


public class NutrientDomainChartRenderer extends LineRadarRenderer {

    protected RadarChart mChart;

    public NutrientDomainChartRenderer(RadarChart chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(animator, viewPortHandler);
        this.mChart = chart;
    }
    
    @Override
    public void initBuffers() {
    
    }
    
    @Override
    public void drawData(Canvas c) {
        RadarData radarData = mChart.getData();
    
        int mostEntries = radarData.getMaxEntryCountSet().getEntryCount();
    
        for (IRadarDataSet set : radarData.getDataSets()) {
        
            if (set.isVisible()) {
                drawDataSet(c, set, mostEntries);
            }
        }
    }
    
    protected Path mDrawDataSetSurfacePathBuffer = new Path();
    
    /**
     * Draws the RadarDataSet
     *
     * @param c
     * @param dataSet
     * @param mostEntries the entry count of the dataset with the most entries
     */
    protected void drawDataSet(Canvas c, IRadarDataSet dataSet, int mostEntries) {
        
        float phaseX = mAnimator.getPhaseX();
        float phaseY = mAnimator.getPhaseY();
        
        float sliceangle = mChart.getSliceAngle();
        
        // calculate the factor that is needed for transforming the value to
        // pixels
        float factor = mChart.getFactor();
        
        MPPointF center = mChart.getCenterOffsets();
        
        MPPointF pOut = MPPointF.getInstance(0,0);
        Path surface = mDrawDataSetSurfacePathBuffer;
        surface.reset();
        
        boolean hasMovedToPoint = false;
        
        for (int j = 0; j < dataSet.getEntryCount(); j++) {
            
            mRenderPaint.setColor(dataSet.getColor(j));
            
            RadarEntry e = dataSet.getEntryForIndex(j);
            
            Utils.getPosition(
                center,
                (e.getY() - mChart.getYChartMin()) * factor * phaseY,
                sliceangle * j * phaseX + mChart.getRotationAngle(), pOut);
            
            if (Float.isNaN(pOut.x))
                continue;
            
            if (!hasMovedToPoint) {
                surface.moveTo(pOut.x, pOut.y);
                hasMovedToPoint = true;
            } else
                surface.lineTo(pOut.x, pOut.y);
        }
        
        if (dataSet.getEntryCount() > mostEntries) {
            // if this is not the largest set, draw a line to the center before closing
            surface.lineTo(center.x, center.y);
        }
        
        surface.close();
        
        if (dataSet.isDrawFilledEnabled()) {
            
            final Drawable drawable = dataSet.getFillDrawable();
            if (drawable != null) {
                
                drawFilledPath(c, surface, drawable);
            } else {
                
                drawFilledPath(c, surface, dataSet.getFillColor(), dataSet.getFillAlpha());
            }
        }
        
        mRenderPaint.setStrokeWidth(dataSet.getLineWidth());
        mRenderPaint.setStyle(Paint.Style.STROKE);
        
        // draw the line (only if filled is disabled or alpha is below 255)
        if (!dataSet.isDrawFilledEnabled() || dataSet.getFillAlpha() < 255)
            c.drawPath(surface, mRenderPaint);
        
        MPPointF.recycleInstance(center);
        MPPointF.recycleInstance(pOut);
    }
    
    @Override
    public void drawValues(Canvas c) {
    
    }
    
    @Override
    public void drawExtras(Canvas c) {
        drawWeb(c);
    }
    
    protected void drawWeb(Canvas c) {
        
        float sliceangle = mChart.getSliceAngle();
        
        // calculate the factor that is needed for transforming the value to
        // pixels
        float factor = mChart.getFactor();
        float rotationangle = mChart.getRotationAngle();
        
        MPPointF center = mChart.getCenterOffsets();
        
        final int xIncrements = 1 + mChart.getSkipWebLineCount();
        int maxEntryCount = mChart.getData().getMaxEntryCountSet().getEntryCount();
        
        MPPointF p = MPPointF.getInstance(0,0);
        for (int i = 0; i < maxEntryCount; i += xIncrements) {
            
            Utils.getPosition(
                center,
                mChart.getYRange() * factor,
                sliceangle * i + rotationangle,
                p);

        }
        MPPointF.recycleInstance(p);
        
        // draw the inner-web
//        mWebPaint.setStrokeWidth(mChart.getWebLineWidthInner());
//        mWebPaint.setColor(mChart.getWebColorInner());
//        mWebPaint.setAlpha(mChart.getWebAlpha());
        
        int labelCount = mChart.getYAxis().mEntryCount;
        
        MPPointF p1out = MPPointF.getInstance(0,0);
        MPPointF p2out = MPPointF.getInstance(0,0);
        for (int j = 0; j < labelCount; j++) {
            
            for (int i = 0; i < mChart.getData().getEntryCount(); i++) {
                
                float r = (mChart.getYAxis().mEntries[j] - mChart.getYChartMin()) * factor;
                
                Utils.getPosition(center, r, sliceangle * i + rotationangle, p1out);
                Utils.getPosition(center, r, sliceangle * (i + 1) + rotationangle, p2out);
                
//                c.drawLine(p1out.x, p1out.y, p2out.x, p2out.y, mWebPaint);
                
                
            }
        }
        MPPointF.recycleInstance(p1out);
        MPPointF.recycleInstance(p2out);
    }
    
    @Override
    public void drawHighlighted(Canvas c, Highlight[] indices) {
    
    }
}
