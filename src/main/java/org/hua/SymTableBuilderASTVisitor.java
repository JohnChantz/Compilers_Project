package org.hua;

import java.util.ArrayDeque;
import java.util.Deque;

import org.hua.symbol.HashSymTable;
import org.hua.symbol.SymTable;
import org.hua.symbol.SymTableEntry;
import org.hua.ast.ASTUtils;
import org.hua.ast.ASTVisitor;
import org.hua.ast.ASTVisitorException;
import org.hua.ast.AssignmentStatement;
import org.hua.ast.BinaryExpression;
import org.hua.ast.BreakStatement;
import org.hua.ast.ClassDefinition;
import org.hua.ast.CompilationUnit;
import org.hua.ast.CompoundStatement;
import org.hua.ast.FunctionCallExpression;
import org.hua.ast.ContinueStatement;
import org.hua.ast.Definitions;
import org.hua.ast.DoWhileStatement;
import org.hua.ast.DotExpression;
import org.hua.ast.DotExpressionList;
import org.hua.ast.DoubleLiteralExpression;
import org.hua.ast.Expression;
import org.hua.ast.FieldDefinition;
import org.hua.ast.FieldOrFunctionDefinition;
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

/**
 * Build symbol tables for each node of the AST.
 */
public class SymTableBuilderASTVisitor implements ASTVisitor {

    private final Deque<SymTable<SymTableEntry>> symTableArray;
    private boolean loopFlag = false;       //indicates whether inside a loop

    public SymTableBuilderASTVisitor() {
        symTableArray = new ArrayDeque<SymTable<SymTableEntry>>();
    }

    @Override
    public void visit(CompilationUnit node) throws ASTVisitorException {
        pushEnvironment();
        ASTUtils.setEnv(node, symTableArray.element());
        for (Definitions def : node.getDefinitions()) {
            def.accept(this);
        }
        popEnvironment();
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
    public void visit(FunctionDefinition node) throws ASTVisitorException {
        ASTUtils.setEnv(node, symTableArray.element());
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
        ASTUtils.setEnv(node, symTableArray.element());
        node.getIdentifier().accept(this);
    }

    @Override
    public void visit(ClassDefinition node) throws ASTVisitorException {
        pushEnvironment();
        ASTUtils.setEnv(node, symTableArray.element());
        ASTUtils.setIsClass(node, true);
        node.getIdentifier().accept(this);
        for (FieldOrFunctionDefinition f : node.getFieldOrFunctionDefinitions()) {
            f.accept(this);
        }
        popEnvironment();
    }

    @Override
    public void visit(AssignmentStatement node) throws ASTVisitorException {
        ASTUtils.setEnv(node, symTableArray.element());
        node.getExpression1().accept(this);
        node.getExpression2().accept(this);
    }

    @Override
    public void visit(PrintStatement node) throws ASTVisitorException {
        ASTUtils.setEnv(node, symTableArray.element());
        node.getExpression().accept(this);
    }

    @Override
    public void visit(BinaryExpression node) throws ASTVisitorException {
        ASTUtils.setEnv(node, symTableArray.element());
        node.getExpression1().accept(this);
        node.getExpression2().accept(this);
    }

    @Override
    public void visit(UnaryExpression node) throws ASTVisitorException {
        ASTUtils.setEnv(node, symTableArray.element());
        node.getExpression().accept(this);
    }

    @Override
    public void visit(IdentifierExpression node) throws ASTVisitorException {
        //just set the environment
        ASTUtils.setEnv(node, symTableArray.element());
    }

    @Override
    public void visit(DoubleLiteralExpression node) throws ASTVisitorException {
        //just set the environment
        ASTUtils.setEnv(node, symTableArray.element());
    }

    @Override
    public void visit(IntegerLiteralExpression node) throws ASTVisitorException {
        //just set the environment
        ASTUtils.setEnv(node, symTableArray.element());
    }

    @Override
    public void visit(StringLiteralExpression node) throws ASTVisitorException {
        //just set the environment
        ASTUtils.setEnv(node, symTableArray.element());
    }

    @Override
    public void visit(ParenthesisExpression node) throws ASTVisitorException {
        ASTUtils.setEnv(node, symTableArray.element());
        node.getExpression().accept(this);
    }

    @Override
    public void visit(WhileStatement node) throws ASTVisitorException {
        ASTUtils.setEnv(node, symTableArray.element());
        this.loopFlag = true;
        node.getExpression().accept(this);
        node.getStatement().accept(this);
        this.loopFlag = false;
    }

    @Override
    public void visit(DoWhileStatement node) throws ASTVisitorException {
        ASTUtils.setEnv(node, symTableArray.element());
        this.loopFlag = true;
        node.getExpression().accept(this);
        node.getStatement().accept(this);
        this.loopFlag = false;
    }

    @Override
    public void visit(CompoundStatement node) throws ASTVisitorException {
        pushEnvironment();
        ASTUtils.setEnv(node, symTableArray.element());
        for (Statement s : node.getStatements()) {
            s.accept(this);
        }
        popEnvironment();
    }

    @Override
    public void visit(BreakStatement node) throws ASTVisitorException {
        //just set the environment
        if (this.loopFlag) {
            ASTUtils.setAllowBreak(node, loopFlag);
        }
        ASTUtils.setEnv(node, symTableArray.element());
    }

    @Override
    public void visit(ContinueStatement node) throws ASTVisitorException {
        //just set the environment
        if (this.loopFlag) {
            ASTUtils.setAllowContinue(node, loopFlag);
        }
        ASTUtils.setEnv(node, symTableArray.element());
    }

    @Override
    public void visit(IfElseStatement node) throws ASTVisitorException {
        ASTUtils.setEnv(node, symTableArray.element());
        node.getExpression().accept(this);
        node.getStatement1().accept(this);
        node.getStatement2().accept(this);
    }

    @Override
    public void visit(IfStatement node) throws ASTVisitorException {
        ASTUtils.setEnv(node, symTableArray.element());
        node.getExpression().accept(this);
        node.getStatement().accept(this);
    }

    @Override
    public void visit(ReturnStatement node) throws ASTVisitorException {
        ASTUtils.setEnv(node, symTableArray.element());
        node.getExpr().accept(this);
    }

    @Override
    public void visit(TypeSpecifierStatement node) throws ASTVisitorException {
        ASTUtils.setEnv(node, symTableArray.element());
        node.getIdentifier().accept(this);
    }

    @Override
    public void visit(PlainStatement node) throws ASTVisitorException {
        ASTUtils.setEnv(node, symTableArray.element());
        node.getExp().accept(this);
    }

    @Override
    public void visit(WriteStatement node) throws ASTVisitorException {
        ASTUtils.setEnv(node, symTableArray.element());
        node.getExpression().accept(this);
    }

    @Override
    public void visit(DotExpression node) throws ASTVisitorException {
        ASTUtils.setEnv(node, symTableArray.element());
        node.getExp().accept(this);
        node.getIdentifier().accept(this);
    }

    @Override
    public void visit(NullExpression node) throws ASTVisitorException {
        //just set the environment
        ASTUtils.setEnv(node, symTableArray.element());
    }

    @Override
    public void visit(NewExpression node) throws ASTVisitorException {
        ASTUtils.setEnv(node, symTableArray.element());
        node.getIdentifier().accept(this);
        for (Expression e : node.getList()) {
            e.accept(this);
        }
    }

    @Override
    public void visit(DotExpressionList node) throws ASTVisitorException {
        //just set the environment
        ASTUtils.setEnv(node, symTableArray.element());
        node.getExp().accept(this);
        node.getIdentifier().accept(this);
        for (Expression e : node.getList()) {
            e.accept(this);
        }

    }

    @Override
    public void visit(ParameterDeclaration node) throws ASTVisitorException {
        ASTUtils.setEnv(node, symTableArray.element());
        node.getIdentifier().accept(this);
    }

    @Override
    public void visit(ThisExpression node) throws ASTVisitorException {
        ASTUtils.setEnv(node, symTableArray.element());
    }

    @Override
    public void visit(FunctionCallExpression node) throws ASTVisitorException {
        ASTUtils.setEnv(node, symTableArray.element());
        node.getIdentifier().accept(this);
        for (Expression e : node.getExprList()) {
            e.accept(this);
        }
    }

    private void pushEnvironment() {
        SymTable<SymTableEntry> oldSymTable = symTableArray.peek();
        SymTable<SymTableEntry> newSymTable = new HashSymTable<SymTableEntry>(oldSymTable);
        this.symTableArray.push(newSymTable);
    }

    private void popEnvironment() {
        symTableArray.pop();
    }
}
