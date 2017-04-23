package ast;

public class FieldDefinition extends ASTNode{
    
    private TypeSpecifier typeSpecifier;
    private String identifier;

    public FieldDefinition(TypeSpecifier typeSpecifier, String identifier) {
        this.typeSpecifier = typeSpecifier;
        this.identifier = identifier;
    }

    public TypeSpecifier getTypeSpecifier() {
        return typeSpecifier;
    }

    public void setTypeSpecifier(TypeSpecifier typeSpecifier) {
        this.typeSpecifier = typeSpecifier;
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