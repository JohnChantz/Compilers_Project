package org.hua;

import org.hua.ast.*;
import java.util.Iterator;
import org.apache.commons.lang3.StringEscapeUtils;

public class PrintASTVisitor implements ASTVisitor {

    @Override
    public void visit(AssignmentStatement node) throws ASTVisitorException {
        node.getExpression1().accept(this);
        System.out.print(" = ");
        node.getExpression2().accept(this);
        System.out.println(";");
    }

    @Override
    public void visit(BinaryExpression node) throws ASTVisitorException {
        node.getExpression1().accept(this);
        System.out.print(" ");
        System.out.print(node.getOperator());
        System.out.print(" ");
        node.getExpression2().accept(this);
    }

    @Override
    public void visit(UnaryExpression node) throws ASTVisitorException {
        System.out.print(node.getOperator());
        System.out.print(" ");
        node.getExpression().accept(this);
    }

    @Override
    public void visit(IdentifierExpression node) throws ASTVisitorException {
        System.out.print(node.getIdentifier());
    }

    @Override
    public void visit(DoubleLiteralExpression node) throws ASTVisitorException {
        System.out.print(node.getLiteral());
    }

    @Override
    public void visit(IntegerLiteralExpression node) throws ASTVisitorException {
        System.out.print(node.getLiteral());
    }

    @Override
    public void visit(StringLiteralExpression node) throws ASTVisitorException {
        System.out.print("\"");
        System.out.print(StringEscapeUtils.escapeJava(node.getLiteral()));
        System.out.print("\"");
    }

    @Override
    public void visit(ParenthesisExpression node) throws ASTVisitorException {
        System.out.print("(");
        node.getExpression().accept(this);
        System.out.print(")");
    }

    @Override
    public void visit(CompoundStatement node) throws ASTVisitorException {
        System.out.println("{");
        for (Statement st : node.getStatements()) {
            st.accept(this);
        }
        System.out.println("}");
    }

    @Override
    public void visit(WhileStatement node) throws ASTVisitorException {
        System.out.print("while(");
        node.getExpression().accept(this);
        System.out.print(")");
        node.getStatement().accept(this);
    }

    @Override
    public void visit(IfStatement node) throws ASTVisitorException {
        System.out.print("if(");
        node.getExpression().accept(this);
        System.out.print(")");
        node.getStatement().accept(this);
    }

    @Override
    public void visit(IfElseStatement node) throws ASTVisitorException {
        System.out.print("if(");
        node.getExpression().accept(this);
        System.out.print(")");
        node.getStatement1();
        System.out.print("else");
        node.getStatement2().accept(this);
    }

    @Override
    public void visit(CompilationUnit node) throws ASTVisitorException {
        for(Definition def : node.getDefinitions()){
            def.accept(this);
        }
    }

    @Override
    public void visit(ContinueStatement node) throws ASTVisitorException {
        System.out.println("continue;");
    }

    @Override
    public void visit(BreakStatement node) throws ASTVisitorException {
        System.out.println("break;");
    }

    @Override
    public void visit(ReturnStatement node) throws ASTVisitorException {
        System.out.print("return ");
        node.getExpr().accept(this);
        System.out.println(";");
    }

    @Override
    public void visit(TypeSpecifierStatement node) throws ASTVisitorException {
        node.getTypeSpecifier().accept(this);
        node.getIdentifier().accept(this);
        System.out.println(";");
    }

    @Override
    public void visit(FunctionDefinition node) throws ASTVisitorException {
        node.getTypeSpecifier().accept(this);
        node.getIdentifier().accept(this);
        System.out.print("(");
        if(node.getParameterList().size() == 1){
            for (ParameterDeclaration p : node.getParameterList()) {
                p.accept(this);
            }
        }else{
                Iterator it = node.getParameterList().iterator();
                int i=0;
                while(it.hasNext()){
                    node.getParameterList().get(i).accept(this);
                    i++;
                    if(it.hasNext()){
                        System.out.print(",");
                    }
                    it.next();
                }
            }
        System.out.print(")");
        node.getCompoundStatement().accept(this);
    }

    @Override
    public void visit(FieldOrFunctionDefinition node) throws ASTVisitorException {
        if(node.getFieldDef() == null)
            node.getFunctionDef().accept(this);
        if(node.getFunctionDef() == null)
            node.getFieldDef().accept(this);
    }

    @Override
    public void visit(FieldDefinition node) throws ASTVisitorException {
        node.getTypeSpecifier().accept(this);
        node.getIdentifier().accept(this);
        System.out.println(";");
    }

    @Override
    public void visit(ClassDefinition node) throws ASTVisitorException {
        System.out.print("class ");
        node.getIdentifier().accept(this);
        System.out.println("{");
        for (FieldOrFunctionDefinition f : node.getFieldOrFunctionDefinitions()) {
            f.accept(this);
        }
        System.out.println("}");
    }

    @Override
    public void visit(PlainStatement node) throws ASTVisitorException {
        node.getExp().accept(this);
        System.out.println(";");
    }

    @Override
    public void visit(DoWhileStatement node) throws ASTVisitorException {
        System.out.print("do");
        node.getStatement().accept(this);
        System.out.print("while(");
        node.getExpression().accept(this);
        System.out.println(");");
    }
    
    @Override
    public void visit(Definitions node) throws ASTVisitorException {
        if(node.getClassDefinition() == null)
            node.getFunctionDefinition().accept(this);
        if(node.getFunctionDefinition() == null)
            node.getClassDefinition().accept(this);
    }

    @Override
    public void visit(IdentifierTypeSpecifier node) throws ASTVisitorException {
        node.getIdentifier().accept(this);
        System.out.print(" ");
    }

    @Override
    public void visit(VoidTypeSpecifier node) throws ASTVisitorException {
        System.out.print("void ");
    }

    @Override
    public void visit(StringTypeSpecifier node) throws ASTVisitorException {
        System.out.print("string ");
    }

    @Override
    public void visit(NumberTypeSpecifier node) throws ASTVisitorException {
        System.out.print("number ");
    }

     @Override
    public void visit(WriteStatement node) throws ASTVisitorException {
        System.out.print("write(");
        node.getExpression().accept(this);
        System.out.println(");");
    }

    @Override
    public void visit(DotExpression node) throws ASTVisitorException {
        node.getExp().accept(this);
        System.out.print(".");
        node.getIdentifier().accept(this);
    }

    @Override
    public void visit(NullExpression node) throws ASTVisitorException {
        System.out.print("null");
    }

    @Override
    public void visit(NewExpression node) throws ASTVisitorException {
        System.out.print("new ");
        node.getIdentifier().accept(this);
        System.out.print("(");
        
        for(Expression exp : node.getList()){
            exp.accept(this);
        }        
        System.out.print(")");
    }

    @Override
    public void visit(DotExpressionList node) throws ASTVisitorException {
        node.getExp().accept(this);
        System.out.print(".");
        node.getIdentifier().accept(this);
        System.out.print("(");
        for(Expression exp : node.getList()){
            exp.accept(this);
        }
        System.out.print(")");
    }

    @Override
    public void visit(ParameterDeclaration node) throws ASTVisitorException {
        node.getTypeSpecifier().accept(this);
        node.getIdentifier().accept(this);
    }

    @Override
    public void visit(VarDeclarationStatement node) throws ASTVisitorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(PrintStatement node) throws ASTVisitorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
