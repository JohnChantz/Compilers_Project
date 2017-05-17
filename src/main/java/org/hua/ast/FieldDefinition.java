package org.hua.ast;

import org.objectweb.asm.Type;

public class FieldDefinition extends Definition{
    
    private Type type;
    private IdentifierExpression identifier;

    public FieldDefinition(Type typeSpecifier, String identifier) {
        this.type = typeSpecifier;
        this.identifier = new IdentifierExpression(identifier);
    }
    
    public FieldDefinition(){}

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