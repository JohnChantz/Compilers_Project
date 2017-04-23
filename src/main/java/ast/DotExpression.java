package ast;


public class DotExpression extends Expression{

    private Expression exp;
    private String identifier;

    public DotExpression(Expression exp, String identifier) {
        this.exp = exp;
        this.identifier = identifier;
    }

    public Expression getExp() {
        return exp;
    }

    public void setExp(Expression exp) {
        this.exp = exp;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {

    }
}
