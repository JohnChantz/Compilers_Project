package org.hua.ast;

import org.objectweb.asm.Type;

public class TypeSpecifierStatement extends Statement {

    private Type type;
    private IdentifierExpression identifier;

    public TypeSpecifierStatement(Type typeSpecifier, String identifier) {
        this.type = typeSpecifier;
        this.identifier = new IdentifierExpression(identifier);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type typeSpecifier) {
        this.type = typeSpecifier;
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