package org.hua;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import org.hua.ast.ASTNode;
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
import org.hua.ast.UnaryExpression;
import org.hua.ast.WhileStatement;
import org.hua.ast.WriteStatement;
import org.hua.symbol.LocalIndexPool;
import org.hua.symbol.SymTable;
import org.hua.symbol.SymTableEntry;
import org.hua.types.TypeUtils;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class BytecodeGeneratorASTVisitor implements ASTVisitor {

    /*
    *megaClass is used to include all the other classes and functions as well as the main function of the program
    *cn is a ClassNode used to include the inner classes of the megaClass
    *fn is a FieldNode for the class fields
    *mn is a MethodNode used to create the functions
     */
    private ClassNode megaClass;
    private ClassNode cn;
    private Deque<ClassNode> classStack = new ArrayDeque();
    private FieldNode fn;
    private MethodNode mn;
    private String id;
    private Boolean externalMethod = false;

    public BytecodeGeneratorASTVisitor() {
    }

    public ClassNode getClassNode() {
        return megaClass;
    }

    @Override
    public void visit(CompilationUnit node) throws ASTVisitorException {
        megaClass = new ClassNode();
        megaClass.access = Opcodes.ACC_PUBLIC;
        megaClass.version = Opcodes.V1_5;
        megaClass.name = "MegaClass";
        megaClass.sourceFile = "MegaClass.in";
        megaClass.superName = "java/lang/Object";

        //constructor for the MegaClass
        mn = new MethodNode(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mn.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
        mn.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V"));
        mn.instructions.add(new InsnNode(Opcodes.RETURN));
        mn.maxLocals = 1;
        mn.maxStack = 1;
        megaClass.methods.add(mn);

        for (Definitions def : node.getDefinitions()) {
            def.accept(this);
        }
    }

    @Override
    public void visit(Definitions node) throws ASTVisitorException {
        if (node.getClassDefinition() == null) {
            externalMethod = true;
            node.getFunctionDefinition().accept(this);
            externalMethod = false;
        }
        if (node.getFunctionDefinition() == null) {
            node.getClassDefinition().accept(this);
        }
    }

    @Override
    public void visit(ClassDefinition node) throws ASTVisitorException {
        id = node.getIdentifier().getIdentifier();
        cn = new ClassNode();
        cn.access = Opcodes.ACC_PUBLIC;
        cn.version = Opcodes.V1_5;
        cn.name = id;
        cn.superName = "MegaClass";
        cn.sourceFile = "MegaClass.in";

        // create constructor
        mn = new MethodNode(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mn.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
        mn.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V"));
        mn.instructions.add(new InsnNode(Opcodes.RETURN));
        mn.maxLocals = 1;
        mn.maxStack = 1;
        cn.methods.add(mn);
        classStack.push(cn);

        for (FieldOrFunctionDefinition f : node.getFieldOrFunctionDefinitions()) {
            f.accept(this);
        }
    }

    @Override
    public void visit(FieldDefinition node) throws ASTVisitorException {
        id = node.getIdentifier().getIdentifier();
        Type type = node.getType();
        fn = new FieldNode(Opcodes.ACC_PUBLIC, id, type.toString(), null, null);
        cn.fields.add(fn);
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
    public void visit(FunctionDefinition node) throws ASTVisitorException {
        String fooId = node.getIdentifier().getIdentifier();
        Type fooType = node.getType();
        String parameterTypes = "";
        for (ParameterDeclaration p : node.getParameterList()) {
            p.accept(this);
            parameterTypes += p.getType().toString();
        }
        if (fooId.equals("main")) {
            mn = new MethodNode(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        } else {
            mn = new MethodNode(Opcodes.ACC_PUBLIC, fooId, "(" + parameterTypes + ")" + fooType, null, null);
        }
        node.getCompoundStatement().accept(this);

        if (externalMethod) {
            megaClass.methods.add(mn);
        } else {
            cn.methods.add(mn);
        }
    }

    @Override
    public void visit(BreakStatement node) throws ASTVisitorException {
        JumpInsnNode jmp = new JumpInsnNode(Opcodes.GOTO, null);
        mn.instructions.add(jmp);
        ASTUtils.getBreakList(node).add(jmp);
    }

    @Override
    public void visit(ContinueStatement node) throws ASTVisitorException {
        JumpInsnNode jmp = new JumpInsnNode(Opcodes.GOTO, null);
        mn.instructions.add(jmp);
        ASTUtils.getContinueList(node).add(jmp);
    }

    @Override
    public void visit(CompoundStatement node) throws ASTVisitorException {
        List<JumpInsnNode> breakList = new ArrayList<JumpInsnNode>();
        List<JumpInsnNode> continueList = new ArrayList<JumpInsnNode>();
        Statement s = null, ps;
        Iterator<Statement> it = node.getStatements().iterator();
        while (it.hasNext()) {
            ps = s;
            s = it.next();
            if (ps != null && !ASTUtils.getNextList(ps).isEmpty()) {
                LabelNode labelNode = new LabelNode();
                mn.instructions.add(labelNode);
                backpatch(ASTUtils.getNextList(ps), labelNode);
            }
            s.accept(this);
            breakList.addAll(ASTUtils.getBreakList(s));
            continueList.addAll(ASTUtils.getContinueList(s));
        }
        if (s != null) {
            ASTUtils.setNextList(node, ASTUtils.getNextList(s));
        }
        ASTUtils.setBreakList(node, breakList);
        ASTUtils.setContinueList(node, continueList);
    }

    @Override
    public void visit(IfStatement node) throws ASTVisitorException {
        ASTUtils.setBooleanExpression(node.getExpression(), true);

        node.getExpression().accept(this);

        LabelNode labelNode = new LabelNode();
        mn.instructions.add(labelNode);
        backpatch(ASTUtils.getTrueList(node.getExpression()), labelNode);

        node.getStatement().accept(this);

        ASTUtils.getBreakList(node).addAll(ASTUtils.getBreakList(node.getStatement()));
        ASTUtils.getContinueList(node).addAll(ASTUtils.getContinueList(node.getStatement()));

        ASTUtils.getNextList(node).addAll(ASTUtils.getFalseList(node.getExpression()));
        ASTUtils.getNextList(node).addAll(ASTUtils.getNextList(node.getStatement()));
    }

    @Override
    public void visit(DoWhileStatement node) throws ASTVisitorException {
        ASTUtils.setBooleanExpression(node.getExpression(), true);

        LabelNode beginLabelNode = new LabelNode();
        mn.instructions.add(beginLabelNode);

        node.getStatement().accept(this);
        ASTUtils.getNextList(node).addAll(ASTUtils.getBreakList(node.getStatement()));

        LabelNode beginExprLabelNode = new LabelNode();
        mn.instructions.add(beginExprLabelNode);
        backpatch(ASTUtils.getNextList(node.getStatement()), beginExprLabelNode);
        backpatch(ASTUtils.getContinueList(node.getStatement()), beginExprLabelNode);

        node.getExpression().accept(this);
        ASTUtils.getNextList(node).addAll(ASTUtils.getFalseList(node.getExpression()));
        backpatch(ASTUtils.getTrueList(node.getExpression()), beginLabelNode);
    }

    @Override
    public void visit(IfElseStatement node) throws ASTVisitorException {
        ASTUtils.setBooleanExpression(node.getExpression(), true);

        node.getExpression().accept(this);

        LabelNode stmt1StartLabelNode = new LabelNode();
        mn.instructions.add(stmt1StartLabelNode);
        node.getStatement1().accept(this);

        JumpInsnNode skipGoto = new JumpInsnNode(Opcodes.GOTO, null);
        mn.instructions.add(skipGoto);

        LabelNode stmt2StartLabelNode = new LabelNode();
        mn.instructions.add(stmt2StartLabelNode);
        node.getStatement2().accept(this);

        backpatch(ASTUtils.getTrueList(node.getExpression()), stmt1StartLabelNode);
        backpatch(ASTUtils.getFalseList(node.getExpression()), stmt2StartLabelNode);

        ASTUtils.getNextList(node).addAll(ASTUtils.getNextList(node.getStatement1()));
        ASTUtils.getNextList(node).addAll(ASTUtils.getNextList(node.getStatement2()));
        ASTUtils.getNextList(node).add(skipGoto);

        ASTUtils.getBreakList(node).addAll(ASTUtils.getBreakList(node.getStatement1()));
        ASTUtils.getBreakList(node).addAll(ASTUtils.getBreakList(node.getStatement2()));

        ASTUtils.getContinueList(node).addAll(ASTUtils.getContinueList(node.getStatement1()));
        ASTUtils.getContinueList(node).addAll(ASTUtils.getContinueList(node.getStatement2()));
    }

    @Override
    public void visit(WhileStatement node) throws ASTVisitorException {
        ASTUtils.setBooleanExpression(node.getExpression(), true);

        LabelNode beginLabelNode = new LabelNode();
        mn.instructions.add(beginLabelNode);

        node.getExpression().accept(this);

        LabelNode trueLabelNode = new LabelNode();
        mn.instructions.add(trueLabelNode);
        backpatch(ASTUtils.getTrueList(node.getExpression()), trueLabelNode);

        node.getStatement().accept(this);

        backpatch(ASTUtils.getNextList(node.getStatement()), beginLabelNode);
        backpatch(ASTUtils.getContinueList(node.getStatement()), beginLabelNode);

        mn.instructions.add(new JumpInsnNode(Opcodes.GOTO, beginLabelNode));

        ASTUtils.getNextList(node).addAll(ASTUtils.getFalseList(node.getExpression()));
        ASTUtils.getNextList(node).addAll(ASTUtils.getBreakList(node.getStatement()));
    }

    @Override
    public void visit(IntegerLiteralExpression node) throws ASTVisitorException {
        if (ASTUtils.isBooleanExpression(node)) {
            JumpInsnNode i = new JumpInsnNode(Opcodes.GOTO, null);
            mn.instructions.add(i);
            if (node.getLiteral() != 0) {
                ASTUtils.getTrueList(node).add(i);
            } else {
                ASTUtils.getFalseList(node).add(i);
            }
        } else {
            Double d = Double.valueOf(node.getLiteral());
            mn.instructions.add(new LdcInsnNode(d));
        }
    }

    @Override
    public void visit(DoubleLiteralExpression node) throws ASTVisitorException {
        if (ASTUtils.isBooleanExpression(node)) {
            JumpInsnNode i = new JumpInsnNode(Opcodes.GOTO, null);
            mn.instructions.add(i);
            if (node.getLiteral() != 0) {
                ASTUtils.getTrueList(node).add(i);
            } else {
                ASTUtils.getFalseList(node).add(i);
            }
        } else {
            Double d = Double.valueOf(node.getLiteral());
            mn.instructions.add(new LdcInsnNode(d));
        }
    }

    @Override
    public void visit(StringLiteralExpression node) throws ASTVisitorException {
        if (ASTUtils.isBooleanExpression(node)) {
            ASTUtils.error(node, "String Literal can't be boolean expression!");
        } else {
            String s = node.getLiteral();
            mn.instructions.add(new LdcInsnNode(s));
        }
    }

    @Override
    public void visit(ParenthesisExpression node) throws ASTVisitorException {
        node.getExpression().accept(this);
    }

    @Override
    public void visit(PrintStatement node) throws ASTVisitorException {
        mn.instructions.add(new FieldInsnNode(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
        node.getExpression().accept(this);
        Type type = ASTUtils.getSafeType(node.getExpression());
        mn.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(" + type + ")V"));
    }

    @Override
    public void visit(UnaryExpression node) throws ASTVisitorException {
        node.getExpression().accept(this);
        Type type = ASTUtils.getSafeType(node.getExpression());
        if (node.getOperator().equals(Operator.MINUS)) {
            mn.instructions.add(new InsnNode(type.getOpcode(Opcodes.INEG)));
        } else {
            ASTUtils.error(node, "Operator not recognized.");
        }
    }

    @Override
    public void visit(AssignmentStatement node) throws ASTVisitorException {
        node.getExpression1().accept(this);
        node.getExpression2().accept(this);
        SymTable<SymTableEntry> symTable = ASTUtils.getSafeEnv(node);

        Expression sourceExp = node.getExpression2();
        Expression targetExp = node.getExpression1();
        Type sourceType = ASTUtils.getSafeType(sourceExp);
        Type targetType = ASTUtils.getSafeType(targetExp);

        SymTableEntry target = symTable.lookup(id);
        int targetIndex = target.getIndex();

        this.widen(targetType, sourceType);
        mn.instructions.add(new VarInsnNode(targetType.getOpcode(Opcodes.ISTORE), targetIndex));
    }

    @Override
    public void visit(IdentifierExpression node) throws ASTVisitorException {
        id = node.getIdentifier();
        SymTable<SymTableEntry> symTable = ASTUtils.getSafeEnv(node);
        SymTableEntry e = symTable.lookup(id);
        if (e != null) {
            Type type = e.getType();
            int index = e.getIndex();
            mn.instructions.add(new VarInsnNode(type.getOpcode(Opcodes.ILOAD), index));
        }
    }

    @Override
    public void visit(BinaryExpression node) throws ASTVisitorException {
        node.getExpression1().accept(this);
        Type expr1Type = ASTUtils.getSafeType(node.getExpression1());

        node.getExpression2().accept(this);
        Type expr2Type = ASTUtils.getSafeType(node.getExpression2());

        Type maxType = TypeUtils.maxType(expr1Type, expr2Type);
        // cast top of stack to max
        if (!maxType.equals(expr2Type)) {
            widen(maxType, expr2Type);
        }
        // cast second from top to max
        if (!maxType.equals(expr1Type)) {
            LocalIndexPool lip = ASTUtils.getSafeLocalIndexPool(node);
            int localIndex = -1;
            if (expr2Type.equals(Type.DOUBLE_TYPE) || expr1Type.equals(Type.DOUBLE_TYPE)) {
                localIndex = lip.getLocalIndex(expr2Type);
                mn.instructions.add(new VarInsnNode(expr2Type.getOpcode(Opcodes.ISTORE), localIndex));
            } else {
                mn.instructions.add(new InsnNode(Opcodes.SWAP));
            }
            widen(maxType, expr1Type);
            if (expr2Type.equals(Type.DOUBLE_TYPE) || expr1Type.equals(Type.DOUBLE_TYPE)) {
                mn.instructions.add(new VarInsnNode(expr2Type.getOpcode(Opcodes.ILOAD), localIndex));
                lip.freeLocalIndex(localIndex, expr2Type);
            } else {
                mn.instructions.add(new InsnNode(Opcodes.SWAP));
            }
        }
        if (ASTUtils.isBooleanExpression(node)) {
            handleBooleanOperator(node, node.getOperator(), maxType);
        } else if (maxType.equals(TypeUtils.STRING_TYPE)) {
            mn.instructions.add(new InsnNode(Opcodes.SWAP));
            handleStringOperator(node, node.getOperator());
        } else {
            handleNumberOperator(node, node.getOperator(), maxType);
        }
    }

    private void backpatch(List<JumpInsnNode> list, LabelNode labelNode) {
        if (list == null) {
            return;
        }
        for (JumpInsnNode instr : list) {
            instr.label = labelNode;
        }
    }

    /**
     * Cast the top of the stack to a particular parametersTypes
     */
    private void widen(Type target, Type source) {
        if (source.equals(target)) {
            return;
        }

        if (source.equals(Type.BOOLEAN_TYPE)) {
            if (target.equals(Type.INT_TYPE)) {
                // nothing
            } else if (target.equals(Type.DOUBLE_TYPE)) {
                mn.instructions.add(new InsnNode(Opcodes.I2D));
            } else if (target.equals(TypeUtils.STRING_TYPE)) {
                mn.instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Boolean", "toString", "(Z)Ljava/lang/String;"));
            }
        } else if (source.equals(Type.INT_TYPE)) {
            if (target.equals(Type.DOUBLE_TYPE)) {
                mn.instructions.add(new InsnNode(Opcodes.I2D));
            } else if (target.equals(TypeUtils.STRING_TYPE)) {
                mn.instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Integer", "toString", "(I)Ljava/lang/String;"));
            }
        } else if (source.equals(Type.DOUBLE_TYPE)) {
            if (target.equals(TypeUtils.STRING_TYPE)) {
                mn.instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/lang/Double", "toString", "(D)Ljava/lang/String;"));
            }
        }
    }

    private void handleBooleanOperator(Expression node, Operator op, Type type) throws ASTVisitorException {
        List<JumpInsnNode> trueList = new ArrayList<JumpInsnNode>();
        if (type.equals(TypeUtils.STRING_TYPE)) {
            mn.instructions.add(new InsnNode(Opcodes.SWAP));
            JumpInsnNode jmp = null;
            mn.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z"));
            switch (op) {
                case EQUAL:
                    jmp = new JumpInsnNode(Opcodes.IFNE, null);
                    break;
                case NOT_EQUAL:
                    jmp = new JumpInsnNode(Opcodes.IFEQ, null);
                    break;
                default:
                    ASTUtils.error(node, "Operator not supported on strings");
                    break;
            }
            mn.instructions.add(jmp);
            trueList.add(jmp);
        } else if (type.equals(Type.DOUBLE_TYPE)) {
            mn.instructions.add(new InsnNode(Opcodes.DCMPG));
            JumpInsnNode jmp = null;
            switch (op) {
                case EQUAL:
                    jmp = new JumpInsnNode(Opcodes.IFEQ, null);
                    break;
                case NOT_EQUAL:
                    jmp = new JumpInsnNode(Opcodes.IFNE, null);
                    break;
                case GREATER:
                    jmp = new JumpInsnNode(Opcodes.IFGT, null);
                    break;
                case GREATER_EQUAL:
                    jmp = new JumpInsnNode(Opcodes.IFGE, null);
                    break;
                case LESS:
                    jmp = new JumpInsnNode(Opcodes.IFLT, null);
                    break;
                case LESS_EQUAL:
                    jmp = new JumpInsnNode(Opcodes.IFLE, null);
                    break;
                default:
                    ASTUtils.error(node, "No such operator!");
                    break;
            }
            mn.instructions.add(jmp);
            trueList.add(jmp);
        } else {
            JumpInsnNode jmp = null;
            switch (op) {
                case EQUAL:
                    jmp = new JumpInsnNode(Opcodes.IF_ICMPEQ, null);
                    mn.instructions.add(jmp);
                    break;
                case NOT_EQUAL:
                    jmp = new JumpInsnNode(Opcodes.IF_ICMPNE, null);
                    mn.instructions.add(jmp);
                    break;
                case GREATER:
                    jmp = new JumpInsnNode(Opcodes.IF_ICMPGT, null);
                    mn.instructions.add(jmp);
                    break;
                case GREATER_EQUAL:
                    jmp = new JumpInsnNode(Opcodes.IF_ICMPGE, null);
                    mn.instructions.add(jmp);
                    break;
                case LESS:
                    jmp = new JumpInsnNode(Opcodes.IF_ICMPLT, null);
                    mn.instructions.add(jmp);
                    break;
                case LESS_EQUAL:
                    jmp = new JumpInsnNode(Opcodes.IF_ICMPLE, null);
                    mn.instructions.add(jmp);
                    break;
                default:
                    ASTUtils.error(node, "Operator not supported");
                    break;
            }
            trueList.add(jmp);
        }
        ASTUtils.setTrueList(node, trueList);
        List<JumpInsnNode> falseList = new ArrayList<JumpInsnNode>();
        JumpInsnNode jmp = new JumpInsnNode(Opcodes.GOTO, null);
        mn.instructions.add(jmp);
        falseList.add(jmp);
        ASTUtils.setFalseList(node, falseList);
    }

    /**
     * Assumes top of stack contains two strings
     */
    private void handleStringOperator(ASTNode node, Operator op) throws ASTVisitorException {
        if (op.equals(Operator.PLUS)) {
            mn.instructions.add(new TypeInsnNode(Opcodes.NEW, "java/lang/StringBuilder"));
            mn.instructions.add(new InsnNode(Opcodes.DUP));
            mn.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V"));
            mn.instructions.add(new InsnNode(Opcodes.SWAP));
            mn.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;"));
            mn.instructions.add(new InsnNode(Opcodes.SWAP));
            mn.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;"));
            mn.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;"));
        } else if (op.isRelational()) {
            LabelNode trueLabelNode = new LabelNode();
            switch (op) {
                case EQUAL:
                    mn.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z"));
                    mn.instructions.add(new JumpInsnNode(Opcodes.IFNE, trueLabelNode));
                    break;
                case NOT_EQUAL:
                    mn.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z"));
                    mn.instructions.add(new JumpInsnNode(Opcodes.IFEQ, trueLabelNode));
                    break;
                default:
                    ASTUtils.error(node, "Operator not supported on strings");
                    break;
            }
            mn.instructions.add(new InsnNode(Opcodes.ICONST_0));
            LabelNode endLabelNode = new LabelNode();
            mn.instructions.add(new JumpInsnNode(Opcodes.GOTO, endLabelNode));
            mn.instructions.add(trueLabelNode);
            mn.instructions.add(new InsnNode(Opcodes.ICONST_1));
            mn.instructions.add(endLabelNode);
        } else {
            ASTUtils.error(node, "Operator not recognized");
        }
    }

    private void handleNumberOperator(ASTNode node, Operator op, Type type) throws ASTVisitorException {
        if (op.equals(Operator.PLUS)) {
            mn.instructions.add(new InsnNode(type.getOpcode(Opcodes.IADD)));
        } else if (op.equals(Operator.MINUS)) {
            mn.instructions.add(new InsnNode(type.getOpcode(Opcodes.ISUB)));
        } else if (op.equals(Operator.MULTIPLY)) {
            mn.instructions.add(new InsnNode(type.getOpcode(Opcodes.IMUL)));
        } else if (op.equals(Operator.DIVISION)) {
            mn.instructions.add(new InsnNode(type.getOpcode(Opcodes.IDIV)));
        } else if (op.isRelational()) {
            if (type.equals(Type.DOUBLE_TYPE)) {
                mn.instructions.add(new InsnNode(Opcodes.DCMPG));
                JumpInsnNode jmp = null;
                switch (op) {
                    case EQUAL:
                        jmp = new JumpInsnNode(Opcodes.IFEQ, null);
                        mn.instructions.add(jmp);
                        break;
                    case NOT_EQUAL:
                        jmp = new JumpInsnNode(Opcodes.IFNE, null);
                        mn.instructions.add(jmp);
                        break;
                    case GREATER:
                        jmp = new JumpInsnNode(Opcodes.IFGT, null);
                        mn.instructions.add(jmp);
                        break;
                    case GREATER_EQUAL:
                        jmp = new JumpInsnNode(Opcodes.IFGE, null);
                        mn.instructions.add(jmp);
                        break;
                    case LESS:
                        jmp = new JumpInsnNode(Opcodes.IFLT, null);
                        mn.instructions.add(jmp);
                        break;
                    case LESS_EQUAL:
                        jmp = new JumpInsnNode(Opcodes.IFLE, null);
                        mn.instructions.add(jmp);
                        break;
                    default:
                        ASTUtils.error(node, "Operator not supported");
                        break;
                }
                mn.instructions.add(new InsnNode(Opcodes.ICONST_0));
                LabelNode endLabelNode = new LabelNode();
                mn.instructions.add(new JumpInsnNode(Opcodes.GOTO, endLabelNode));
                LabelNode trueLabelNode = new LabelNode();
                jmp.label = trueLabelNode;
                mn.instructions.add(trueLabelNode);
                mn.instructions.add(new InsnNode(Opcodes.ICONST_1));
                mn.instructions.add(endLabelNode);
            } else if (type.equals(Type.INT_TYPE)) {
                LabelNode trueLabelNode = new LabelNode();
                switch (op) {
                    case EQUAL:
                        mn.instructions.add(new JumpInsnNode(Opcodes.IF_ICMPEQ, trueLabelNode));
                        break;
                    case NOT_EQUAL:
                        mn.instructions.add(new JumpInsnNode(Opcodes.IF_ICMPNE, trueLabelNode));
                        break;
                    case GREATER:
                        mn.instructions.add(new JumpInsnNode(Opcodes.IF_ICMPGT, trueLabelNode));
                        break;
                    case GREATER_EQUAL:
                        mn.instructions.add(new JumpInsnNode(Opcodes.IF_ICMPGE, trueLabelNode));
                        break;
                    case LESS:
                        mn.instructions.add(new JumpInsnNode(Opcodes.IF_ICMPLT, trueLabelNode));
                        break;
                    case LESS_EQUAL:
                        mn.instructions.add(new JumpInsnNode(Opcodes.IF_ICMPLE, trueLabelNode));
                        break;
                    default:
                        break;
                }
                mn.instructions.add(new InsnNode(Opcodes.ICONST_0));
                LabelNode endLabelNode = new LabelNode();
                mn.instructions.add(new JumpInsnNode(Opcodes.GOTO, endLabelNode));
                mn.instructions.add(trueLabelNode);
                mn.instructions.add(new InsnNode(Opcodes.ICONST_1));
                mn.instructions.add(endLabelNode);
            } else {
                ASTUtils.error(node, "Cannot compare such types.");
            }
        } else {
            ASTUtils.error(node, "Operator not recognized.");
        }
    }

    @Override
    public void visit(ReturnStatement node) throws ASTVisitorException {
        node.getExpr().accept(this);
        Type type = ASTUtils.getSafeType(node.getExpr());
        mn.instructions.add(new InsnNode(type.getOpcode(Opcodes.IRETURN)));
    }

    @Override
    public void visit(TypeSpecifierStatement node) throws ASTVisitorException {
        //nothing to do here
    }

    @Override
    public void visit(PlainStatement node) throws ASTVisitorException {
        //just accept the expression
        node.getExp().accept(this);
    }

    @Override
    public void visit(WriteStatement node) throws ASTVisitorException {
        mn.instructions.add(new FieldInsnNode(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"));
        node.getExpression().accept(this);
        Type type = ASTUtils.getSafeType(node.getExpression());
        mn.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(" + type + ")V"));
    }

    @Override
    public void visit(DotExpression node) throws ASTVisitorException {
        node.getExp().accept(this);
        String className = ASTUtils.getClassBelongsName(node);
        SymTable<SymTableEntry> symTable = ASTUtils.getSafeEnv(node);
        id = node.getIdentifier().getIdentifier();   //this is the field name
        SymTableEntry entry = symTable.lookup(id);
        Type type = entry.getType();
        mn.instructions.add(new FieldInsnNode(Opcodes.GETFIELD, className, id, type.toString()));
    }

    @Override
    public void visit(NullExpression node) throws ASTVisitorException {
        //push a null reference onto the stack
        mn.instructions.add(new LdcInsnNode("null"));
    }

    @Override
    public void visit(NewExpression node) throws ASTVisitorException {
        String identifier = node.getIdentifier().getIdentifier();
        SymTable<SymTableEntry> symTable = Registry.getInstance().lookup(identifier);
        if (symTable != null) {
            for (Expression e : node.getList()) {
                e.accept(this);
            }
            mn.instructions.add(new TypeInsnNode(Opcodes.NEW, identifier));
            mn.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, identifier, "<init>", "(DD)V"));
        }
    }

    @Override
    public void visit(DotExpressionList node) throws ASTVisitorException {
        node.getExp().accept(this);
        String className = ASTUtils.getClassBelongsName(node);

        SymTable<SymTableEntry> symTable = ASTUtils.getSafeEnv(node);
        id = node.getIdentifier().getIdentifier();   //this is the field name
        SymTableEntry entry = symTable.lookup(id);
        String fooType = entry.getType().toString();
        String parametersTypes = "";
        for (Expression e : node.getList()) {
            e.accept(this);
            parametersTypes += ASTUtils.getType(e).toString();
        }
        mn.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, className, id, "(" + parametersTypes + ")" + fooType));
    }

    @Override
    public void visit(ParameterDeclaration node) throws ASTVisitorException {
        String id = node.getIdentifier().getIdentifier();
        SymTable<SymTableEntry> symTable = ASTUtils.getSafeEnv(node);
        SymTableEntry symTableEntry = symTable.lookup(id);
        Type type = symTableEntry.getType();
        int opcode = type.getOpcode(Opcodes.ILOAD);
        int index = symTableEntry.getIndex();
        mn.instructions.add(new VarInsnNode(opcode, index));
    }

    @Override
    public void visit(ThisExpression node) throws ASTVisitorException {
        mn.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
    }

    @Override
    public void visit(FunctionCallExpression node) throws ASTVisitorException {
        String fooId = node.getIdentifier().getIdentifier();
        String fooParametersTypes = "";
        for (Expression e : node.getExprList()) {
            e.accept(this);
            fooParametersTypes += ASTUtils.getType(e).toString();
        }
        String fooClass = ASTUtils.getClassBelongsName(node);
        String fooReturnType = ASTUtils.getType(node).toString();
        mn.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, fooClass, fooId, "(" + fooParametersTypes + ")" + fooReturnType));
    }

}
