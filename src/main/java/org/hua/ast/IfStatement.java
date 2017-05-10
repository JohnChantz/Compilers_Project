package org.hua.ast;

public class IfStatement extends Statement {
    
    private Expression expression;
    private CompoundStatement statement;

    public IfStatement(Expression expression, CompoundStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public CompoundStatement getStatement() {
        return statement;
    }

    public void setStatement(CompoundStatement statement) {
        this.statement = statement;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
    
    
}
