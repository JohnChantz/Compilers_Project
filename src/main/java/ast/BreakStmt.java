package ast;

public class BreakStmt extends Statement{

    public BreakStmt(){
        
    }
    
    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}
