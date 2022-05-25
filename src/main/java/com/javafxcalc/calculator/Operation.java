package com.javafxcalc.calculator;

import org.mathIT.numbers.BigNumbers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BiFunction;

public class Operation {

    public static Operation ADD = new Operation("+", (a, b) -> a.add(b));
    public static Operation SUB = new Operation("-", (a, b) -> a.subtract(b));
    public static Operation MUL = new Operation("*", (a, b) -> a.multiply(b));
    public static Operation DIV = new Operation("/", (a, b) -> a.divide(b, 40, RoundingMode.HALF_UP)).setInputValidator((a, b) -> !(b == BigDecimal.ZERO));
    public static Operation POW = new Operation("^", (a, b) -> a.pow(b.intValue()));
    public static Operation POW2 = new Operation("^2", (a, b) -> b.pow(2), true);

    public static Operation SQRT = new Operation("sqrt", (a, b) -> BigNumbers.sqrt(b), true).setInputValidator((a, b) -> b.compareTo(BigDecimal.ONE) > 0);
    public static Operation ROOT = new Operation("root", (a, b) -> BigNumbers.root(b.intValue(), a));

    private String op;
    private BiFunction<BigDecimal, BigDecimal, BigDecimal> calc;

    private BiFunction<BigDecimal, BigDecimal, Boolean> inputValidator;

    private boolean oneOperand;

    public Operation(String op, BiFunction<BigDecimal, BigDecimal, BigDecimal> calc) {
        this(op, calc, false);
    }
    public Operation(String op, BiFunction<BigDecimal, BigDecimal, BigDecimal> calc, boolean oneOperand) {
        this.op = op;
        this.calc = calc;
        this.oneOperand = oneOperand;
    }

    public Operation setInputValidator(BiFunction<BigDecimal, BigDecimal, Boolean> inputValidator) {
        this.inputValidator = inputValidator;
        return this;
    }

    public boolean validateInput(BigDecimal a, BigDecimal b) {
        if (inputValidator == null) return true;
        return inputValidator.apply(a, b);
    }

    public String getOp() {
        return op;
    }

    public boolean isOneOperand() {
        return oneOperand;
    }

    public BigDecimal calculate(BigDecimal num1, BigDecimal num2) {
        return this.calc.apply(num1, num2);
    }
}
