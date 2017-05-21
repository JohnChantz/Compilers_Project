package org.hua.ast;

public interface ASTVisitor {

    void visit(CompilationUnit node) throws ASTVisitorException;

    void visit(AssignmentStatement node) throws ASTVisitorException;

    void visit(CompoundStatement node) throws ASTVisitorException;

    void visit(BinaryExpression node) throws ASTVisitorException;

    void visit(UnaryExpression node) throws ASTVisitorException;

    void visit(IdentifierExpression node) throws ASTVisitorException;

    void visit(DoubleLiteralExpression node) throws ASTVisitorException;

    void visit(IntegerLiteralExpression node) throws ASTVisitorException;

    void visit(StringLiteralExpression node) throws ASTVisitorException;

    void visit(ParenthesisExpression node) throws ASTVisitorException;

    void visit(WhileStatement node) throws ASTVisitorException;

    void visit(IfStatement node) throws ASTVisitorException;

    void visit(IfElseStatement node) throws ASTVisitorException;

    void visit(ContinueStatement node) throws ASTVisitorException;

    void visit(BreakStatement node) throws ASTVisitorException;

    void visit(ReturnStatement node) throws ASTVisitorException;

    void visit(TypeSpecifierStatement node) throws ASTVisitorException;

    void visit(FunctionDefinition node) throws ASTVisitorException;

    void visit(FieldOrFunctionDefinition node) throws ASTVisitorException;

    void visit(FieldDefinition node) throws ASTVisitorException;

    void visit(ClassDefinition node) throws ASTVisitorException;

    void visit(PlainStatement node) throws  ASTVisitorException;

    void visit(DoWhileStatement node) throws ASTVisitorException;

    void visit(Definitions node) throws ASTVisitorException;

    void visit(WriteStatement node) throws ASTVisitorException;

    void visit(DotExpression node) throws ASTVisitorException;

    void visit(NullExpression node) throws ASTVisitorException;

    void visit(NewExpression node) throws ASTVisitorException;

    void visit(DotExpressionList node) throws ASTVisitorException;

    void visit(ParameterDeclaration node) throws ASTVisitorException;

    void visit(PrintStatement node) throws ASTVisitorException;

    void visit(ThisExpression node) throws ASTVisitorException;
}
