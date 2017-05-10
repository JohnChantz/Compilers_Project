package org.hua.ast;

import java.util.ArrayList;

public class ConditionExpression extends Expression{

    private ArrayList<Expression> exprList;
    private String identifier;

    public ConditionExpression(String identifier,ArrayList<Expression> exprList) {
        this.exprList = exprList;
        this.identifier = identifier;
    }

    public ArrayList<Expression> getExprList() {
        return exprList;
    }

    public void setExprList(ArrayList<Expression> exprList) {
        this.exprList = exprList;
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
