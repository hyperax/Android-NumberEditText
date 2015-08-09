package ru.softbalance.widgets.filters;

import android.text.InputFilter;
import android.text.Spanned;

public class NumberInputFilter implements InputFilter {
    public static final char DECIMAL_DELIMITER = '.';

    private int digsAfterDot;
    private int digsBeforeDot;

    public NumberInputFilter(int digsAfterDot, int digsBeforeDot) {
        this.digsAfterDot = digsAfterDot;
        this.digsBeforeDot = digsBeforeDot;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        boolean isValid = true;

        // Получаем значение
        StringBuilder newText = new StringBuilder(dest).replace(dstart, dend, source.toString());

        int size = newText.length();
        int decInd = -1; // индекс десятичного разделителя
        // проверяем десятичный разделитель
        // количество разделителей не должно превышать 1
        for (int i = 0; i < size; i++) {
            if (newText.charAt(i) == DECIMAL_DELIMITER) {
                if (decInd < 0) {
                    decInd = i; // запоминаем индекс разделителя
                } else { // разделителей более 1, некорректный ввод
                    isValid = false;
                    break;
                }
            }
        }

        // проверяем длину числа

        if (decInd < 0) { // случай когда разделителя нет
            if (size > digsAfterDot) { // проверяем длину всего числа
                isValid = false;
            }
        } else if (decInd > digsAfterDot) {// проверяем длину целой части
            isValid = false;
        } else if (size - decInd - 1 > digsBeforeDot) { // проверяем длину дробной части
            isValid = false;
        }

        if (isValid) {
            return null;
        } else if (source.equals("")) {
            return dest.subSequence(dstart, dend);
        } else {
            return "";
        }
    }
}
