package ast;

import java.util.ArrayList;
import java.util.List;

public class CompoundStatement extends Statement {

    private ArrayList<Statement> statements;

    public CompoundStatement() {
        this.statements = new ArrayList<Statement>();
    }

    public CompoundStatement(ArrayList<Statement> statements) {
        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public void setStatements(ArrayList<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }

}
