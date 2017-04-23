package ast;

import java.util.ArrayList;
import java.util.List;

public class ClassDefinitionList extends ASTNode{

    private ArrayList<ClassDefinition> classDefinitions = null;
    private ArrayList<FunctionDefinition> functionDefinitions = null;

    public ClassDefinitionList(){
        this.classDefinitions = new ArrayList<ClassDefinition>();
        this.functionDefinitions = new ArrayList<FunctionDefinition>();
    }
    
    public ClassDefinitionList(ArrayList<ClassDefinition> classDefinitions, ArrayList<FunctionDefinition> functionDefinitions) {
        this.classDefinitions = classDefinitions;
        this.functionDefinitions = functionDefinitions;
    }

    public List<ClassDefinition> getClassDefinitions() {
        return classDefinitions;
    }

    public void setClassDefinitions(ArrayList<ClassDefinition> classDefinitions) {
        this.classDefinitions = classDefinitions;
    }

    public List<FunctionDefinition> getFunctionDefinitions() {
        return functionDefinitions;
    }

    public void setFunctionDefinitions(ArrayList<FunctionDefinition> functionDefinitions) {
        this.functionDefinitions = functionDefinitions;
    }
    
    
    public void addClassDefinition(ClassDefinition classDefinition){
        this.classDefinitions.add(classDefinition);
    }
    
    public void addFunctionDefinition(FunctionDefinition functionDefinition){
        this.functionDefinitions.add(functionDefinition);
    }
    
    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
    }
    
}