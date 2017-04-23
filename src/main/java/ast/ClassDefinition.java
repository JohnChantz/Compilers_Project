package ast;

import java.util.ArrayList;

public class ClassDefinition extends ASTNode{

    private IdentifierExpression identifier = null;
    private ArrayList<FieldOrFunctionDefinition> definitions = null;
    
    public ClassDefinition(){
        
    }
    
    public ClassDefinition(String identifier){
        this.identifier = new IdentifierExpression(identifier);
    }

    public ClassDefinition(String identifier,ArrayList fieldOrFunctionDefinitions) {
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

    public void setFieldOrFunctionDefinitions(ArrayList fieldOrFunctionDefinitions) {
        this.definitions = fieldOrFunctionDefinitions;
    }
    
    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}