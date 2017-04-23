package ast;

public class FieldOrFunctionDefinition extends ASTNode{

    private FieldDefinition fieldDef;
    private FunctionDefinition functionDef;

    public FieldOrFunctionDefinition(){
        
    }
    
    public FieldOrFunctionDefinition(FieldDefinition fieldDefinition, FunctionDefinition funtionDefinition) {
        this.fieldDef = fieldDefinition;
        this.functionDef = funtionDefinition;
    }

    public FieldDefinition getFieldDef() {
        return fieldDef;
    }

    public void setFieldDef(FieldDefinition fieldDef) {
        this.fieldDef = fieldDef;
    }

    public FunctionDefinition getFunctionDef() {
        return functionDef;
    }

    public void setFunctionDef(FunctionDefinition functionDef) {
        this.functionDef = functionDef;
    }
    
    
    
    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}