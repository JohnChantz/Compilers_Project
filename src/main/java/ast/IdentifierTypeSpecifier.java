package ast;

public class IdentifierTypeSpecifier extends TypeSpecifier{
    
    private IdentifierExpression identifier;
    
    public IdentifierTypeSpecifier(String identifier){
        this.identifier = new IdentifierExpression(identifier);
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
