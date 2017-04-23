package ast;

public class ContinueStmt extends Statement{
    
    public ContinueStmt(){
    }
    
    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}