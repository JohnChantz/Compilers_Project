package ast;

import ast.ASTVisitor;
import ast.ASTVisitorException;
import ast.Statement;
import ast.TypeSpecifier;

public class TypeSpecifierStmt extends Statement {

    private TypeSpecifier typeSpecifier;
    private IdentifierExpression identifier;

    public TypeSpecifierStmt(TypeSpecifier typeSpecifier, String identifier) {
        this.typeSpecifier = typeSpecifier;
        this.identifier = new IdentifierExpression(identifier);
    }

    public TypeSpecifier getTypeSpecifier() {
        return typeSpecifier;
    }

    public void setTypeSpecifier(TypeSpecifier typeSpecifier) {
        this.typeSpecifier = typeSpecifier;
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