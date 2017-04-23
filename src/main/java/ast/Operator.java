package ast;

public enum Operator {

    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVISION("/"),
    GREATER_EQ(">="),
    LESS_EQ("<="),
    GREATER(">"),
    LESS("<"),
    EQUAL("=="),
    NOT_EQUAL("!="),
    MOD("%"),
    NOT("!"),
    AND("&&"),
    OR("||");

    private String type;

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

}
