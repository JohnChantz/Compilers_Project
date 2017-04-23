package ast;

public class FunctionDefinition extends ASTNode{

    private TypeSpecifier typeSpecifier;
    private String identifier;
    private ParameterList parameterList;
    private CompoundStatement compoundStatement;

    
    
    public FunctionDefinition(TypeSpecifier typeSpecifier,String identifier,ParameterList parameterList,CompoundStatement compStmt) {
        this.typeSpecifier = typeSpecifier;
        this.identifier = identifier;
        this.parameterList = parameterList;
        this.compoundStatement = compStmt;
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

    public ParameterList getParameterList() {
        return parameterList;
    }

    public void setParameterList(ParameterList parameterList) {
        this.parameterList = parameterList;
    }

    public CompoundStatement getCompoundStatement() {
        return compoundStatement;
    }

    public void setCompoundStatement(CompoundStatement compoundStatement) {
        this.compoundStatement = compoundStatement;
    }
    
    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}