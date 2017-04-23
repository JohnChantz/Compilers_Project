package ast;

public class CompilationUnit extends ASTNode {

    private ClassDefinitionList classDefinitions = null;
    private FunctionDefinitionList functionDefinitions = null;

    public CompilationUnit() {
        classDefinitions = new ClassDefinitionList();
        functionDefinitions = new FunctionDefinitionList();
    }

    public CompilationUnit(ClassDefinitionList classDefinitions) {
        this.classDefinitions = classDefinitions;
    }

    public CompilationUnit(FunctionDefinitionList functionDefinitions) {
        this.functionDefinitions = functionDefinitions;
    }

    public ClassDefinitionList getClassDefinitions() {
        return classDefinitions;
    }

    public FunctionDefinitionList getFunctionDefinitions() {
        return functionDefinitions;
    }

    public void setFunctionDefinitions(FunctionDefinitionList functionDefinitions) {
        this.functionDefinitions = functionDefinitions;
    }

    public void setClassDefinitions(ClassDefinitionList classDefinitions) {
        this.classDefinitions = classDefinitions;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }

}
