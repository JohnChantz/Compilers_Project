package ast;

/**
 * Abstract syntax tree visitor.
 */
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
    
   void visit(DoWhileStatement node) throws ASTVisitorException;
   
   void visit(IfStatement node) throws ASTVisitorException;

   void visit(IfElseStatement node) throws ASTVisitorException;

   void visit(ExpressionList node) throws ASTVisitorException;

   void visit(ContinueStmt node) throws ASTVisitorException;

   void visit(BreakStmt node) throws ASTVisitorException;

   void visit(ReturnStmt node) throws ASTVisitorException;

   void visit(TypeSpecifierStmt node) throws ASTVisitorException;

   void visit(TypeSpecifier node) throws ASTVisitorException;

   void visit(FunctionDefinition node) throws ASTVisitorException;

   void visit(FieldOrFunctionDefinition node) throws ASTVisitorException;

   void visit(FieldOrFunctionDefinitionList node) throws ASTVisitorException;

   void visit(FieldDefinition node) throws ASTVisitorException;

   void visit(ClassDefinition node) throws ASTVisitorException;

}
