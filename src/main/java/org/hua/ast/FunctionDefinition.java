package org.hua.ast;

import java.util.ArrayList;
import org.objectweb.asm.Type;

public class FunctionDefinition extends Definition{

    private Type typeSpecifier;
    private IdentifierExpression identifier;
    private ArrayList<ParameterDeclaration> parameterList;
    private CompoundStatement compoundStatement;

    public FunctionDefinition(){}
    
    public FunctionDefinition(Type typeSpecifier,String identifier,ArrayList<ParameterDeclaration> parameterList,CompoundStatement compStmt) {
        this.typeSpecifier = typeSpecifier;
        this.identifier = new IdentifierExpression(identifier);
        this.parameterList = parameterList;
        this.compoundStatement = compStmt;
    }

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

    public ArrayList<ParameterDeclaration> getParameterList() {
        return parameterList;
    }

    public void setParameterList(ArrayList<ParameterDeclaration> parameterList) {
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