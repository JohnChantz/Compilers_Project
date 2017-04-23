package ast;

import java.util.ArrayList;

public class NewExpression extends Expression{

    private String identifier;
    private ArrayList<Expression> list;

    public NewExpression(String identifier, ArrayList<Expression> list) {
        this.identifier = identifier;
        this.list = list;
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
