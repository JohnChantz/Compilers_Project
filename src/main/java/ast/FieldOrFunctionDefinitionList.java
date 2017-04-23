package ast;

import java.util.ArrayList;
import java.util.List;

public class FieldOrFunctionDefinitionList extends ASTNode{
    
    private List<FieldOrFunctionDefinition> fieldOrFunctionDefinitions;
    
    public FieldOrFunctionDefinitionList(){
        this.fieldOrFunctionDefinitions = new ArrayList<FieldOrFunctionDefinition>();
    }

    public List<FieldOrFunctionDefinition> getFieldOrFunctionDefinitions() {
        return fieldOrFunctionDefinitions;
    }

    public void setFieldOrFunctionDefinitions(List<FieldOrFunctionDefinition> fieldOrFunctionDefinitions) {
        this.fieldOrFunctionDefinitions = fieldOrFunctionDefinitions;
    }
    
    public void add(FieldOrFunctionDefinition definition){
        this.fieldOrFunctionDefinitions.add(definition);
    }
    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}