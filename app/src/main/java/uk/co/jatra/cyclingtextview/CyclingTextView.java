package uk.co.jatra.cyclingtextview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.view.Gravity.CENTER_HORIZONTAL;
import static android.view.animation.AnimationUtils.loadAnimation;

public class CyclingTextView extends TextSwitcher {
    private List<CharSequence> labels;
    private int currentLabel;
    private Runnable cycle;
    private Handler handler;
    private float sp = 48f;
    private int color = Color.BLUE;
    private int delayMillis = 3000;

    public CyclingTextView(Context context) {
        super(context);
        init(null, 0);
    }

    public CyclingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public int getDelayMillis() {
        return delayMillis;
    }

    public void setDelayMillis(int delayMillis) {
        this.delayMillis = delayMillis;
    }

    public float getSize() {
        return sp;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setTextSize(float sp) {
        this.sp = sp;
    }

    public void setText(List<CharSequence> labels) {
        this.labels = new ArrayList();
        this.labels.addAll(labels);
        if (labels == null || labels.isEmpty()) {
            setText("");
            handler.removeCallbacks(cycle);
        } else {
            //make this an attribute
            currentLabel = 0;
            handler.post(cycle);
        }
    }

    private void init(AttributeSet attrs, int defStyle) {
        handler = new Handler();
        cycle = new Runnable() {
            @Override
            public void run() {
                if (currentLabel >= labels.size()) {
                    currentLabel = 0;
                }
                updateLabel(currentLabel++);
                handler.postDelayed(cycle, delayMillis);
            }
        };

        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CyclingTextView,
                defStyle, 0);
        try {
            delayMillis = a.getInteger(R.styleable.CyclingTextView_delay, 3000);
            sp = a.getDimension(R.styleable.CyclingTextView_textSize, 14);
            color = a.getColor(R.styleable.CyclingTextView_textColor, Color.BLUE);
            CharSequence[] entries = a.getTextArray(R.styleable.CyclingTextView_android_entries);
            if (entries != null) {
                setText(Arrays.asList(entries));
            }
        } finally {
            a.recycle();
        }

        setFactory(new ViewFactory() {
            public View makeView() {
                TextView textView = new TextView(getContext());
                textView.setGravity(CENTER_HORIZONTAL);
                textView.setTextSize(sp);
                textView.setTextColor(color);
                return textView;
            }
        });

        setInAnimation(loadAnimation(getContext(), R.anim.slide_in_down));
        setOutAnimation(loadAnimation(getContext(), R.anim.slide_out_down));

    }

    private void updateLabel(int index) {
        setText(labels.get(index));
    }
}
