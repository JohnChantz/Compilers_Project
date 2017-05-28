package org.hua;

import java.util.HashMap;
import java.util.Map;
import org.hua.ast.ASTNode;
import org.hua.symbol.SymTable;
import org.hua.symbol.SymTableEntry;

/**
 * Global registry (Singleton pattern)
 */
public class Registry {

    ASTNode root;
    Map<String, SymTable<SymTableEntry>> map;

    private Registry() {
        root = null;
        map = new HashMap<String, SymTable<SymTableEntry>>();
    }

    private static class SingletonHolder {

        public static final Registry instance = new Registry();

    }

    public static Registry getInstance() {
        return SingletonHolder.instance;
    }

    public ASTNode getRoot() {
        return root;
    }

    public void setRoot(ASTNode root) {
        this.root = root;
    }

    public Map<String, SymTable<SymTableEntry>> getMap() {
        return map;
    }

    public void setMap(Map<String, SymTable<SymTableEntry>> map) {
        this.map = map;
    }

    public SymTable<SymTableEntry> lookup(String s) {
        SymTable<SymTableEntry> r = map.get(s);
        if (r != null) {
            return r;
        }
        return null;
    }
}
