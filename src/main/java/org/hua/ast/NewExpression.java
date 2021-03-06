package org.hua.ast;

import java.util.ArrayList;

public class NewExpression extends Expression{

    private IdentifierExpression identifier;
    private ArrayList<Expression> list;

    public NewExpression(String identifier, ArrayList<Expression> list) {
        this.identifier = new IdentifierExpression(identifier);
        this.list = list;
    }

    public IdentifierExpression getIdentifier() {
        return identifier;
    }

    public void setIdentifier(IdentifierExpression identifier) {
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
        visitor.visit(this);
    }
}
