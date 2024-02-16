package com.momground.android.ui.health.graph.chart;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.momground.android.R;
import com.momground.android.data.ResultTrainingData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AvgScoreChart extends LineChart {
    long reference_timestamp = 1451660400;
    float axisHeight = 150f;

    private static final int FIRST_DAY = -1;
    private static final int VISIBLE_X_RANGE = 7;

    public AvgScoreChart(Context context) {
        super(context);
        initChart();
    }

    public AvgScoreChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initChart();
    }

    public AvgScoreChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initChart();
    }

    public long getReference_timestamp() {
        return reference_timestamp;
    }

    public void setReference_timestamp(long reference_timestamp) {
        this.reference_timestamp = reference_timestamp;
        ((AvgScoreLineChartRenderer)getRenderer()).setReference_timestamp(reference_timestamp);
        ((AvgScoreLineChartAxisRenderer)getRendererXAxis()).setReference_timestamp(reference_timestamp);
    }

    public void initChart() {
        setRenderer(new AvgScoreLineChartRenderer(this, this.getAnimator(), this.getViewPortHandler()));
        setXAxisRenderer(new AvgScoreLineChartAxisRenderer(this.getViewPortHandler(), this.getXAxis(), getTransformer(null)));
        ((AvgScoreLineChartAxisRenderer)getRendererXAxis()).setAxisHeight(axisHeight);
        setViewPortOffsets(0f,0f,0f,axisHeight);

        setScaleEnabled(false);
        setHighlightPerDragEnabled(false);
        setHighlightPerTapEnabled(false);
        getLegend().setEnabled(false);

        getAxisLeft().setDrawLabels(false);
        getAxisRight().setDrawLabels(false);

        setDescription(null);
        getAxisLeft().setDrawTopYLabelEntry(false);
        getAxisLeft().setDrawAxisLine(false);
        getAxisRight().setDrawAxisLine(false);
        getXAxis().setDrawGridLines(false);

        getXAxis().setDrawGridLines(false);
        getAxisLeft().setDrawGridLines(false);
        getAxisRight().setDrawGridLines(false);
        getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        
    }

    public void setRange(float min, float max){
        getAxisLeft().setAxisMaximum(max);
        getAxisRight().setAxisMaximum(max);

        getAxisLeft().setAxisMinimum(min);
        getAxisRight().setAxisMinimum(min);
    }

    public void setAvgScoreChart(List<ResultTrainingData> resultTrainingDataList, Drawable graphBackground) throws ParseException {
        // 1. Convert common date format -> unix time (timestamp) for x axis
        long reference_timestamp = Long.MAX_VALUE;

        for (Iterator<ResultTrainingData> iterator = resultTrainingDataList.iterator(); iterator.hasNext(); ) {
            ResultTrainingData resultTrainingData = iterator.next();

            // Calculate timestamp
            long days = TimeUnit.MILLISECONDS.toDays(resultTrainingData.getDate().getTime());
            if (reference_timestamp > days) {
                reference_timestamp = days;
            }
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);

        long today = TimeUnit.MILLISECONDS.toDays(cal.getTimeInMillis());

        if ((today - reference_timestamp) < VISIBLE_X_RANGE) {
            reference_timestamp = today - VISIBLE_X_RANGE + 1;
        }

        today = today - reference_timestamp;
        setReference_timestamp(reference_timestamp);

        // Calculate Average Scores
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        HashMap<Integer, Float> map = new HashMap<>();
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<Entry> maxValues = new ArrayList<>();

        int sum = 0;
        int count = 0;
        String currentDateString = "";

        for (int i = 0; i < resultTrainingDataList.size(); i++) {
            ResultTrainingData resultTrainingData = resultTrainingDataList.get(i);

            // Compare Date
            String dateString = format.format(resultTrainingData.getDate());

            if (!currentDateString.equals(dateString)) {
                // New DataSet starts..
                if (!currentDateString.isEmpty()) {
                    float average = (float) sum / count;

                    long days = TimeUnit.MILLISECONDS.toDays(format.parse(currentDateString).getTime()) - reference_timestamp;
                    map.put((int) days, average);

                    sum = 0;
                    count = 0;
                }

                currentDateString = dateString;
            }

            sum += resultTrainingData.getScore();
            count++;

            if (i == resultTrainingDataList.size() - 1) {
                // Last entry
                long days = TimeUnit.MILLISECONDS.toDays(format.parse(currentDateString).getTime()) - reference_timestamp;
                map.put((int) days, (float) sum / count);
            }
        }

        // Fill default values
        if (map.size() > 0) {
            Log.e("AvgScoreChart", FIRST_DAY + " " + today);

            for (int i = FIRST_DAY; i <= today; i++) {
                if (!map.containsKey(i)) {
                    values.add(new Entry(i, 0)); //날짜data 없을 경우 0으로 지정
                } else {
                    values.add(new Entry(i, map.get(i)));
                }
            }
        }

        // Calculate maxValue values
        long maxDay = 0;
        float max = 0f;
        for (int i = (int) today; i > today - 7; i--) {
            if (i < FIRST_DAY)
                break;

            if (map.containsKey(i) && max <= map.get(i)) {
                max = map.get(i);
                maxDay = i;
            }
        }

        maxValues.add(new Entry((float) maxDay, max));

        LineDataSet set = new LineDataSet(values, "Average Score");
        LineDataSet maxSet = new LineDataSet(maxValues, "Max Score");

        // 그래프 선/점 모양 설정
        IValueFormatter blankFormatter = new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return "";
            }};

        set.setValueFormatter(blankFormatter);
        set.setDrawFilled(true);
        set.setFillDrawable(graphBackground);
        set.setColor(0xffffffff);

        set.setLineWidth(2);
        set.setCircleHoleRadius(2f);
        set.setCircleRadius(4f);

        set.setCircleColor(0xffffffff);
        set.setCircleColorHole(0xffffff);
        set.setCircleColorHole(R.color.transparent);

        maxSet.setValueFormatter(blankFormatter);
        maxSet.setCircleColor(0xffffffff);
        maxSet.setLineWidth(0.1f);
        maxSet.setDrawCircleHole(false);
        maxSet.setCircleRadius(4.5f);

        List<ILineDataSet> sets = new ArrayList<>();
        sets.add(set);
        sets.add(maxSet);

        LineData data = new LineData(sets);
        setData(data);
        getData().setDrawValues(false);

        setVisibleXRange(0, VISIBLE_X_RANGE);

        getXAxis().setAxisMaximum(today + 1);
        if (today >= VISIBLE_X_RANGE) getXAxis().setAxisMinimum(FIRST_DAY - 1);
        else                          getXAxis().setAxisMinimum(FIRST_DAY);

        if (values.size() > 0) {
            Entry last_entry = values.get(values.size() - 1);
            moveViewToX(last_entry.getX());
        }
        
        setRange(-100, max * 1.5f);

        notifyDataSetChanged();
        invalidate();
    }
}