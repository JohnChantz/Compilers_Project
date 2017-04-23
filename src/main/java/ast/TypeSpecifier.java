package ast;

public class TypeSpecifier extends ASTNode{

    private String typeSpecifier;
    
    public TypeSpecifier(String typeSpecifier){
        this.typeSpecifier = typeSpecifier;
    }

    public String getTypeSpecifier() {
        return typeSpecifier;
    }

    public void setTypeSpecifier(String typeSpecifier) {
        this.typeSpecifier = typeSpecifier;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}