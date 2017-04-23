package ast;

import java.util.ArrayList;

public class CompilationUnit extends ASTNode {

    private ArrayList<ClassDefinition> classDefinitions = null;
    private ArrayList<FunctionDefinition> functionDefinitions = null;

    public CompilationUnit() {
        classDefinitions = new ArrayList<ClassDefinition>();
        functionDefinitions = new ArrayList<FunctionDefinition>();
    }

    public CompilationUnit(ArrayList classDefinitions) {
        this.classDefinitions = classDefinitions;
    }

    public ArrayList<ClassDefinition> getClassDefinitions() {
        return classDefinitions;
    }

    public ArrayList<FunctionDefinition> getFunctionDefinitions() {
        return functionDefinitions;
    }

    public void setFunctionDefinitions(ArrayList functionDefinitions) {
        this.functionDefinitions = functionDefinitions;
    }

    public void setClassDefinitions(ArrayList classDefinitions) {
        this.classDefinitions = classDefinitions;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }

}
