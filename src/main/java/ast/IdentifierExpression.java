package ast;

import ast.ASTVisitor;
import ast.ASTVisitorException;
import ast.Expression;

public class IdentifierExpression extends Expression {

    private String identifier;

    public IdentifierExpression(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }

}
