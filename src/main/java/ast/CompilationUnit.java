package ast;

import java.util.ArrayList;

public class CompilationUnit extends ASTNode {

    private ArrayList<Definition> definitions = null;

    public CompilationUnit() {
        definitions = new ArrayList<Definition>();
    }

    public CompilationUnit(ArrayList<Definition> definitions) {
        this.definitions = definitions;
    }

    public ArrayList<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(ArrayList<Definition> definitions) {
        this.definitions = definitions;
    }
    
    
    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }

}
