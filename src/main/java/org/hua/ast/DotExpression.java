package org.hua.ast;


public class DotExpression extends Expression{

    private Expression exp;
    private IdentifierExpression identifier;

    public DotExpression(Expression exp, String identifier) {
        this.exp = exp;
        this.identifier = new IdentifierExpression(identifier);
    }

    public Expression getExp() {
        return exp;
    }

    public void setExp(Expression exp) {
        this.exp = exp;
    }

    public IdentifierExpression getIdentifier() {
        return identifier;
    }

    public void setIdentifier(IdentifierExpression identifier) {
        this.identifier = identifier;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
}
