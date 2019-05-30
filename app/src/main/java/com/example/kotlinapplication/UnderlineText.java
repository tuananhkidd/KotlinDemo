package com.example.kotlinapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UnderlineText extends RelativeLayout {
    private Context mContext;
    private TextView tvTitle;
    private TextView tvDetail;

    public UnderlineText(Context context) {
        super(context);
    }

    public UnderlineText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        setParam(attrs);
    }

    public UnderlineText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        setParam(attrs);

    }

    private void init(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_underline_text, this, true);
        tvDetail = view.findViewById(R.id.tv_detail);
        tvTitle = view.findViewById(R.id.tv_title);
    }

    private void setParam(AttributeSet attrs) {
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.UnderlineText, 0, 0);

        String title = typedArray.getString(R.styleable.UnderlineText_ut_title);
        tvTitle.setText(title);

        String detail = typedArray.getString(R.styleable.UnderlineText_ut_detail);
        tvDetail.setText(detail);

        int titleColor = typedArray.getInt(R.styleable.UnderlineText_ut_color_title, R.color.colorPrimary);
        tvTitle.setTextColor(titleColor);

        int detailColor = typedArray.getInt(R.styleable.UnderlineText_ut_color_detail, R.color.colorPrimary);
        tvDetail.setTextColor(detailColor);

        boolean isShowDetail = typedArray.getBoolean(R.styleable.UnderlineText_ut_visible_detail, true);
        tvDetail.setVisibility(isShowDetail ? GONE : VISIBLE);


    }
}
