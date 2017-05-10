package org.hua.ast;

public class VoidTypeSpecifier extends TypeSpecifier{

    public VoidTypeSpecifier() {
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}
