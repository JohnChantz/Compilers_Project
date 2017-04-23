package ast;

import java.util.ArrayList;

public class FunctionDefinition extends ASTNode{

    private TypeSpecifier typeSpecifier;
    private IdentifierExpression identifier;
    private ArrayList<ParameterDeclaration> parameterList;
    private CompoundStatement compoundStatement;

    
    
    public FunctionDefinition(TypeSpecifier typeSpecifier,String identifier,ArrayList parameterList,CompoundStatement compStmt) {
        this.typeSpecifier = typeSpecifier;
        this.identifier = new IdentifierExpression(identifier);
        this.parameterList = parameterList;
        this.compoundStatement = compStmt;
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

    public ArrayList<ParameterDeclaration> getParameterList() {
        return parameterList;
    }

    public void setParameterList(ArrayList parameterList) {
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