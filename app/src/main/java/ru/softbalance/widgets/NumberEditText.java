package ru.softbalance.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

import java.lang.reflect.Method;
import java.math.BigDecimal;

import ru.softbalance.widgets.filters.NumberInputFilter;

public class NumberEditText extends EditText {

    public static final int DIGITS_BEFORE_DOT = 10;
    public static final int DIGITS_AFTER_DOT = 2;

    private int digsBeforeDot = DIGITS_BEFORE_DOT;
    private int digsAfterDot = DIGITS_AFTER_DOT;

    private boolean showSoftInputOnFocus = true;

    public NumberEditText(Context context) {
        super(context);
        initAttrs(context, null, 0);
    }

    public NumberEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs, 0);
    }

    public NumberEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.NumberEditText,
                defStyleAttr, 0);

        try {
            digsBeforeDot = a.getInt(R.styleable.NumberEditText_digits_before_dot,
                    DIGITS_BEFORE_DOT);
            digsAfterDot = a.getInt(R.styleable.NumberEditText_digits_after_dot,
                    DIGITS_AFTER_DOT);
            showSoftInputOnFocus = a.getBoolean(R.styleable.NumberEditText_show_soft_input_on_focus, showSoftInputOnFocus);
        } finally {
            a.recycle();
        }

        showSoftInputOnFocusCompat(showSoftInputOnFocus);
        setNumberInputType();
    }

    private void setNumberInputType() {
        int inputType = InputType.TYPE_CLASS_NUMBER;
        if (digsAfterDot > 0) {
            inputType |= InputType.TYPE_NUMBER_FLAG_DECIMAL;
        }
        setInputType(inputType);
        setFilters(new InputFilter[]{new NumberInputFilter(digsBeforeDot, digsAfterDot)});
    }

    public void showSoftInputOnFocusCompat(boolean isShow) {
        showSoftInputOnFocus = isShow;
        if (Build.VERSION.SDK_INT >= 21) {
            setShowSoftInputOnFocus(showSoftInputOnFocus);
        } else {
            try {
                final Method method = EditText.class.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(this, showSoftInputOnFocus);
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public boolean isShowSoftInputOnFocus() {
        return showSoftInputOnFocus;
    }

    public boolean isValidNumberFormat(String numberText) {
        if (!TextUtils.isEmpty(numberText)) {
            try {
                new BigDecimal(numberText);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    public void setPrecision(int digsBeforeDot, int digsAfterDot) {
        this.digsBeforeDot = digsBeforeDot;
        this.digsAfterDot = digsAfterDot;
        setNumberInputType();
    }

    public BigDecimal getValue() {
        String text = getText().toString().trim();
        BigDecimal value = BigDecimal.ZERO;
        try {
            value = new BigDecimal(text);
        } catch (Exception e) {
            // Use dafault BigDecimal.ZERO value if empty string or smth
        }
        return value;
    }

    public void setValue(BigDecimal numberValue) {
        setText(numberValue.toPlainString());
    }
}