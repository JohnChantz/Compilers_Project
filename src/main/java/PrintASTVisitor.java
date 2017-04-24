import ast.*;
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
        System.out.print("( ");
        node.getExpression().accept(this);
        System.out.print(" )");
    }

    @Override
    public void visit(CompoundStatement node) throws ASTVisitorException {
        System.out.println(" { ");
        for (Statement st : node.getStatements()) {
            st.accept(this);
        }
        System.out.println(" } ");
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
    public void visit(BreakStmt node) throws ASTVisitorException {
        System.out.println("break;");
    }

    @Override
    public void visit(ReturnStatement node) throws ASTVisitorException {
        System.out.print("return ");
        node.getExpr().accept(this);
        System.out.print(";");
    }

    @Override
    public void visit(TypeSpecifierStatement node) throws ASTVisitorException {
        node.getTypeSpecifier().accept(this);
        node.getIdentifier().accept(this);
        System.out.print(";");
    }

    @Override
    public void visit(TypeSpecifier node) throws ASTVisitorException {
        System.out.print(node.getTypeSpecifier());
    }

    @Override
    public void visit(FunctionDefinition node) throws ASTVisitorException {
        node.getTypeSpecifier().accept(this);
        node.getIdentifier().accept(this);
        System.out.print("(");
        for (ParameterDeclaration p : node.getParameterList()) {
            p.accept(this);
            System.out.print(",");
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
    }

    @Override
    public void visit(ClassDefinition node) throws ASTVisitorException {
        System.out.println("class ");
        node.getIdentifier().accept(this);
        System.out.println("{");
        for (FieldOrFunctionDefinition f : node.getFieldOrFunctionDefinitions()) {
            f.accept(this);
        }
        System.out.print("}");
    }

    @Override
    public void visit(PlainStatement node) throws ASTVisitorException {
        node.getExp().accept(this);
        System.out.print(";");
    }

    @Override
    public void visit(DoWhileStatement node) throws ASTVisitorException {
        System.out.print("do");
        node.getStatement().accept(this);
        System.out.print("while(");
        node.getExpression().accept(this);
        System.out.println(")");
    }

}
