package ir.devapp.mahbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MahBar extends View {
    private int minValue = 0;
    private int maxValue = 100;
    private int value;

    private int bar_height;
    private int circle_radius;
    private int circle_text_size;
    private int space_after_bar;
    private int max_value_text_size;
    private int max_value_text_color;
    private int label_text_size;
    private int label_text_color;
    private int circle_text_color;
    private int base_color;
    private int fill_color;
    private String label_text = "BAR";

    private Paint labelPaint, circlePaint;
    private Paint barBasePaint, barFillPaint;
    private Paint currentValuePaint, maxValuePaint;

    public MahBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MahBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        draw_label(canvas);
        draw_max_value(canvas);
        draw_bar(canvas);
    }

    private void draw_bar(Canvas canvas) {

    }

    private void draw_max_value(Canvas canvas) {
        float x = getWidth() - getPaddingRight();
        String maxValueText = String.valueOf(maxValue);
        Rect bounds = new Rect();
        maxValuePaint.getTextBounds(maxValueText, 0, maxValueText.length(), bounds);
        float y = getBarCenter() + bounds.height() / 2;
        canvas.drawText(maxValueText, x, y, maxValuePaint);
    }

    private void draw_label(Canvas canvas) {
        float x = getPaddingLeft();
        Rect bounds = new Rect();
        labelPaint.getTextBounds(label_text, 0, label_text.length(), bounds);
        float y = bounds.height() + getPaddingTop();
        canvas.drawText(label_text, x, y, labelPaint);
    }

    private float getBarCenter() {
        float barCenter = (getHeight() - getPaddingTop() - getPaddingBottom()) / 2;
        barCenter += getPaddingTop() + 0.1f * getHeight();
        return barCenter;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int size = getPaddingLeft() + getPaddingLeft();
        Rect bounds = new Rect();
        labelPaint.getTextBounds(label_text, 0, label_text.length(), bounds);
        size += bounds.width();

        bounds = new Rect();
        String maxValueText = String.valueOf(maxValue);
        labelPaint.getTextBounds(maxValueText, 0, maxValueText.length(), bounds);
        size += bounds.width();

        return resolveSizeAndState(size, measureSpec, 0);
    }

    private int measureHeight(int measureSpec) {
        int size = getPaddingBottom() + getPaddingTop();
        size += labelPaint.getFontSpacing();
        float maxValueSpacing = maxValuePaint.getFontSpacing();
        size += Math.max(maxValueSpacing, Math.max(circle_radius * 2, bar_height));
        return resolveSizeAndState(size, measureSpec, 0);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray tp = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MahBar, 0, 0);

        value = tp.getInteger(R.styleable.MahBar_value, 0);
        minValue = tp.getInteger(R.styleable.MahBar_min_value, 0);
        maxValue = tp.getInteger(R.styleable.MahBar_max_value, 100);

        bar_height = tp.getDimensionPixelSize(R.styleable.MahBar_bar_height, 0);
        circle_radius = tp.getDimensionPixelSize(R.styleable.MahBar_circle_radius, 0);
        circle_text_size = tp.getDimensionPixelSize(R.styleable.MahBar_circle_text_size, 0);
        space_after_bar = tp.getDimensionPixelSize(R.styleable.MahBar_space_after_bar, 0);
        max_value_text_size = tp.getDimensionPixelSize(R.styleable.MahBar_max_value_text_size, 0);
        max_value_text_color = tp.getColor(R.styleable.MahBar_max_value_text_color, Color.BLACK);
        label_text_size = tp.getDimensionPixelSize(R.styleable.MahBar_label_text_size, 0);
        label_text_color = tp.getColor(R.styleable.MahBar_label_text_color, Color.BLACK);
        circle_text_color = tp.getColor(R.styleable.MahBar_circle_text_color, Color.WHITE);
        base_color = tp.getColor(R.styleable.MahBar_base_color, Color.BLACK);
        fill_color = tp.getColor(R.styleable.MahBar_fill_color, Color.RED);
        label_text = tp.getString(R.styleable.MahBar_label_text);

        tp.recycle();

        labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelPaint.setTextSize(label_text_size);
        labelPaint.setColor(label_text_color);
        labelPaint.setTextAlign(Paint.Align.LEFT);
        labelPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        maxValuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        maxValuePaint.setTextSize(max_value_text_size);
        maxValuePaint.setColor(max_value_text_color);
        maxValuePaint.setTextAlign(Paint.Align.LEFT);
        maxValuePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        barBasePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        barBasePaint.setColor(base_color);

        barFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        barFillPaint.setColor(fill_color);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(fill_color);

        currentValuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        currentValuePaint.setTextSize(circle_text_size);
        currentValuePaint.setColor(circle_text_color);
        currentValuePaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
        invalidate();
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = (maxValue > 0) ? maxValue : 100;
        invalidate();
        requestLayout();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int newValue) {

        this.value = (newValue > maxValue) ? maxValue : (newValue < 0) ? 0 : newValue;
        invalidate();
    }
}
