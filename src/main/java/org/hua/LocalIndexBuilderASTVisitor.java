package org.hua;

import java.util.ArrayDeque;
import java.util.Deque;

import org.hua.ast.ASTUtils;
import org.hua.ast.ASTVisitor;
import org.hua.ast.ASTVisitorException;
import org.hua.ast.AssignmentStatement;
import org.hua.ast.BinaryExpression;
import org.hua.ast.BreakStatement;
import org.hua.ast.ClassDefinition;
import org.hua.ast.CompilationUnit;
import org.hua.ast.CompoundStatement;
import org.hua.ast.ContinueStatement;
import org.hua.ast.Definitions;
import org.hua.ast.DoWhileStatement;
import org.hua.ast.DotExpression;
import org.hua.ast.DotExpressionList;
import org.hua.ast.DoubleLiteralExpression;
import org.hua.ast.Expression;
import org.hua.ast.FieldDefinition;
import org.hua.ast.FieldOrFunctionDefinition;
import org.hua.ast.FunctionCallExpression;
import org.hua.ast.FunctionDefinition;
import org.hua.ast.IdentifierExpression;
import org.hua.ast.IfElseStatement;
import org.hua.ast.IfStatement;
import org.hua.ast.IntegerLiteralExpression;
import org.hua.ast.NewExpression;
import org.hua.ast.NullExpression;
import org.hua.ast.ParameterDeclaration;
import org.hua.ast.ParenthesisExpression;
import org.hua.ast.PlainStatement;
import org.hua.ast.PrintStatement;
import org.hua.ast.ReturnStatement;
import org.hua.ast.Statement;
import org.hua.ast.StringLiteralExpression;
import org.hua.ast.ThisExpression;
import org.hua.ast.TypeSpecifierStatement;
import org.hua.ast.UnaryExpression;
import org.hua.ast.WhileStatement;
import org.hua.ast.WriteStatement;
import org.hua.symbol.LocalIndexPool;

/**
 * Build LocalIndexPool for each node of the AST.
 */
public class LocalIndexBuilderASTVisitor implements ASTVisitor {

    private final Deque<LocalIndexPool> env;

    public LocalIndexBuilderASTVisitor() {
        env = new ArrayDeque<LocalIndexPool>();
    }

    @Override
    public void visit(CompilationUnit node) throws ASTVisitorException {
        env.push(new LocalIndexPool());
        ASTUtils.setLocalIndexPool(node, env.element());
        for (Definitions d : node.getDefinitions()) {
            d.accept(this);
        }
        env.pop();
    }

    @Override
    public void visit(AssignmentStatement node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
        node.getExpression1().accept(this);
        node.getExpression2().accept(this);
    }

    @Override
    public void visit(PrintStatement node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
        node.getExpression().accept(this);
    }

    @Override
    public void visit(BinaryExpression node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
        node.getExpression1().accept(this);
        node.getExpression2().accept(this);
    }

    @Override
    public void visit(UnaryExpression node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
        node.getExpression().accept(this);
    }

    @Override
    public void visit(IdentifierExpression node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
    }

    @Override
    public void visit(DoubleLiteralExpression node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
    }

    @Override
    public void visit(IntegerLiteralExpression node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
    }

    @Override
    public void visit(StringLiteralExpression node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
    }

    @Override
    public void visit(ParenthesisExpression node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
        node.getExpression().accept(this);
    }

    @Override
    public void visit(WhileStatement node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
        node.getExpression().accept(this);
        node.getStatement().accept(this);
    }

    @Override
    public void visit(DoWhileStatement node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
        node.getExpression().accept(this);
        node.getStatement().accept(this);
    }

    @Override
    public void visit(CompoundStatement node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
        for (Statement s : node.getStatements()) {
            s.accept(this);
        }
    }

    @Override
    public void visit(BreakStatement node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
    }

    @Override
    public void visit(ContinueStatement node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
    }

    @Override
    public void visit(IfElseStatement node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
        node.getExpression().accept(this);
        node.getStatement1().accept(this);
        node.getStatement2().accept(this);
    }

    @Override
    public void visit(IfStatement node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
        node.getExpression().accept(this);
        node.getStatement().accept(this);
    }

    @Override
    public void visit(ReturnStatement node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
        node.getExpr().accept(this);
    }

    @Override
    public void visit(TypeSpecifierStatement node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
    }

    @Override
    public void visit(FunctionDefinition node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
        ASTUtils.setIsFunction(node, true);
        node.getIdentifier().accept(this);
        for (ParameterDeclaration p : node.getParameterList()) {
            p.accept(this);
        }
        node.getCompoundStatement().accept(this);
    }

    @Override
    public void visit(FieldOrFunctionDefinition node) throws ASTVisitorException {
        if (node.getFieldDef() == null) {
            node.getFunctionDef().accept(this);
        }
        if (node.getFunctionDef() == null) {
            node.getFieldDef().accept(this);
        }
    }

    @Override
    public void visit(FieldDefinition node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
        node.getIdentifier().accept(this);
    }

    @Override
    public void visit(ClassDefinition node) throws ASTVisitorException {
        env.push(new LocalIndexPool());
        ASTUtils.setLocalIndexPool(node, env.element());
        ASTUtils.setIsClass(node, true);
        node.getIdentifier().accept(this);
        for (FieldOrFunctionDefinition f : node.getFieldOrFunctionDefinitions()) {
            f.accept(this);
        }
        env.pop();
    }

    @Override
    public void visit(PlainStatement node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
        node.getExp().accept(this);
    }

    @Override
    public void visit(Definitions node) throws ASTVisitorException {
        if (node.getClassDefinition() == null) {
            node.getFunctionDefinition().accept(this);
        }
        if (node.getFunctionDefinition() == null) {
            node.getClassDefinition().accept(this);
        }
    }

    @Override
    public void visit(WriteStatement node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
        node.getExpression().accept(this);
    }

    @Override
    public void visit(DotExpression node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
        node.getExp().accept(this);
        node.getIdentifier().accept(this);
    }

    @Override
    public void visit(NullExpression node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
    }

    @Override
    public void visit(NewExpression node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
        node.getIdentifier().accept(this);
        for (Expression e : node.getList()) {
            e.accept(this);
        }
    }

    @Override
    public void visit(DotExpressionList node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
        node.getExp().accept(this);
        node.getIdentifier().accept(this);
        for (Expression e : node.getList()) {
            e.accept(this);
        }
    }

    @Override
    public void visit(ParameterDeclaration node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
        node.getIdentifier().accept(this);
    }

    @Override
    public void visit(ThisExpression node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
    }

    @Override
    public void visit(FunctionCallExpression node) throws ASTVisitorException {
        ASTUtils.setLocalIndexPool(node, env.element());
        node.getIdentifier().accept(this);
        for (Expression e : node.getExprList()) {
            e.accept(this);
        }
    }

}
