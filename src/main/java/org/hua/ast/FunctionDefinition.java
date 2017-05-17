package org.hua.ast;

import java.util.ArrayList;
import org.objectweb.asm.Type;

public class FunctionDefinition extends Definition{

    private Type type;
    private IdentifierExpression identifier;
    private ArrayList<ParameterDeclaration> parameterList;
    private CompoundStatement compoundStatement;

    public FunctionDefinition(){}
    
    public FunctionDefinition(Type typeSpecifier,String identifier,ArrayList<ParameterDeclaration> parameterList,CompoundStatement compStmt) {
        this.type = typeSpecifier;
        this.identifier = new IdentifierExpression(identifier);
        this.parameterList = parameterList;
        this.compoundStatement = compStmt;
    }

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