package ast;

import java.util.ArrayList;
import java.util.List;

public class FunctionDefinitionList extends ASTNode {

    private ArrayList<FunctionDefinition> functionDefinitions;

    public FunctionDefinitionList(ArrayList<FunctionDefinition> functionDefinitions) {
        this.functionDefinitions = functionDefinitions;
    }

    public FunctionDefinitionList() {
        this.functionDefinitions = new ArrayList<FunctionDefinition>();
    }

    public List<FunctionDefinition> getFunctionDefinitions() {
        return functionDefinitions;
    }

    public void setFunctionDefinitions(ArrayList<FunctionDefinition> functionDefinitions) {
        this.functionDefinitions = functionDefinitions;
    }

    public void add(FunctionDefinition functionDefinition) {
        this.functionDefinitions.add(functionDefinition);
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
    }

}