package org.hua.ast;

public class StringTypeSpecifier extends TypeSpecifier{

    public StringTypeSpecifier() {
    }
    
    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}
