package ast;

public class IfElseStatement extends Statement {
    
    private Expression expression;
    private CompoundStatement statement1;
    private CompoundStatement statement2;

    public IfElseStatement(Expression expression, CompoundStatement statement1, CompoundStatement statement2) {
        this.expression = expression;
        this.statement1 = statement1;
        this.statement2 = statement2;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public CompoundStatement getStatement1() {
        return statement1;
    }

    public void setStatement1(CompoundStatement statement1) {
        this.statement1 = statement1;
    }

    public CompoundStatement getStatement2() {
        return statement2;
    }

    public void setStatement2(CompoundStatement statement2) {
        this.statement2 = statement2;
    }
    
    

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}
