/**
 * This code is part of the lab exercises for the Compilers course at Harokopio
 * University of Athens, Dept. of Informatics and Telematics.
 */
package org.hua;

import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.hua.ast.ContinueStatement;
import org.hua.ast.Definition;
import org.hua.ast.Definitions;
import org.hua.ast.DoWhileStatement;
import org.hua.ast.DotExpression;
import org.hua.ast.DotExpressionList;
import org.hua.ast.DoubleLiteralExpression;
import org.hua.ast.FieldDefinition;
import org.hua.ast.FieldOrFunctionDefinition;
import org.hua.ast.FunctionDefinition;
import org.hua.ast.IdentifierExpression;
import org.hua.ast.IdentifierTypeSpecifier;
import org.hua.ast.IfElseStatement;
import org.hua.ast.IfStatement;
import org.hua.ast.IntegerLiteralExpression;
import org.hua.ast.NewExpression;
import org.hua.ast.NullExpression;
import org.hua.ast.NumberTypeSpecifier;
import org.hua.ast.ParameterDeclaration;
import org.hua.ast.ParenthesisExpression;
import org.hua.ast.PlainStatement;
import org.hua.ast.PrintStatement;
import org.hua.ast.ReturnStatement;
import org.hua.ast.Statement;
import org.hua.ast.StringLiteralExpression;
import org.hua.ast.StringTypeSpecifier;
import org.hua.ast.TypeSpecifierStatement;
import org.hua.types.TypeUtils;
import org.hua.ast.UnaryExpression;
import org.hua.ast.VarDeclarationStatement;
import org.hua.ast.VoidTypeSpecifier;
import org.hua.ast.WhileStatement;
import org.hua.ast.WriteStatement;
import org.objectweb.asm.Type;
import org.hua.types.TypeException;

/**
 * Compute possible types for each node.
 */
public class CollectTypesASTVisitor implements ASTVisitor {

    public CollectTypesASTVisitor() {
    }

    @Override
    public void visit(CompilationUnit node) throws ASTVisitorException {
       for(Definition def : node.getDefinitions()){
            def.accept(this);
        }
        ASTUtils.setType(node, Type.VOID_TYPE);
    }

    @Override
    public void visit(AssignmentStatement node) throws ASTVisitorException {
        Type type1 = ASTUtils.getSafeType(node.getExpression1());
        Type type2 = ASTUtils.getSafeType(node.getExpression2());
        
        if(TypeUtils.isAssignable(type1, type2)){
            node.getExpression1().accept(this);
            node.getExpression2().accept(this);
        }else{
            ASTUtils.setType(node, TypeUtils.maxType(type1, type2));
        }
    }

    @Override
    public void visit(PrintStatement node) throws ASTVisitorException {
        node.getExpression().accept(this);
        ASTUtils.setType(node, Type.VOID_TYPE);
    }

    @Override
    public void visit(CompoundStatement node) throws ASTVisitorException {
        for (Statement st : node.getStatements()) {
            st.accept(this);
        }
        ASTUtils.setType(node, Type.VOID_TYPE);
    }

    @Override
    public void visit(BinaryExpression node) throws ASTVisitorException {
        node.getExpression1().accept(this);
        node.getExpression2().accept(this);
        
        Type type1 = ASTUtils.getSafeType(node.getExpression1());
        Type type2 = ASTUtils.getSafeType(node.getExpression2());
        
        try {
            Type type = TypeUtils.applyBinary(node.getOperator(), type1, type2);
            ASTUtils.setType(node, type);
        } catch (TypeException ex) {
            ASTUtils.error(node, ex.getMessage());
        }
    }

    @Override
    public void visit(UnaryExpression node) throws ASTVisitorException {
        node.getExpression().accept(this);
        try {
            ASTUtils.setType(node, TypeUtils.applyUnary(node.getOperator(), ASTUtils.getSafeType(node.getExpression())));
        } catch (TypeException e) {
            ASTUtils.error(node, e.getMessage());
        }
    }

    @Override
    public void visit(IdentifierExpression node) throws ASTVisitorException {
        SymTable<SymTableEntry> env = ASTUtils.getSafeEnv(node);
        String id = node.getIdentifier();
        SymTableEntry symTableEntry = env.lookup(id);
        if ( symTableEntry == null ) {
            ASTUtils.error(node, "Not Definied!");
        }
        else {
            Type type = symTableEntry.getType();
            ASTUtils.setType(node, type);
        }

    }

    @Override
    public void visit(IntegerLiteralExpression node) throws ASTVisitorException {
        ASTUtils.setType(node, Type.INT_TYPE);
    }

    @Override
    public void visit(DoubleLiteralExpression node) throws ASTVisitorException {
        ASTUtils.setType(node, Type.DOUBLE_TYPE);
    }

    @Override
    public void visit(StringLiteralExpression node) throws ASTVisitorException {
        ASTUtils.setType(node, Type.getType(String.class));
    }

    @Override
    public void visit(ParenthesisExpression node) throws ASTVisitorException {
        node.getExpression().accept(this);
        ASTUtils.setType(node, ASTUtils.getSafeType(node.getExpression()));
    }

    @Override
    public void visit(DoWhileStatement node) throws ASTVisitorException {
        node.getStatement().accept(this);
        ASTUtils.setType(node, Type.VOID_TYPE);
        node.getExpression().accept(this);
        if (!ASTUtils.getSafeType(node.getExpression()).equals(Type.BOOLEAN_TYPE)) {
            ASTUtils.error(node.getExpression(), "Invalid expression, should be boolean");
        }
    }

    @Override
    public void visit(WhileStatement node) throws ASTVisitorException {
        node.getExpression().accept(this);
        if (!ASTUtils.getSafeType(node.getExpression()).equals(Type.BOOLEAN_TYPE)) {
            ASTUtils.error(node.getExpression(), "Invalid expression, should be boolean");
        }
        node.getStatement().accept(this);
        ASTUtils.setType(node, Type.VOID_TYPE);
    }

    @Override
    public void visit(VarDeclarationStatement node) throws ASTVisitorException {
        ASTUtils.setType(node, Type.VOID_TYPE);
    }

    @Override
    public void visit(BreakStatement node) throws ASTVisitorException {
        node.accept(this);
        ASTUtils.setType(node, Type.VOID_TYPE);
    }

    @Override
    public void visit(ContinueStatement node) throws ASTVisitorException {
        node.accept(this);
        ASTUtils.setType(node, Type.VOID_TYPE);
    }

    @Override
    public void visit(IfElseStatement node) throws ASTVisitorException {
        node.getExpression().accept(this);
        if (!ASTUtils.getSafeType(node.getExpression()).equals(Type.BOOLEAN_TYPE)) {
            ASTUtils.error(node.getExpression(), "Invalid expression, should be boolean");
        }
        node.getStatement1().accept(this);
        node.getStatement2().accept(this);
        ASTUtils.setType(node, Type.VOID_TYPE);
    }

    @Override
    public void visit(IfStatement node) throws ASTVisitorException {
        node.getExpression().accept(this);
        if (!ASTUtils.getSafeType(node.getExpression()).equals(Type.BOOLEAN_TYPE)) {
            ASTUtils.error(node.getExpression(), "Invalid expression, should be boolean");
        }
        node.getStatement().accept(this);
        ASTUtils.setType(node, Type.VOID_TYPE);
    }

    @Override
    public void visit(ReturnStatement node) throws ASTVisitorException {
        node.accept(this);
        ASTUtils.setType(node, Type.VOID_TYPE);

    }

    @Override
    public void visit(TypeSpecifierStatement node) throws ASTVisitorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(FunctionDefinition node) throws ASTVisitorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(FieldOrFunctionDefinition node) throws ASTVisitorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(FieldDefinition node) throws ASTVisitorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(ClassDefinition node) throws ASTVisitorException {
        node.accept(this);
        ASTUtils.setType(node, Type.VOID_TYPE);
    }

    @Override
    public void visit(PlainStatement node) throws ASTVisitorException {
        node.accept(this);
    }

    @Override
    public void visit(Definitions node) throws ASTVisitorException {
        node.accept(this);
    }

    @Override
    public void visit(IdentifierTypeSpecifier node) throws ASTVisitorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(VoidTypeSpecifier node) throws ASTVisitorException {
        ASTUtils.setType(node, Type.VOID_TYPE);
    }

    @Override
    public void visit(StringTypeSpecifier node) throws ASTVisitorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(NumberTypeSpecifier node) throws ASTVisitorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(WriteStatement node) throws ASTVisitorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(DotExpression node) throws ASTVisitorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(NullExpression node) throws ASTVisitorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(NewExpression node) throws ASTVisitorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(DotExpressionList node) throws ASTVisitorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void visit(ParameterDeclaration node) throws ASTVisitorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
