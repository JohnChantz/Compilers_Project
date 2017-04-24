package ast;

public class PlainStatement extends Statement {

    private Expression exp;

    public PlainStatement(Expression exp) {
        this.exp = exp;
    }

    public Expression getExp() {
        return exp;
    }

    public void setExp(Expression exp) {
        this.exp = exp;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
}
