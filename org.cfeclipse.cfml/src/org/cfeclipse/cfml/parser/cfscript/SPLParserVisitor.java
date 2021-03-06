/* Generated By:JJTree: Do not edit this line. C:/Program Files/Eclipse/workspace/CFELive/src/com/rohanclan/cfml/parser/cfscript\SPLParserVisitor.java */

package org.cfeclipse.cfml.parser.cfscript;

public interface SPLParserVisitor
{
  public Object visit(SimpleNode node, Object data);
  public Object visit(ASTCompilationUnit node, Object data);
  public Object visit(ASTReturnStatement node, Object data);
  public Object visit(ASTFunctionDeclaration node, Object data);
  public Object visit(ASTVarDeclaration node, Object data);
  public Object visit(ASTAssignment node, Object data);
  public Object visit(ASTOrNode node, Object data);
  public Object visit(ASTAndNode node, Object data);
  public Object visit(ASTBitwiseOrNode node, Object data);
  public Object visit(ASTBitwiseXorNode node, Object data);
  public Object visit(ASTBitwiseAndNode node, Object data);
  public Object visit(ASTNENode node, Object data);
  public Object visit(ASTEQNode node, Object data);
  public Object visit(ASTStrEQNode node, Object data);
  public Object visit(ASTStrNEQNode node, Object data);
  public Object visit(ASTLTNode node, Object data);
  public Object visit(ASTGTNode node, Object data);
  public Object visit(ASTLENode node, Object data);
  public Object visit(ASTGENode node, Object data);
  public Object visit(ASTAddNode node, Object data);
  public Object visit(ASTSubtractNode node, Object data);
  public Object visit(ASTMulNode node, Object data);
  public Object visit(ASTDivNode node, Object data);
  public Object visit(ASTModNode node, Object data);
  public Object visit(ASTStructRefNode node, Object data);
  public Object visit(ASTCFCMethodCallNode node, Object data);
  public Object visit(ASTId node, Object data);
  public Object visit(ASTStringLiteral node, Object data);
  public Object visit(ASTIntLiteral node, Object data);
  public Object visit(ASTFloatingPointLiteral node, Object data);
  public Object visit(ASTTrueNode node, Object data);
  public Object visit(ASTFalseNode node, Object data);
  public Object visit(ASTForInStatement node, Object data);
  public Object visit(ASTForStatementNode node, Object data);
  public Object visit(ASTFunctionCallNode node, Object data);
  public Object visit(ASTParameterList node, Object data);
  public Object visit(ASTBlock node, Object data);
  public Object visit(ASTStatementExpression node, Object data);
  public Object visit(ASTIfStatement node, Object data);
  public Object visit(ASTWhileStatement node, Object data);
  public Object visit(ASTDoStatement node, Object data);
  public Object visit(ASTSwitchStatement node, Object data);
  public Object visit(ASTSwitchLabel node, Object data);
  public Object visit(ASTTryStatement node, Object data);
}
