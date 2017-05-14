package org.hua.ast;

public enum Operator {

    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVISION("/"),
    GREATER_EQUAL(">="),
    LESS_EQUAL("<="),
    GREATER(">"),
    LESS("<"),
    EQUAL("=="),
    NOT_EQUAL("!="),
    MOD("%"),
    NOT("!"),
    AND("&&"),
    OR("||");

    private final String type;

    private Operator(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }

    public boolean isUnary() {
        return this.equals(Operator.MINUS);
    }

    public boolean isRelational() {
        return this.equals(Operator.EQUAL) || this.equals(Operator.NOT_EQUAL)
                || this.equals(Operator.GREATER) || this.equals(Operator.GREATER_EQUAL)
                || this.equals(Operator.LESS) || this.equals(Operator.LESS_EQUAL);
    }

}
