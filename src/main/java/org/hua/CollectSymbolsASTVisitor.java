package org.hua;

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
import org.hua.types.TypeUtils;
import org.objectweb.asm.Type;

/**
 * Collect all symbols such as variables, methods, etc in symbol table.
 */
public class CollectSymbolsASTVisitor implements ASTVisitor {

    public CollectSymbolsASTVisitor() {
    }

    @Override
    public void visit(CompilationUnit node) throws ASTVisitorException {
        for (Definitions definitions : node.getDefinitions()) {
            definitions.accept(this);
        }
        SymTable<SymTableEntry> symTable = ASTUtils.getSafeEnv(node);
        if (symTable.lookup("main") == null) {
            ASTUtils.error(node, "Main function has not been declared!");
        }
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
        SymTable<SymTableEntry> symTable = ASTUtils.getSafeEnv(node);
        Type type = node.getType();
        String id = node.getIdentifier().getIdentifier();

        if (symTable.lookup(id) != null) {
            String message = "The function " + id + " exists!";
            ASTUtils.error(node, message);
        } else {
            symTable.put(id, new SymTableEntry(id, type));
        }
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
        SymTable<SymTableEntry> symTable = ASTUtils.getSafeEnv(node);
        String id = node.getIdentifier().getIdentifier();
        Type type = node.getType();
        if (symTable.lookup(id) != null) {
            String message = "Field already exists!";
            ASTUtils.error(node, message);
        } else {
            symTable.put(id, new SymTableEntry(id, type));
        }
        node.getIdentifier().accept(this);
    }

    @Override
    public void visit(ClassDefinition node) throws ASTVisitorException {
        SymTable<SymTableEntry> symTable = ASTUtils.getSafeEnv(node);
        String id = node.getIdentifier().getIdentifier();

        if (symTable.lookup(id) != null) {
            ASTUtils.error(node, "Class with name " + id + " already exists!");
        } else {
            symTable.put(id, new SymTableEntry(id, TypeUtils.CLASS_TYPE));
            for (FieldOrFunctionDefinition f : node.getFieldOrFunctionDefinitions()) {
                f.accept(this);
            }
        }
    }

    @Override
    public void visit(AssignmentStatement node) throws ASTVisitorException {
        node.getExpression1().accept(this);
        node.getExpression2().accept(this);
    }

    @Override
    public void visit(PrintStatement node) throws ASTVisitorException {
        node.getExpression().accept(this);
    }

    @Override
    public void visit(CompoundStatement node) throws ASTVisitorException {
        for (Statement st : node.getStatements()) {
            st.accept(this);
        }
    }

    @Override
    public void visit(BinaryExpression node) throws ASTVisitorException {
        node.getExpression1().accept(this);
        node.getExpression2().accept(this);
    }

    @Override
    public void visit(UnaryExpression node) throws ASTVisitorException {
        node.getExpression().accept(this);
    }

    @Override
    public void visit(IdentifierExpression node) throws ASTVisitorException {
        //nothing
    }

    @Override
    public void visit(IntegerLiteralExpression node) throws ASTVisitorException {
        // nothing        
    }

    @Override
    public void visit(DoubleLiteralExpression node) throws ASTVisitorException {
        // nothing
    }

    @Override
    public void visit(StringLiteralExpression node) throws ASTVisitorException {
        // nothing
    }

    @Override
    public void visit(ParenthesisExpression node) throws ASTVisitorException {
        node.getExpression().accept(this);
    }

    @Override
    public void visit(DoWhileStatement node) throws ASTVisitorException {
        node.getStatement().accept(this);
        node.getExpression().accept(this);
    }

    @Override
    public void visit(WhileStatement node) throws ASTVisitorException {
        node.getExpression().accept(this);
        node.getStatement().accept(this);
    }

    @Override
    public void visit(BreakStatement node) throws ASTVisitorException {
        // nothing
    }

    @Override
    public void visit(ContinueStatement node) throws ASTVisitorException {
        // nothing
    }

    @Override
    public void visit(IfElseStatement node) throws ASTVisitorException {
        node.getExpression().accept(this);
        node.getStatement1().accept(this);
        node.getStatement2().accept(this);
    }

    @Override
    public void visit(IfStatement node) throws ASTVisitorException {
        node.getExpression().accept(this);
        node.getStatement().accept(this);
    }

    @Override
    public void visit(ReturnStatement node) throws ASTVisitorException {
        node.getExpr().accept(this);
    }

    @Override
    public void visit(TypeSpecifierStatement node) throws ASTVisitorException {
        SymTable<SymTableEntry> symTable = ASTUtils.getSafeEnv(node);
        String id = node.getIdentifier().getIdentifier();
        Type type = node.getType();
        if (symTable.lookupOnlyInTop(id) != null) {
            String message = "The variable already " + id + " exists!";
            ASTUtils.error(node, message);
        } else {
            symTable.put(id, new SymTableEntry(id, type));
        }
    }

    @Override
    public void visit(PlainStatement node) throws ASTVisitorException {
        node.getExp().accept(this);
    }

    @Override
    public void visit(WriteStatement node) throws ASTVisitorException {
        node.getExpression().accept(this);
    }

    @Override
    public void visit(DotExpression node) throws ASTVisitorException {
        node.getExp().accept(this);
        node.getIdentifier().accept(this);
    }

    @Override
    public void visit(NullExpression node) throws ASTVisitorException {
        //nothing
    }

    @Override
    public void visit(NewExpression node) throws ASTVisitorException {
        node.getIdentifier().accept(this);
        for (Expression e : node.getList()) {
            e.accept(this);
        }
    }

    @Override
    public void visit(DotExpressionList node) throws ASTVisitorException {
        node.getExp().accept(this);
        for (Expression e : node.getList()) {
            e.accept(this);
        }
    }

    @Override
    public void visit(ParameterDeclaration node) throws ASTVisitorException {
        SymTable<SymTableEntry> symTable = ASTUtils.getSafeEnv(node);
        String id = node.getIdentifier().getIdentifier();
        Type type = node.getType();
        if (symTable.lookupOnlyInTop(id) != null) {
            String message = "Parameter " + id + " already exists!";
            ASTUtils.error(node, message);
        } else {
            symTable.put(id, new SymTableEntry(id, type));
        }
        node.getIdentifier().accept(this);
    }

    @Override
    public void visit(ThisExpression node) throws ASTVisitorException {
        //nothing
    }

    @Override
    public void visit(FunctionCallExpression node) throws ASTVisitorException {
        node.getIdentifier().accept(this);
        for (Expression e : node.getExprList()) {
            e.accept(this);
        }
    }

}
