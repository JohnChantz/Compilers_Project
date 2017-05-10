package org.hua.ast;

public class NullExpression extends Expression{

    public NullExpression() {
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
}
