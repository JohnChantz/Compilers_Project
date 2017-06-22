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
import org.hua.ast.Definition;
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
import org.hua.ast.Operator;
import org.hua.ast.ParameterDeclaration;
import org.hua.ast.ParenthesisExpression;
import org.hua.ast.PlainStatement;
import org.hua.ast.PrintStatement;
import org.hua.ast.ReturnStatement;
import org.hua.ast.Statement;
import org.hua.ast.StringLiteralExpression;
import org.hua.ast.ThisExpression;
import org.hua.ast.TypeSpecifierStatement;
import org.hua.types.TypeUtils;
import org.hua.ast.UnaryExpression;
import org.hua.ast.WhileStatement;
import org.hua.ast.WriteStatement;
import org.objectweb.asm.Type;
import org.hua.types.TypeException;

/**
 * Compute possible types for each node.
 */
public class CollectTypesASTVisitor implements ASTVisitor {

    private Type type;
    private Boolean classFlag = false;
    private Boolean isThis = false;
    private Boolean isDot = false;
    private String className;

    public CollectTypesASTVisitor() {
    }

    @Override
    public void visit(CompilationUnit node) throws ASTVisitorException {
        for (Definition def : node.getDefinitions()) {
            def.accept(this);
        }
        ASTUtils.setType(node, Type.VOID_TYPE);
    }

    @Override
    public void visit(AssignmentStatement node) throws ASTVisitorException {
        node.getExpression1().accept(this);
        node.getExpression2().accept(this);

        Type type1 = ASTUtils.getSafeType(node.getExpression1());
        Type type2 = ASTUtils.getSafeType(node.getExpression2());

        boolean bool = TypeUtils.isAssignable(type1, type2);
        if (bool) {
            ASTUtils.setType(node, type1);
        } else {
            ASTUtils.error(node, "Assigment statement with different types!");
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

        Operator op = node.getOperator();
        Type type1 = ASTUtils.getSafeType(node.getExpression1());
        Type type2 = ASTUtils.getSafeType(node.getExpression2());

        try {
            Type type = TypeUtils.applyBinary(op, type1, type2);
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
        Type type;
        SymTable<SymTableEntry> symTable = ASTUtils.getSafeEnv(node);
        String id = node.getIdentifier();
        if (id.equals("")) {
            ASTUtils.setType(node, Type.VOID_TYPE);
            return;
        }
        SymTableEntry symTableEntry = symTable.lookup(id);
        if (symTableEntry == null) {
            ASTUtils.error(node, "Variable " + id + " not defined in scope!");
        } else {
            type = symTableEntry.getType();
            ASTUtils.setType(node, type);
            this.className = type.toString();
            int i = className.lastIndexOf('/');
            if (i != -1) {
                className = className.substring(i+1, className.length() - 1);
            }
            if (this.isDot) {
                ASTUtils.setClassBelongsName(node, className);
            }
        }
    }

    @Override
    public void visit(IntegerLiteralExpression node) throws ASTVisitorException {
        //set node nodeType = int
        ASTUtils.setType(node, Type.INT_TYPE);
    }

    @Override
    public void visit(DoubleLiteralExpression node) throws ASTVisitorException {
        //set node nodeType = double
        ASTUtils.setType(node, Type.DOUBLE_TYPE);
    }

    @Override
    public void visit(StringLiteralExpression node) throws ASTVisitorException {
        //set node nodeType = String
        ASTUtils.setType(node, TypeUtils.STRING_TYPE);
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
            ASTUtils.error(node.getExpression(), "Invalid expression, should be boolean!");
        }
    }

    @Override
    public void visit(WhileStatement node) throws ASTVisitorException {
        node.getExpression().accept(this);
        if (!ASTUtils.getSafeType(node.getExpression()).equals(Type.BOOLEAN_TYPE)) {
            ASTUtils.error(node.getExpression(), "Invalid expression, should be boolean!");
        }
        node.getStatement().accept(this);
        ASTUtils.setType(node, Type.VOID_TYPE);
    }

    @Override
    public void visit(BreakStatement node) throws ASTVisitorException {
        Boolean b = ASTUtils.allowBreak(node);
        if (!b) {
            ASTUtils.error(node, "Break statement outside loop!");
        }
        ASTUtils.setType(node, Type.VOID_TYPE);
    }

    @Override
    public void visit(ContinueStatement node) throws ASTVisitorException {
        Boolean b = ASTUtils.allowContinue(node);
        if (!b) {
            ASTUtils.error(node, "Continue statement outside loop!");
        }
        ASTUtils.setType(node, Type.VOID_TYPE);
    }

    @Override
    public void visit(IfElseStatement node) throws ASTVisitorException {
        node.getExpression().accept(this);
        if (!ASTUtils.getSafeType(node.getExpression()).equals(Type.BOOLEAN_TYPE)) {
            ASTUtils.error(node.getExpression(), "Invalid expression, should be boolean!");
        }
        node.getStatement1().accept(this);
        node.getStatement2().accept(this);
        ASTUtils.setType(node, Type.VOID_TYPE);
    }

    @Override
    public void visit(IfStatement node) throws ASTVisitorException {
        node.getExpression().accept(this);
        if (!ASTUtils.getSafeType(node.getExpression()).equals(Type.BOOLEAN_TYPE)) {
            ASTUtils.error(node.getExpression(), "Invalid expression, should be boolean!");
        }
        node.getStatement().accept(this);
        ASTUtils.setType(node, Type.VOID_TYPE);
    }

    @Override
    public void visit(ReturnStatement node) throws ASTVisitorException {
        Type nodeType;
        if (node.getExpr() != null) {
            node.getExpr().accept(this);
            nodeType = ASTUtils.getSafeType(node.getExpr());
        } else {
            nodeType = Type.VOID_TYPE;
        }
        Boolean b = TypeUtils.isAssignable(nodeType, this.type);
        if (b) {
            ASTUtils.setType(node, nodeType);
        } else {
            ASTUtils.error(node, "Return statement type dont match function's return type!");
        }
    }

    @Override
    public void visit(TypeSpecifierStatement node) throws ASTVisitorException {
        node.getIdentifier().accept(this);
        Type type = node.getType();
        ASTUtils.setType(node, type);
    }

    @Override
    public void visit(FunctionDefinition node) throws ASTVisitorException {
        SymTable<SymTableEntry> env = ASTUtils.getSafeEnv(node);
        String id = node.getIdentifier().getIdentifier();
        SymTableEntry symTableEntry = env.lookup(id);

        if (symTableEntry == null) {
            ASTUtils.error(node, "Function" + id + " not definied!");
        } else {
            for (ParameterDeclaration p : node.getParameterList()) {
                p.accept(this);
            }
            Type type = symTableEntry.getType();
            this.type = type;
            node.getCompoundStatement().accept(this);
            ASTUtils.setType(node, type);
            this.type = null;
        }
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
        SymTable<SymTableEntry> env = ASTUtils.getSafeEnv(node);
        String id = node.getIdentifier().getIdentifier();
        SymTableEntry symTableEntry = env.lookupOnlyInTop(id);
        if (symTableEntry == null) {
            ASTUtils.error(node, "Field" + id + " not definied!");
        } else {
            Type type = symTableEntry.getType();
            ASTUtils.setType(node, type);
        }
    }

    @Override
    public void visit(ClassDefinition node) throws ASTVisitorException {
        this.classFlag = true;
        for (FieldOrFunctionDefinition f : node.getFieldOrFunctionDefinitions()) {
            f.accept(this);
        }
        this.classFlag = false;
        ASTUtils.setType(node, TypeUtils.CLASS_TYPE);
    }

    @Override
    public void visit(PlainStatement node) throws ASTVisitorException {
        node.getExp().accept(this);
        ASTUtils.setType(node, Type.VOID_TYPE);
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
        node.getExpression().accept(this);
        ASTUtils.setType(node, Type.VOID_TYPE);

    }

    @Override
    public void visit(DotExpression node) throws ASTVisitorException {
        this.isDot = true;
        node.getExp().accept(this);
        String id = node.getIdentifier().getIdentifier();
        if (!this.isThis) {
            SymTable<SymTableEntry> symTable = Registry.getInstance().lookup(this.className);
            SymTableEntry e = symTable.lookupOnlyInTop(id);
            if (e == null) {
                ASTUtils.error(node, "Field or Function " + id + " does not exist in class!");
            }
            ASTUtils.setType(node, e.getType());
        } else {
            node.getIdentifier().accept(this);
            ASTUtils.setType(node, ASTUtils.getSafeType(node.getIdentifier()));
        }
        this.isThis = false;
        this.isDot = true;
    }

    @Override
    public void visit(NullExpression node) throws ASTVisitorException {
        //set node nodeType = void
        ASTUtils.setType(node, Type.VOID_TYPE);
    }

    @Override
    public void visit(NewExpression node) throws ASTVisitorException {
        String identifier = node.getIdentifier().getIdentifier();
        SymTable<SymTableEntry> symTable = Registry.getInstance().lookup(identifier);
        if (symTable == null) {
            ASTUtils.error(node, "Class " + identifier + " has not been declared!");
        }
        for (Expression e : node.getList()) {
            e.accept(this);
        }
        ASTUtils.setType(node, Type.VOID_TYPE);
    }

    @Override
    public void visit(DotExpressionList node) throws ASTVisitorException {
        this.isDot = true;
        node.getExp().accept(this);
        String id = node.getIdentifier().getIdentifier();
        if (!this.isThis) {
            SymTable<SymTableEntry> symTable = Registry.getInstance().lookup(this.className);
            SymTableEntry e = symTable.lookupOnlyInTop(id);
            if (e == null) {
                ASTUtils.error(node, "Field or Function " + id + " does not exist in class!");
            }
            ASTUtils.setType(node, e.getType());
        } else {
            node.getIdentifier().accept(this);
            ASTUtils.setType(node, ASTUtils.getSafeType(node.getIdentifier()));
        }
        this.isThis = false;
        for (Expression e : node.getList()) {
            e.accept(this);
        }
        this.isDot = false;
    }

    @Override
    public void visit(ParameterDeclaration node) throws ASTVisitorException {
        Type type = node.getType();
        ASTUtils.setType(node, type);
        node.getIdentifier().accept(this);
    }

    @Override
    public void visit(ThisExpression node) throws ASTVisitorException {
        if (!this.classFlag) {
            ASTUtils.error(node, "\'This\' expression not allowed outside of class scope!");
        }
        this.isThis = true;
        ASTUtils.setType(node, Type.VOID_TYPE);
    }

    @Override
    public void visit(FunctionCallExpression node) throws ASTVisitorException {
        SymTable<SymTableEntry> symTable = ASTUtils.getSafeEnv(node);
        node.getIdentifier().accept(this);
        String id = node.getIdentifier().getIdentifier();
        for (Expression e : node.getExprList()) {
            e.accept(this);
        }
        SymTableEntry e = symTable.lookup(id);
        ASTUtils.setType(node, e.getType());
    }

}
