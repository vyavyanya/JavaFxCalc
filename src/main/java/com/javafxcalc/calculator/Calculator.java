package com.javafxcalc.calculator;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Consumer;

@Component
public class Calculator {

    private Consumer<String> onResult;
    public void setOnResult(Consumer<String> onResult) {
        this.onResult = onResult;
    }

    private BigDecimal curNum = BigDecimal.ZERO;

    private BigDecimal lastResult = null;

    private Operation operation;

    private boolean reset;

    public String getCurrentNumber() {
        return bigDecimalToString(curNum);
    }

    private static String bigDecimalToString(BigDecimal d) {
        return d.setScale(20, RoundingMode.HALF_UP).stripTrailingZeros().toString();
    }

    public void inputDigit(int d) {
        if (d < 0 || d > 9) throw new IllegalArgumentException();

        if (reset) {
            lastResult = null;
            curNum = BigDecimal.ZERO;
            reset = false;
        }

        if (operation != null && lastResult == null) {
            lastResult = curNum;
            curNum = BigDecimal.ZERO;
        }

        curNum = curNum.multiply(BigDecimal.valueOf(10)).add(BigDecimal.valueOf(d));
    }

    public void inputOperation(Operation operation) {
        if (!operation.validateInput(lastResult, curNum)) return;

        if (operation.isOneOperand()) {
            inputResult();
            BigDecimal prevCurNum = curNum;
            curNum = operation.calculate(lastResult, curNum);
            if (onResult != null) {
                onResult.accept("" + bigDecimalToString(prevCurNum) + " " + operation.getOp() + " = " + bigDecimalToString(curNum));
            }
            return;
        }

        if (lastResult != null && this.operation != null) {
            inputResult();
        }

        this.operation = operation;

        if (lastResult != null) {
            inputResult();
        }
        reset = false;
    }

    public void inputResult() {
        if (operation == null || lastResult == null) return;
        if (!operation.validateInput(lastResult, curNum)) return;
        BigDecimal prevCurNum = curNum;
        curNum = operation.calculate(lastResult, curNum);
        if (onResult != null) {
            onResult.accept("" + bigDecimalToString(lastResult) + " " + operation.getOp() + " " + bigDecimalToString(prevCurNum) + " = " + bigDecimalToString(curNum));
        }
        lastResult = null;
        operation = null;
        reset = true;
    }

    public void inputClear() {
        lastResult = null;
        curNum = BigDecimal.ZERO;
        operation = null;
        reset = false;
    }

}
