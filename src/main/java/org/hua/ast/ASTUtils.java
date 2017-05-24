package org.hua.ast;

import org.objectweb.asm.Type;
import org.hua.symbol.SymTable;
import org.hua.symbol.SymTableEntry;

/**
 * Class with static helper methods for AST handling
 */
public class ASTUtils {

    public static final String SYMTABLE_PROPERTY = "SYMTABLE_PROPERTY";
    public static final String IS_BOOLEAN_EXPR_PROPERTY = "IS_BOOLEAN_EXPR_PROPERTY";
    public static final String TYPE_PROPERTY = "TYPE_PROPERTY";
    public static final String IS_FUNCTION = "IS_FUNCTION";
    public static final String IS_CLASS = "IS_CLASS";
    public static final String ALLOW_BREAK = "ALLOW_BREAK";
    public static final String ALLOW_CONTINUE = "ALLOW_CONTINUE";

    private ASTUtils() {
    }

    public static SymTable<SymTableEntry> getEnv(ASTNode node) {
        return (SymTable<SymTableEntry>) node.getProperty(SYMTABLE_PROPERTY);
    }

    public static SymTable<SymTableEntry> getSafeEnv(ASTNode node) throws ASTVisitorException {
        SymTable<SymTableEntry> symTable = (SymTable<SymTableEntry>) node.getProperty(SYMTABLE_PROPERTY);
        if (symTable == null) {
            ASTUtils.error(node, "Symbol table not found.");
        }
        return symTable;
    }

    public static void setEnv(ASTNode node, SymTable<SymTableEntry> symTable) {
        node.setProperty(SYMTABLE_PROPERTY, symTable);
    }

    public static boolean isBooleanExpression(Expression node) {
        Boolean b = (Boolean) node.getProperty(IS_BOOLEAN_EXPR_PROPERTY);
        if (b == null) {
            return false;
        }
        return b;
    }

    public static boolean isClass(ClassDefinition node) {
        Boolean b = (Boolean) node.getProperty("IS_CLASS");
        if (b == null) {
            return false;
        }
        return b;
    }

    public static boolean isFunction(FunctionDefinition node) {
        Boolean b = (Boolean) node.getProperty("IS_FUNCTION");
        if (b == null) {
            return false;
        }
        return b;
    }

    public static boolean allowBreak(Statement node) {
        Boolean b = (Boolean) node.getProperty("ALLOW_BREAK");
        if (b == null) {
            return false;
        }
        return b;
    }

    public static boolean allowContinue(Statement node) {
        Boolean b = (Boolean) node.getProperty("ALLOW_CONTINUE");
        if (b == null) {
            return false;
        }
        return b;
    }

    public static void setBooleanExpression(Expression node, boolean value) {
        node.setProperty(IS_BOOLEAN_EXPR_PROPERTY, value);
    }

    public static void setIsClass(ASTNode node, boolean value) {
        node.setProperty(IS_CLASS, value);
    }

    public static void setIsFunction(ASTNode node, boolean value) {
        node.setProperty(IS_FUNCTION, value);
    }

    public static void setAllowBreak(Statement node, boolean value) {
        node.setProperty(ALLOW_BREAK, value);
    }

    public static void setAllowContinue(Statement node, boolean value) {
        node.setProperty(ALLOW_CONTINUE, value);
    }

    public static Type getType(ASTNode node) {
        return (Type) node.getProperty(TYPE_PROPERTY);
    }

    public static Type getSafeType(ASTNode node) throws ASTVisitorException {
        Type type = (Type) node.getProperty(TYPE_PROPERTY);
        if (type == null) {
            ASTUtils.error(node, "Type not found.");
        }
        return type;
    }

    public static void setType(ASTNode node, Type type) {
        node.setProperty(TYPE_PROPERTY, type);
    }

    public static void error(ASTNode node, String message) throws ASTVisitorException {
        throw new ASTVisitorException(node.getLine() + ":" + node.getColumn() + ": " + message);
    }

}
