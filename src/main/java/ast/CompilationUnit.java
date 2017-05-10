package ast;

import java.util.ArrayList;

public class CompilationUnit extends ASTNode {

    private ArrayList<Definitions> definitions = null;

    public CompilationUnit(){}
    
    public CompilationUnit(ArrayList<Definitions> definitions) {
        this.definitions = definitions;
    }
    
    public ArrayList<Definitions> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(ArrayList<Definitions> definitions) {
        this.definitions = definitions;
    }
   
    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }

}
