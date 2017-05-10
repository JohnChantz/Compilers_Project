package org.hua.ast;

public class NumberTypeSpecifier extends TypeSpecifier{

    public NumberTypeSpecifier() {
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}
