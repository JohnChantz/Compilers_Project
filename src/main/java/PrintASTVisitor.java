import ast.ASTVisitor;
import ast.ASTVisitorException;
import ast.AssignmentStatement;
import ast.BinaryExpression;
import ast.BreakStmt;
import ast.ClassDefinition;
import ast.CompoundStatement;
import ast.DoWhileStatement;
import ast.DoubleLiteralExpression;
import ast.IdentifierExpression;
import ast.IfElseStatement;
import ast.IfStatement;
import ast.IntegerLiteralExpression;
import ast.ParenthesisExpression;
import ast.Statement;
import ast.StringLiteralExpression;
import ast.UnaryExpression;
import ast.WhileStatement;
import ast.CompilationUnit;
import ast.ContinueStmt;
import ast.ExpressionList;
import ast.FieldDefinition;
import ast.FieldOrFunctionDefinition;
import ast.FieldOrFunctionDefinitionList;
import ast.FunctionDefinition;
import ast.ReturnStmt;
import ast.TypeSpecifier;
import ast.TypeSpecifierStmt;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

public class PrintASTVisitor implements ASTVisitor {

    @Override
    public void visit(AssignmentStatement node) throws ASTVisitorException {
        System.out.print(node.getIdentifier());
        System.out.print(" = ");
        node.getExpression().accept(this);
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
        System.out.print("( ");
        node.getExpression().accept(this);
        System.out.print(" )");
    }

    @Override
    public void visit(CompoundStatement node) throws ASTVisitorException {
        System.out.println(" { ");
        for(Statement st: node.getStatements()) { 
            st.accept(this);
        }
        System.out.println(" } ");
    }
    
    @Override
    public void visit(WhileStatement node) throws ASTVisitorException{
        System.out.print("while(");
        node.getExpression().accept(this);
        System.out.print(")");
        node.getStatement().accept(this);
    }
    
    @Override
    public void visit(DoWhileStatement node) throws ASTVisitorException{
        System.out.print("do");
        node.getStatement().accept(this);
        System.out.print("while(");
        node.getExpression().accept(this);
        System.out.println(")");
    }
    
    @Override
    public void visit(IfStatement node) throws ASTVisitorException{
        System.out.print("if(");
        node.getExpression().accept(this);
        System.out.print(")");
        node.getStatement().accept(this);
    }
    
    @Override
    public void visit(IfElseStatement node) throws ASTVisitorException{
        System.out.print("if(");
        node.getExpression().accept(this);
        System.out.print(")");
        node.getStatement1();
        System.out.print("else");
        node.getStatement2().accept(this);
    }

    @Override
    public void visit(CompilationUnit node) throws ASTVisitorException{
        for(ClassDefinition classDef : node.getClassDefinitions().getClassDefinitions()){
            classDef.accept(this);
        }
    }
    

    @Override
    public void visit(ExpressionList node) throws ASTVisitorException {
        //to do
    }

    @Override
    public void visit(ContinueStmt node) throws ASTVisitorException {
        System.out.println("continue;");
    }

    @Override
    public void visit(BreakStmt node) throws ASTVisitorException {
        System.out.println("break;");
    }

    @Override
    public void visit(ReturnStmt node) throws ASTVisitorException {
        System.out.print("return ");
        node.getExpr().accept(this);
        System.out.print(";");
    }

    @Override
    public void visit(TypeSpecifierStmt node) throws ASTVisitorException {
        System.out.print(node.getTypeSpecifier().getTypeSpecifier() + " ");
        System.out.print(node.getIdentifier() + node.getSymbol());
    }

    @Override
    public void visit(TypeSpecifier node) throws ASTVisitorException {
        System.out.print(node.getTypeSpecifier() + " ");
    }

    @Override
    public void visit(FunctionDefinition node) throws ASTVisitorException {
        System.out.print(node.getTypeSpecifier().getTypeSpecifier() + " ");
        System.out.print(node.getIdentifier().getIdentifier());
        System.out.print("(");
        List list = node.getParameterList().getParameterList();
        
    }

    @Override
    public void visit(FieldOrFunctionDefinition node) throws ASTVisitorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(FieldOrFunctionDefinitionList node) throws ASTVisitorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(FieldDefinition node) throws ASTVisitorException {
        System.out.print(node.getTypeSpecifier().getTypeSpecifier() + " ");
        System.out.print(node.getIdentifier() + ";");
    }

    @Override
    public void visit(ClassDefinition node) throws ASTVisitorException {
        System.out.println("class " + node.getIdentifier() + "{" );
        node.getFieldOrFunctionDefinitions().accept(this);
        System.out.print("}");
    }

}
