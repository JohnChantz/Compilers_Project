
//----------------------------------------------------
// The following code was generated by CUP v0.11a beta 20060608
// Mon Mar 21 15:37:23 EET 2016
//----------------------------------------------------

import java_cup.runtime.Symbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** CUP v0.11a beta 20060608 generated parser.
  * @version Mon Mar 21 15:37:23 EET 2016
  */
public class parser extends java_cup.runtime.lr_parser {

  /** Default constructor. */
  public parser() {super();}

  /** Constructor which sets the default scanner. */
  public parser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public parser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\021\000\002\002\004\000\002\005\002\000\002\005" +
    "\003\000\002\004\003\000\002\004\004\000\002\003\007" +
    "\000\002\003\006\000\002\002\003\000\002\002\003\000" +
    "\002\002\003\000\002\002\003\000\002\002\005\000\002" +
    "\002\005\000\002\002\005\000\002\002\005\000\002\002" +
    "\005\000\002\002\004" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\040\000\010\002\000\004\006\005\004\001\002\000" +
    "\004\006\037\001\002\000\010\002\ufffe\004\ufffe\005\ufffe" +
    "\001\002\000\004\011\013\001\002\000\004\002\012\001" +
    "\002\000\010\002\uffff\004\006\005\004\001\002\000\010" +
    "\002\ufffd\004\ufffd\005\ufffd\001\002\000\004\002\001\001" +
    "\002\000\016\004\017\006\022\015\020\017\014\020\015" +
    "\021\021\001\002\000\016\007\ufffa\010\ufffa\012\ufffa\013" +
    "\ufffa\014\ufffa\015\ufffa\001\002\000\016\007\ufff9\010\ufff9" +
    "\012\ufff9\013\ufff9\014\ufff9\015\ufff9\001\002\000\014\010" +
    "\036\012\030\013\026\014\025\015\027\001\002\000\016" +
    "\007\ufff7\010\ufff7\012\ufff7\013\ufff7\014\ufff7\015\ufff7\001" +
    "\002\000\016\004\017\006\022\015\020\017\014\020\015" +
    "\021\021\001\002\000\016\007\ufff8\010\ufff8\012\ufff8\013" +
    "\ufff8\014\ufff8\015\ufff8\001\002\000\016\004\017\006\022" +
    "\015\020\017\014\020\015\021\021\001\002\000\014\007" +
    "\024\012\030\013\026\014\025\015\027\001\002\000\016" +
    "\007\ufff6\010\ufff6\012\ufff6\013\ufff6\014\ufff6\015\ufff6\001" +
    "\002\000\016\004\017\006\022\015\020\017\014\020\015" +
    "\021\021\001\002\000\016\004\017\006\022\015\020\017" +
    "\014\020\015\021\021\001\002\000\016\004\017\006\022" +
    "\015\020\017\014\020\015\021\021\001\002\000\016\004" +
    "\017\006\022\015\020\017\014\020\015\021\021\001\002" +
    "\000\016\007\ufff3\010\ufff3\012\ufff3\013\ufff3\014\ufff3\015" +
    "\ufff3\001\002\000\016\007\ufff4\010\ufff4\012\030\013\026" +
    "\014\ufff4\015\ufff4\001\002\000\016\007\ufff2\010\ufff2\012" +
    "\ufff2\013\ufff2\014\ufff2\015\ufff2\001\002\000\016\007\ufff5" +
    "\010\ufff5\012\030\013\026\014\ufff5\015\ufff5\001\002\000" +
    "\016\007\ufff1\010\ufff1\012\ufff1\013\ufff1\014\ufff1\015\ufff1" +
    "\001\002\000\010\002\ufffb\004\ufffb\005\ufffb\001\002\000" +
    "\016\004\017\006\022\015\020\017\014\020\015\021\021" +
    "\001\002\000\014\007\041\012\030\013\026\014\025\015" +
    "\027\001\002\000\004\010\042\001\002\000\010\002\ufffc" +
    "\004\ufffc\005\ufffc\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\040\000\010\003\004\004\007\005\006\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\004\003\010\001\001\000\002\001\001\000" +
    "\002\001\001\000\004\002\015\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\004\002\034\001\001\000\002\001\001\000\004\002\022" +
    "\001\001\000\002\001\001\000\002\001\001\000\004\002" +
    "\033\001\001\000\004\002\032\001\001\000\004\002\031" +
    "\001\001\000\004\002\030\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\004\002\037\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$parser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$parser$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$parser$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 0;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}



    private static final Logger LOGGER = LoggerFactory.getLogger(parser.class);

    /** Report a non fatal error (or warning).
     *
     * @param message an error message.
     * @param info    an extra object reserved for use by specialized subclasses.
     */
    public void report_error(String message, Object info)
    {
        String error = "";
        if (!(info instanceof Symbol))
            info = cur_token;
        if(info instanceof Symbol) {
            int line = ((Symbol)info).left;
            int column = ((Symbol)info).right;
            error = line  + ":" + column;
        }
        error += ": " + message;
        LOGGER.error(error);
    }

    /** Report a fatal error.
     *
     * @param message an error message.
     * @param info    an extra object reserved for use by specialized subclasses.
     */
    public void report_fatal_error(String   message, Object   info) throws java.lang.Exception
    {
        /* stop parsing (not really necessary since we throw an exception, but) */
        done_parsing();

        /* use the normal error message reporting to put out the message */
        report_error(message, info);

        /* throw an exception */
        throw new Exception("Can't recover from previous error(s)");
    }

    public int getLine() {
        Symbol symbol = (Symbol) cur_token;
        return symbol.left;
    }

    public int getColumn() {
        Symbol symbol = (Symbol) cur_token;
        return symbol.right;
    }

}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$parser$actions {
  private final parser parser;

  /** Constructor */
  CUP$parser$actions(parser parser) {
    this.parser = parser;
  }

  /** Method with the actual generated action code. */
  public final java_cup.runtime.Symbol CUP$parser$do_action(
    int                        CUP$parser$act_num,
    java_cup.runtime.lr_parser CUP$parser$parser,
    java.util.Stack            CUP$parser$stack,
    int                        CUP$parser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$parser$result;

      /* select the action based on the action number */
      switch (CUP$parser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // Expr ::= MINUS Expr 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("Expr",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // Expr ::= Expr DIVISION Expr 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("Expr",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // Expr ::= Expr TIMES Expr 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("Expr",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // Expr ::= Expr MINUS Expr 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("Expr",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // Expr ::= Expr PLUS Expr 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("Expr",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // Expr ::= LPAREN Expr RPAREN 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("Expr",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // Expr ::= IDENTIFIER 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("Expr",0, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // Expr ::= STRING_LITERAL 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("Expr",0, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // Expr ::= DOUBLE_LITERAL 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("Expr",0, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // Expr ::= INTEGER_LITERAL 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("Expr",0, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // Stmt ::= IDENTIFIER EQ Expr SEMICOLON 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("Stmt",1, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-3)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // Stmt ::= PRINT LPAREN Expr RPAREN SEMICOLON 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("Stmt",1, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-4)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // StmtList ::= StmtList Stmt 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("StmtList",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // StmtList ::= Stmt 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("StmtList",2, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // CompUnit ::= StmtList 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("CompUnit",3, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // CompUnit ::= 
            {
              Object RESULT =null;

              CUP$parser$result = parser.getSymbolFactory().newSymbol("CompUnit",3, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // $START ::= CompUnit EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		Object start_val = (Object)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		RESULT = start_val;
              CUP$parser$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$parser$parser.done_parsing();
          return CUP$parser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}
