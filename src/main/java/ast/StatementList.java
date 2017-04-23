package ast;

import ast.ASTVisitor;
import ast.ASTVisitorException;
import ast.Statement;

import java.util.ArrayList;
import java.util.List;

public class StatementList extends Statement {
    
    private List<Statement> statements;

    public StatementList(List<Statement> statements) {
        this.statements = statements;
    }
    
    public StatementList(){
        statements = new ArrayList<Statement>();
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }
    
    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
    }
    
}