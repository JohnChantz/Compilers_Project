package ast;

public class TypeSpecifierStmt extends Statement{

    private TypeSpecifier typeSpecifier;
    private String identifier;
    private String symbol = ";";
    
    public TypeSpecifierStmt(TypeSpecifier typeSpecifier,String identifier){
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}