package submit;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import parser.CminusBaseVisitor;
import parser.CminusParser;
import submit.ast.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ASTVisitor extends CminusBaseVisitor<Node> {
    private final Logger LOGGER;
    private SymbolTable symbolTable;

    public ASTVisitor(Logger LOGGER) {
        this.LOGGER = LOGGER;
    }

    private VarType getVarType(CminusParser.TypeSpecifierContext ctx) {
        final String t = ctx.getText();
        return (t.equals("int")) ? VarType.INT : (t.equals("bool")) ? VarType.BOOL : VarType.CHAR;
    }

    @Override 
    public Node visitProgram(CminusParser.ProgramContext a_context) {
        symbolTable = new SymbolTable();
        List<Declaration> declarations = new ArrayList<>();
       for (CminusParser.DeclarationContext d : a_context.declaration()) {
           declarations.add((Declaration)visitDeclaration(d));
       }
        return new Program(declarations);
    }

    @Override public Node visitVarDeclaration(CminusParser.VarDeclarationContext ctx) {
        for (CminusParser.VarDeclIdContext v : ctx.varDeclId()) {
            String id = v.ID().getText();
            LOGGER.fine("Var ID: " + id);
        }

       VarType type = getVarType(ctx.typeSpecifier());
       List<String> ids = new ArrayList<>();
       List<Integer> arraySizes = new ArrayList<>();
       for (CminusParser.VarDeclIdContext v : ctx.varDeclId()) {
           String id = v.ID().getText();
           ids.add(id);
           symbolTable.addSymbol(id, new SymbolInfo(id, type, false));
           if (v.NUMCONST() != null) {
               arraySizes.add(Integer.parseInt(v.NUMCONST().getText()));
           } else {
               arraySizes.add(-1);
           }
       }
       final boolean isStatic = false;
       return new VarDeclaration(type, ids, arraySizes, isStatic);
    }

//    @Override public Node visitReturnStmt(CminusParser.ReturnStmtContext ctx) {
//        if (ctx.expression() != null) {
//            return new Return((Expression) visitExpression(ctx.expression()));
//        }
//        return new Return(null);
//    }

//    @Override public Node visitConstant(CminusParser.ConstantContext ctx) {
//        final Node node;
//        if (ctx.NUMCONST() != null) {
//            node = new NumConstant(Integer.parseInt(ctx.NUMCONST().getText()));
//        } else if (ctx.CHARCONST() != null) {
//            node = new CharConstant(ctx.CHARCONST().getText().charAt(0));
//        } else if (ctx.STRINGCONST() != null) {
//            node = new StringConstant(ctx.STRINGCONST().getText());
//        } else {
//            node = new BoolConstant(ctx.getText().equals("true"));
//        }
//        return node;
//    }

    // TODO Uncomment and implement whatever methods make sense
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
   //@Override public T visitDeclaration(CminusParser.DeclarationContext ctx) { return visitChildren(ctx); }
   /**
    {@inheritDoc}

 <p>The default implementation returns the result of calling
    {@link #visitChildren} on {@code ctx}.</p>
    
//    @Override public T visitVarDeclId(CminusParser.VarDeclIdContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
    @Override 
    public Node visitFunDeclaration(CminusParser.FunDeclarationContext ctx) {
        //set the type
        VarType type;
        if(ctx.children.get(0).getText().equals("void")) type = null;
        else type = getVarType(ctx.typeSpecifier());

        //set the ID
        String id = ctx.ID().getText();

        //get the parameters
        for(int i = 0; i < ctx.children.size(); i++) {
            System.out.printf("%d: %s\n", i, ctx.children.get(i).getText());
        }

        
        return visitChildren(ctx); 
    }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitTypeSpecifier(CminusParser.TypeSpecifierContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
    @Override
    public Node visitParam(CminusParser.ParamContext ctx) { 
        VarType type = getVarType(ctx.typeSpecifier());
        String id = ctx.paramId().getText();
        
    }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitParamId(CminusParser.ParamIdContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitStatement(CminusParser.StatementContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitCompoundStmt(CminusParser.CompoundStmtContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitExpressionStmt(CminusParser.ExpressionStmtContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitIfStmt(CminusParser.IfStmtContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitWhileStmt(CminusParser.WhileStmtContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitBreakStmt(CminusParser.BreakStmtContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitExpression(CminusParser.ExpressionContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitSimpleExpression(CminusParser.SimpleExpressionContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitOrExpression(CminusParser.OrExpressionContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitAndExpression(CminusParser.AndExpressionContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitUnaryRelExpression(CminusParser.UnaryRelExpressionContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitRelExpression(CminusParser.RelExpressionContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitRelop(CminusParser.RelopContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitSumExpression(CminusParser.SumExpressionContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitSumop(CminusParser.SumopContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitTermExpression(CminusParser.TermExpressionContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitMulop(CminusParser.MulopContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitUnaryExpression(CminusParser.UnaryExpressionContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitUnaryop(CminusParser.UnaryopContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitFactor(CminusParser.FactorContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitMutable(CminusParser.MutableContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitImmutable(CminusParser.ImmutableContext ctx) { return visitChildren(ctx); }
//    /**
//     * {@inheritDoc}
//     *
//     * <p>The default implementation returns the result of calling
//     * {@link #visitChildren} on {@code ctx}.</p>
//     */
//    @Override public T visitCall(CminusParser.CallContext ctx) { return visitChildren(ctx); }

}
