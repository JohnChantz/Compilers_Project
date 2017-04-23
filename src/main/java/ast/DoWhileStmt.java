package ast;

public class DoWhileStmt extends Statement {
    
    private Expression expression;
    private CompoundStatement compoundStatement;

    public DoWhileStmt(Expression expression, CompoundStatement statement) {
        this.expression = expression;
        this.compoundStatement = statement;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public Statement getStatement() {
        return compoundStatement;
    }

    public void setStatement(CompoundStatement statement) {
        this.compoundStatement = statement;
    }
    
    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
    
    
}
