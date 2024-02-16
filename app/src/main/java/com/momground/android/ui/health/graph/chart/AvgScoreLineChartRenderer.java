package com.momground.android.ui.health.graph.chart;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.List;

public class AvgScoreLineChartRenderer extends MyLineChart {
    long reference_timestamp = 1451660400;
    
    public long getReference_timestamp() {
        return reference_timestamp;
    }
    
    public void setReference_timestamp(long reference_timestamp) {
        this.reference_timestamp = reference_timestamp;
    }
    
    public AvgScoreLineChartRenderer(LineDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }
    
    @Override
    protected void drawLinear(Canvas c, ILineDataSet dataSet) {
        if (dataSet.getLineWidth() > 1)
            super.drawLinear(c, dataSet);
    }
    
    @Override
    protected void drawCircles(Canvas c) {
        
        mRenderPaint.setStyle(Paint.Style.FILL);
        
        float phaseY = mAnimator.getPhaseY();
        
        mCirclesBuffer[0] = 0;
        mCirclesBuffer[1] = 0;
        
        List<ILineDataSet> dataSets = mChart.getLineData().getDataSets();
        
        for (int i = 0; i < dataSets.size(); i++) {
            
            ILineDataSet dataSet = dataSets.get(i);
            
            if (!dataSet.isVisible() || !dataSet.isDrawCirclesEnabled() ||
                dataSet.getEntryCount() == 0)
                continue;
            
            mCirclePaintInner.setColor(dataSet.getCircleHoleColor());
            
            Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());
            
            mXBounds.set(mChart, dataSet);
            
            float circleRadius = dataSet.getCircleRadius();
            float glowCircleRadius = circleRadius + 50;
            int[] glowColors = {Color.parseColor("#ffffffff"), Color.parseColor("#00ffffff")};
            float[] glowPos = {0f, 0.6f};
            float circleHoleRadius = dataSet.getCircleHoleRadius();
            boolean drawCircleHole = dataSet.isDrawCircleHoleEnabled() &&
                circleHoleRadius < circleRadius &&
                circleHoleRadius > 0.f;
            boolean drawTransparentCircleHole = drawCircleHole &&
                dataSet.getCircleHoleColor() == ColorTemplate.COLOR_NONE;
            
            DataSetImageCache imageCache;
            
            if (mImageCaches.containsKey(dataSet)) {
                imageCache = mImageCaches.get(dataSet);
            } else {
                imageCache = new DataSetImageCache();
                mImageCaches.put(dataSet, imageCache);
            }
            
            boolean changeRequired = imageCache.init(dataSet);
            
            // only fill the cache with new bitmaps if a change is required
            if (changeRequired) {
                imageCache.fill(dataSet, drawCircleHole, drawTransparentCircleHole);
            }
            
            int boundsRangeCount = mXBounds.range + mXBounds.min;
            
            for (int j = mXBounds.min; j <= boundsRangeCount; j++) {
                
                Entry e = dataSet.getEntryForIndex(j);
                
                if (e == null) break;
                
                mCirclesBuffer[0] = e.getX();
                mCirclesBuffer[1] = e.getY() * phaseY;
                
                trans.pointValuesToPixel(mCirclesBuffer);
                
                if (!mViewPortHandler.isInBoundsRight(mCirclesBuffer[0]))
                    break;
                
                if (!mViewPortHandler.isInBoundsLeft(mCirclesBuffer[0]) ||
                    !mViewPortHandler.isInBoundsY(mCirclesBuffer[1]))
                    continue;
                
                Bitmap circleBitmap = imageCache.getBitmap(j);
                
                if (circleBitmap != null) {
                    c.drawBitmap(circleBitmap, mCirclesBuffer[0] - circleRadius, mCirclesBuffer[1] - circleRadius, mRenderPaint);
                }
                if (dataSet.getLineWidth() < 1f) {
                    mCirclePaintInner.setAntiAlias(true);
//                    mCirclePaintInner.setColorFilter(new PorterDuffColorFilter(COLOR.parseColor("#a0ffffff"), PorterDuff.Mode.SCREEN));
                    mCirclePaintInner.setShader(new RadialGradient(mCirclesBuffer[0], mCirclesBuffer[1], glowCircleRadius, glowColors, glowPos, Shader.TileMode.CLAMP));
                    c.drawCircle(mCirclesBuffer[0], mCirclesBuffer[1], glowCircleRadius, mCirclePaintInner);
//                    mCirclePaintInner.setColorFilter(null);
                }
            }
        }
    }
}