package org.hua.ast;

import org.objectweb.asm.Type;

public class FieldDefinition extends Definition{
    
    private Type typeSpecifier;
    private IdentifierExpression identifier;

    public FieldDefinition(Type typeSpecifier, String identifier) {
        this.typeSpecifier = typeSpecifier;
        this.identifier = new IdentifierExpression(identifier);
    }
    
    public FieldDefinition(){}

    public Type getTypeSpecifier() {
        return typeSpecifier;
    }

    public void setTypeSpecifier(Type typeSpecifier) {
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