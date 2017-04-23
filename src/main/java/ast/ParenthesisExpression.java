
package ast;

import ast.ASTVisitor;
import ast.ASTVisitorException;
import ast.Expression;

public class ParenthesisExpression extends Expression {

    private Expression expression;

    public ParenthesisExpression(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }

}
