package ast;

public class FieldDefinition extends ASTNode{
    
    private TypeSpecifier typeSpecifier;
    private IdentifierExpression identifier;

    public FieldDefinition(TypeSpecifier typeSpecifier, String identifier) {
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