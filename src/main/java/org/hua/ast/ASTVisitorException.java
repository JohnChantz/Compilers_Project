package org.hua.ast;

public class ASTVisitorException extends Exception {

    private static final long serialVersionUID = 1L;

    public ASTVisitorException() {
        super();
    }

    public ASTVisitorException(String msg) {
        super(msg);
    }

    public ASTVisitorException(String msg, Throwable t) {
        super(msg, t);
    }

    public ASTVisitorException(Throwable t) {
        super(t);
    }

}
