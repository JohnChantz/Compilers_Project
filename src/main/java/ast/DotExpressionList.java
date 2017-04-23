package ast;

import java.util.ArrayList;

public class DotExpressionList extends Expression{

    private Expression exp;
    private String identifier;
    private ArrayList<Expression> list;

    public DotExpressionList(Expression exp, String identifier, ArrayList<Expression> list) {
        this.exp = exp;
        this.identifier = identifier;
        this.list = list;
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

    public ArrayList<Expression> getList() {
        return list;
    }

    public void setList(ArrayList<Expression> list) {
        this.list = list;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {

    }
}
