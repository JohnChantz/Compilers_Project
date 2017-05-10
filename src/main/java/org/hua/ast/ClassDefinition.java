package org.hua.ast;

import java.util.ArrayList;

public class ClassDefinition extends Definition{

    private IdentifierExpression identifier = null;
    private ArrayList<FieldOrFunctionDefinition> definitions = null;
    
    public ClassDefinition(){
        
    }
    
    public ClassDefinition(String identifier){
        this.identifier = new IdentifierExpression(identifier);
    }

    public ClassDefinition(String identifier,ArrayList<FieldOrFunctionDefinition> fieldOrFunctionDefinitions) {
        this.identifier = new IdentifierExpression(identifier);
        this.definitions = fieldOrFunctionDefinitions;
    }

    public IdentifierExpression getIdentifier() {
        return identifier;
    }

    public void setIdentifier(IdentifierExpression identifier) {
        this.identifier = identifier;
    }

   

    public ArrayList<FieldOrFunctionDefinition> getFieldOrFunctionDefinitions() {
        return definitions;
    }

    public void setFieldOrFunctionDefinitions(ArrayList<FieldOrFunctionDefinition> fieldOrFunctionDefinitions) {
        this.definitions = fieldOrFunctionDefinitions;
    }
    
    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}