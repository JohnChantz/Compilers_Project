package org.hua.ast;

import java.util.ArrayList;

public class FunctionCallExpression extends Expression{

    private ArrayList<Expression> exprList;
    private IdentifierExpression identifier;

    public FunctionCallExpression(String identifier,ArrayList<Expression> exprList) {
        this.exprList = exprList;
        this.identifier = new IdentifierExpression(identifier);
    }

    public ArrayList<Expression> getExprList() {
        return exprList;
    }

    public void setExprList(ArrayList<Expression> exprList) {
        this.exprList = exprList;
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
