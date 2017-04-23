package ast;

import java.util.ArrayList;
import java.util.List;

public class ExpressionList extends Expression{

    private List<Expression> expressionList;
    
    public ExpressionList(){
        this.expressionList = new ArrayList<Expression>();
    }

    public List<Expression> getExpressionList() {
        return expressionList;
    }

    public void setExpressionList(List<Expression> expressionList) {
        this.expressionList = expressionList;
    }
    
    public void add(Expression exp){
        this.expressionList.add(exp);
    }
    
    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}