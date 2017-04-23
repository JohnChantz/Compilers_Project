package ast;

public class ClassDefinition extends ASTNode{

    private String identifier = null;
    private FieldOrFunctionDefinitionList fieldOrFunctionDefinitions = null;
    
    public ClassDefinition(){
        
    }
    
    public ClassDefinition(String identifier){
        this.identifier = identifier;
    }

    public ClassDefinition(String identifier,FieldOrFunctionDefinitionList fieldOrFunctionDefinitions) {
        this.identifier = identifier;
        this.fieldOrFunctionDefinitions = fieldOrFunctionDefinitions;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

   

    public FieldOrFunctionDefinitionList getFieldOrFunctionDefinitions() {
        return fieldOrFunctionDefinitions;
    }

    public void setFieldOrFunctionDefinitions(FieldOrFunctionDefinitionList fieldOrFunctionDefinitions) {
        this.fieldOrFunctionDefinitions = fieldOrFunctionDefinitions;
    }
    
    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}