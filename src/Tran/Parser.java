package Tran;

import AST.*;

import java.util.*;

public class Parser {

    private final TokenManager tokenManager;
    private final TranNode root;

    public Parser(TranNode top, List<Token> tokens) {
        root = top;
        tokenManager = new TokenManager(tokens);
    }

    public void Tran() throws SyntaxErrorException {

        while (!tokenManager.done()) {

            Optional<InterfaceNode> i = parseInterface();
            while (i.isPresent()) {
                root.Interfaces.add(i.get());
                i = parseInterface();
            }

            Optional<ClassNode> c = parseClass();
            while (c.isPresent()) {
                root.Classes.add(c.get());
                c = parseClass();
            }
            consumeWhitespace();
        }
    }

    //Class =  "class" IDENTIFIER ( "implements" IDENTIFIER ( "," IDENTIFIER )* )? NEWLINE INDENT ( Constructor | MethodDeclaration | Member )* DEDENT
    private Optional<ClassNode> parseClass() throws SyntaxErrorException {

        if (tokenManager.matchAndRemove(Token.TokenTypes.CLASS).isPresent()) {

            ClassNode c = new ClassNode();

            Optional<Token> name = tokenManager.matchAndRemove(Token.TokenTypes.WORD);
            if (name.isEmpty())
                throw new SyntaxErrorException("Class without identifier", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());

            c.name = name.get().getValue();

            if (tokenManager.matchAndRemove(Token.TokenTypes.IMPLEMENTS).isPresent()) {

                Optional<Token> interfaceName = tokenManager.matchAndRemove(Token.TokenTypes.WORD);
                if (interfaceName.isEmpty())
                    throw new SyntaxErrorException("Implements without interface", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());

                c.interfaces.add(interfaceName.get().getValue());

                while (tokenManager.matchAndRemove(Token.TokenTypes.COMMA).isPresent()) {
                    interfaceName = tokenManager.matchAndRemove(Token.TokenTypes.WORD);
                    if (interfaceName.isEmpty())
                        throw new SyntaxErrorException("Missing interface name after comma", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());
                    c.interfaces.add(interfaceName.get().getValue());
                }
            }

            requireNewline();
            requireIndent();

            while (checkDedent() && !tokenManager.done()) {

                Optional<ConstructorNode> constructorNode = parseConstructor();
                constructorNode.ifPresent(node -> c.constructors.add(node));

                Optional<MemberNode> memberNode = parseMember();
                memberNode.ifPresent(node -> c.members.add(node));

                Optional<MethodDeclarationNode> methodDeclarationNode = parseMethodDeclaration();
                methodDeclarationNode.ifPresent(node -> c.methods.add(node));

                consumeWhitespace();

            }
            return Optional.of(c);
        } else
            return Optional.empty();
    }

    //Constructor = "construct" "(" ParameterVariableDeclarations ")" NEWLINE MethodBody
    private Optional<ConstructorNode> parseConstructor() throws SyntaxErrorException {

        if (tokenManager.matchAndRemove(Token.TokenTypes.CONSTRUCT).isPresent()) {

            ConstructorNode c = new ConstructorNode();

            if (tokenManager.matchAndRemove(Token.TokenTypes.LPAREN).isEmpty())
                throw new SyntaxErrorException("LPAREN expected in constructor header", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());

            Optional<List<VariableDeclarationNode>> parameters = parseVariableDeclarations();
            parameters.ifPresent(variableDeclarationNodes -> c.parameters.addAll(variableDeclarationNodes));

            if (tokenManager.matchAndRemove(Token.TokenTypes.RPAREN).isEmpty())
                throw new SyntaxErrorException("RPAREN expected in constructor header", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());

            requireNewline();

            //MethodBody = INDENT ( VariableDeclarations )*  Statement* DEDENT

            requireIndent();

            while (checkDedent() && !tokenManager.done()) {

                Optional<List<VariableDeclarationNode>> locals = parseVariableDeclarations();
                locals.ifPresent(variableDeclarationNodes -> c.locals.addAll(variableDeclarationNodes));

                Optional<StatementNode> statementNode = parseStatement();
                statementNode.ifPresent(node -> c.statements.add(node));

                consumeWhitespace();

            }

            return Optional.of(c);
        }
        return Optional.empty();
    }

    //Member = VariableDeclarations
    private Optional<MemberNode> parseMember() throws SyntaxErrorException {

        MemberNode m = new MemberNode();

        Optional<VariableDeclarationNode> vd = parseVariableDeclaration();
        if (vd.isPresent())
            m.declaration = vd.get();
        else
            return Optional.empty();

        return Optional.of(m);
    }

    //MethodDeclaration = "private"? "shared"? MethodHeader NEWLINE MethodBody
    private Optional<MethodDeclarationNode> parseMethodDeclaration() throws SyntaxErrorException {

        MethodDeclarationNode md = new MethodDeclarationNode();

        if (tokenManager.matchAndRemove(Token.TokenTypes.PRIVATE).isPresent())
            md.isPrivate = true;
        if (tokenManager.matchAndRemove(Token.TokenTypes.SHARED).isPresent())
            md.isShared = true;

        Optional<MethodHeaderNode> mhn = parseMethodHeader();
        if (mhn.isPresent()) {
            md.name = mhn.get().name;
            md.parameters = mhn.get().parameters;
            md.returns = mhn.get().returns;
        } else
            return Optional.empty();

        //MethodBody = INDENT ( VariableDeclarations )*  Statement* DEDENT

        requireIndent();

        while (checkDedent() && !tokenManager.done()) {

            Optional<List<VariableDeclarationNode>> locals = parseVariableDeclarations();
            locals.ifPresent(variableDeclarationNodes -> md.locals.addAll(variableDeclarationNodes));

            Optional<StatementNode> statementNode = parseStatement();
            statementNode.ifPresent(node -> md.statements.add(node));

            consumeWhitespace();

        }

        return Optional.of(md);
    }

    //MethodCall = (VariableReference ( "," VariableReference )* "=")? MethodCallExpression NEWLINE
    private Optional<MethodCallStatementNode> parseMethodCall() throws SyntaxErrorException {

        Optional<List<VariableReferenceNode>> returnValuesTemp = Optional.of(new LinkedList<>());

        Optional<VariableReferenceNode> varRef = parseVariableReference();
        varRef.ifPresent(variableReferenceNode -> returnValuesTemp.get().add(variableReferenceNode));

        while (tokenManager.matchAndRemove(Token.TokenTypes.COMMA).isPresent()) {

            varRef = parseVariableReference();
            if (varRef.isPresent())
                returnValuesTemp.get().add(varRef.get());
            else
                throw new SyntaxErrorException("Variable reference expected after comma in method call", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());

        }

        if (!returnValuesTemp.get().isEmpty() && tokenManager.matchAndRemove(Token.TokenTypes.ASSIGN).isEmpty())
            throw new SyntaxErrorException("Assignment expected", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());

        Optional<MethodCallExpressionNode> mce = parseMethodCallExpression();
        if (mce.isEmpty())
            return Optional.empty();

        requireNewline();

        MethodCallStatementNode mcs = new MethodCallStatementNode(mce.get());
        mcs.returnValues = returnValuesTemp.get();

        return Optional.of(mcs);

    }

    //MethodCallExpression =  (IDENTIFIER ".")? IDENTIFIER "(" (Expression ("," Expression )* )? ")"
    private Optional<MethodCallExpressionNode> parseMethodCallExpression() throws SyntaxErrorException {

        if (!tokenManager.nextTwoTokensMatch(Token.TokenTypes.WORD, Token.TokenTypes.DOT) && !tokenManager.nextTwoTokensMatch(Token.TokenTypes.WORD, Token.TokenTypes.LPAREN))
            return Optional.empty();

        Optional<Token> name = tokenManager.matchAndRemove(Token.TokenTypes.WORD);
        MethodCallExpressionNode mce = new MethodCallExpressionNode();
        if (tokenManager.matchAndRemove(Token.TokenTypes.DOT).isPresent()) {

            mce.objectName = Optional.of(name.get().getValue());
            name = tokenManager.matchAndRemove(Token.TokenTypes.WORD);
            if (name.isEmpty())
                throw new SyntaxErrorException("Method identifier expected", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());
            mce.methodName = name.get().getValue();

        } else {
            mce.objectName = Optional.empty();
            mce.methodName = name.get().getValue();
        }

        if (tokenManager.matchAndRemove(Token.TokenTypes.LPAREN).isEmpty())
            throw new SyntaxErrorException("LPAREN expected after method name", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());

        Optional<ExpressionNode> expression = parseExpression();
        if (expression.isPresent()) {

            mce.parameters.add(expression.get());

            while (tokenManager.matchAndRemove(Token.TokenTypes.COMMA).isPresent()) {

                expression = parseExpression();
                if (expression.isEmpty())
                    throw new SyntaxErrorException("Expression expected after comma", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());
                mce.parameters.add(expression.get());

            }
        }

        if (tokenManager.matchAndRemove(Token.TokenTypes.RPAREN).isEmpty())
            throw new SyntaxErrorException("RPAREN expected after methodname", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());

        return Optional.of(mce);
    }

    //Statements = INDENT Statement*  DEDENT
    private Optional<List<StatementNode>> parseStatements() throws SyntaxErrorException {
        List<StatementNode> statements = new ArrayList<StatementNode>();

        requireIndent();

        while (checkDedent() && !tokenManager.done()) {

            Optional<StatementNode> s = parseStatement();
            if (s.isPresent())
                statements.add(s.get());
            else
                break;

            consumeWhitespace();

        }
        if (statements.isEmpty())
            return Optional.empty();
        else
            return Optional.of(statements);
    }

    //Statement = If | Loop | MethodCall | Assignment
    private Optional<StatementNode> parseStatement() throws SyntaxErrorException {

        Optional<IfNode> ifNode = parseIf();
        if (ifNode.isPresent())
            return Optional.of(ifNode.get());

        Optional<LoopNode> loopNode = parseLoop();
        if (loopNode.isPresent())
            return Optional.of(loopNode.get());

        return disambiguate();

    }

    private Optional<StatementNode> disambiguate() throws SyntaxErrorException {

        Optional<MethodCallExpressionNode> mce = parseMethodCallExpression();

        if (mce.isPresent()) {

            MethodCallStatementNode mcs = new MethodCallStatementNode(mce.get());

            return Optional.of(mcs);

        }

        Optional<AssignmentNode> assignmentNode = parseAssignment();
        if (assignmentNode.isPresent()) {
            if (assignmentNode.get().expression.getClass().equals(MethodCallExpressionNode.class)) {
                MethodCallStatementNode methodCall = new MethodCallStatementNode((MethodCallExpressionNode) assignmentNode.get().expression);
                methodCall.returnValues.add(assignmentNode.get().target);
                return Optional.of(methodCall);
            }
            return Optional.of(assignmentNode.get());
        }

        Optional<MethodCallStatementNode> methodCall = parseMethodCall();
        if (methodCall.isPresent())
            return Optional.of(methodCall.get());

        return Optional.empty();

    }

    //Assignment = VariableReference "=" Expression NEWLINE
    private Optional<AssignmentNode> parseAssignment() throws SyntaxErrorException {

        if (!tokenManager.nextTwoTokensMatch(Token.TokenTypes.WORD, Token.TokenTypes.ASSIGN))
            return Optional.empty();

        AssignmentNode assignmentNode = new AssignmentNode();

        Optional<VariableReferenceNode> varRef = parseVariableReference();
        varRef.ifPresent(variableReferenceNode -> assignmentNode.target = variableReferenceNode);

        tokenManager.matchAndRemove(Token.TokenTypes.ASSIGN);

        Optional<ExpressionNode> expression = parseExpression();
        if (expression.isPresent())
            assignmentNode.expression = expression.get();
        else
            throw new SyntaxErrorException("Missing expression", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());

        //requireNewline();

        return Optional.of(assignmentNode);

    }

    //If = "if" BoolExpTerm NEWLINE Statements ("else" NEWLINE (Statement | Statements))?
    private Optional<IfNode> parseIf() throws SyntaxErrorException {

        if (tokenManager.matchAndRemove(Token.TokenTypes.IF).isPresent()) {

            IfNode ifNode = new IfNode();

            Optional<ExpressionNode> BoolExpTerm = parseBooleanExpTerm();
            if (BoolExpTerm.isPresent())
                ifNode.condition = BoolExpTerm.get();
            else
                throw new SyntaxErrorException("Boolean condition expected", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());

            requireNewline();

            Optional<List<StatementNode>> ifStatements = parseStatements();
            if (ifStatements.isPresent())
                ifNode.statements = ifStatements.get();
            else
                throw new SyntaxErrorException("Statements expected in if body", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());

            if (tokenManager.matchAndRemove(Token.TokenTypes.ELSE).isPresent()) {

                requireNewline();

                ElseNode elseNode = new ElseNode();

                Optional<List<StatementNode>> elseStatements = parseStatements();
                if (elseStatements.isPresent())
                    elseNode.statements = elseStatements.get();
                else
                    throw new SyntaxErrorException("Statements expected in else body", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());

                ifNode.elseStatement = Optional.of(elseNode);

            } else
                ifNode.elseStatement = Optional.empty();
            return Optional.of(ifNode);
        } else
            return Optional.empty();
    }

    //Loop = "loop" (VariableReference "=" )?  ( BoolExpTerm ) NEWLINE Statements
    private Optional<LoopNode> parseLoop() throws SyntaxErrorException {

        if (tokenManager.matchAndRemove(Token.TokenTypes.LOOP).isPresent()) {

            LoopNode loopNode = new LoopNode();

            if (tokenManager.peek(0).get().getType().equals(Token.TokenTypes.WORD) && tokenManager.peek(1).get().getType().equals(Token.TokenTypes.ASSIGN) && tokenManager.peek(2).get().getType().equals(Token.TokenTypes.WORD)) {
                Optional<VariableReferenceNode> varRef = parseVariableReference();
                tokenManager.matchAndRemove(Token.TokenTypes.ASSIGN);
                loopNode.assignment = Optional.of(varRef.get());
            }

            Optional<ExpressionNode> BoolExpTerm = parseBooleanExpTerm();
            if (BoolExpTerm.isEmpty())
                throw new SyntaxErrorException("Boolean expression expected", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());

            loopNode.expression = BoolExpTerm.get();

            requireNewline();

            Optional<List<StatementNode>> statements = parseStatements();
            if (statements.isEmpty())
                throw new SyntaxErrorException("Statements expected in loop body", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());

            loopNode.statements.addAll(statements.get());

            return Optional.of(loopNode);

        } else
            return Optional.empty();
    }

    //BoolExpTerm = MethodCallExpression | (Expression ( "==" | "!=" | "<=" | ">=" | ">" | "<" ) Expression) | VariableReference
    private Optional<ExpressionNode> parseBooleanExpTerm() throws SyntaxErrorException {

        Optional<MethodCallExpressionNode> mce = parseMethodCallExpression();
        if (mce.isPresent())
            return Optional.of(mce.get());

        Optional<ExpressionNode> expression = parseExpression();
        if (expression.isPresent()) {
            CompareNode boolOp = new CompareNode();
            boolOp.left = expression.get();

            if (tokenManager.matchAndRemove(Token.TokenTypes.EQUAL).isPresent())
                boolOp.op = CompareNode.CompareOperations.eq;
            else if (tokenManager.matchAndRemove(Token.TokenTypes.LESSTHAN).isPresent())
                boolOp.op = CompareNode.CompareOperations.lt;
            else if (tokenManager.matchAndRemove(Token.TokenTypes.GREATERTHAN).isPresent())
                boolOp.op = CompareNode.CompareOperations.gt;
            else if (tokenManager.matchAndRemove(Token.TokenTypes.LESSTHANEQUAL).isPresent())
                boolOp.op = CompareNode.CompareOperations.le;
            else if (tokenManager.matchAndRemove(Token.TokenTypes.GREATERTHANEQUAL).isPresent())
                boolOp.op = CompareNode.CompareOperations.ge;
            else if (tokenManager.matchAndRemove(Token.TokenTypes.NOTEQUAL).isPresent())
                boolOp.op = CompareNode.CompareOperations.ne;
            else
                return expression;

            expression = parseExpression();
            if (expression.isPresent())
                boolOp.right = expression.get();
            else
                throw new SyntaxErrorException("Expression expected", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());

            return Optional.of(boolOp);
        }

        Optional<VariableReferenceNode> varRef = parseVariableReference();
        if (varRef.isPresent())
            return Optional.of(varRef.get());

        return Optional.empty();


    }

    //Expression = Term ( ("+"|"-") Term )*
    private Optional<ExpressionNode> parseExpression() throws SyntaxErrorException {

        Optional<ExpressionNode> expression = parseTerm();
        if (expression.isEmpty()) return Optional.empty();

        while (true) {
            MathOpNode.MathOperations op = null;
            if (tokenManager.matchAndRemove(Token.TokenTypes.PLUS).isPresent())
                op = MathOpNode.MathOperations.add;
            else if (tokenManager.matchAndRemove(Token.TokenTypes.MINUS).isPresent())
                op = MathOpNode.MathOperations.subtract;
            else break;

            MathOpNode mathOp = new MathOpNode();
            mathOp.op = op;
            mathOp.left = expression.get();
            Optional<ExpressionNode> right = parseExpression();
            if (right.isEmpty())
                throw new SyntaxErrorException("Expression expected", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());
            mathOp.right = right.get();
            expression = Optional.of(mathOp);
        }
        return expression;
    }

    //Term = Factor ( ("*"|"/"|"%") Factor )*
    private Optional<ExpressionNode> parseTerm() throws SyntaxErrorException {

        Optional<ExpressionNode> term = parseFactor();
        if (term.isEmpty()) return Optional.empty();

        while (true) {
            MathOpNode.MathOperations op = null;
            if (tokenManager.matchAndRemove(Token.TokenTypes.TIMES).isPresent())
                op = MathOpNode.MathOperations.multiply;
            else if (tokenManager.matchAndRemove(Token.TokenTypes.DIVIDE).isPresent())
                op = MathOpNode.MathOperations.divide;
            else if (tokenManager.matchAndRemove(Token.TokenTypes.MODULO).isPresent())
                op = MathOpNode.MathOperations.modulo;
            else break;

            MathOpNode mathOp = new MathOpNode();
            mathOp.op = op;
            mathOp.left = term.get();
            Optional<ExpressionNode> right = parseFactor();
            if (right.isEmpty())
                throw new SyntaxErrorException("Term expected", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());
            mathOp.right = right.get();
            term = Optional.of(mathOp);
        }
        return term;
    }

    //Factor = NUMBER | VariableReference |  STRINGLITERAL | CHARACTERLITERAL | MethodCallExpression | "(" Expression ")" | "new" IDENTIFIER "(" (Expression ("," Expression )*)? ")"
    private Optional<ExpressionNode> parseFactor() throws SyntaxErrorException {

        Optional<Token> number = tokenManager.matchAndRemove(Token.TokenTypes.NUMBER);
        if (number.isPresent()) {
            NumericLiteralNode numNode = new NumericLiteralNode();
            numNode.value = Float.parseFloat(number.get().getValue());
            return Optional.of(numNode);
        }

        Optional<MethodCallExpressionNode> mce = parseMethodCallExpression();
        if (mce.isPresent())
            return Optional.of(mce.get());

        Optional<VariableReferenceNode> varRef = parseVariableReference();
        if (varRef.isPresent())
            return Optional.of(varRef.get());

        Optional<Token> string = tokenManager.matchAndRemove(Token.TokenTypes.QUOTEDSTRING);
        if (string.isPresent()) {
            StringLiteralNode strNode = new StringLiteralNode();
            strNode.value = string.get().getValue();
            return Optional.of(strNode);
        }

        Optional<Token> character = tokenManager.matchAndRemove(Token.TokenTypes.QUOTEDCHARACTER);
        if (character.isPresent()) {
            CharLiteralNode charNode = new CharLiteralNode();
            charNode.value = character.get().getValue().charAt(0);
            return Optional.of(charNode);
        }

        if (tokenManager.matchAndRemove(Token.TokenTypes.LPAREN).isPresent()) {
            Optional<ExpressionNode> expression = parseExpression();
            if (expression.isEmpty())
                throw new SyntaxErrorException("Expression expected in factor", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());
            if (tokenManager.matchAndRemove(Token.TokenTypes.RPAREN).isEmpty())
                throw new SyntaxErrorException("RPAREN expected after factor expression", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());
            return expression;
        }

        if (tokenManager.matchAndRemove(Token.TokenTypes.NEW).isPresent()) {

            NewNode newNode = new NewNode();
            Optional<Token> name = tokenManager.matchAndRemove(Token.TokenTypes.WORD);
            if (name.isEmpty())
                throw new SyntaxErrorException("Name expected", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());
            newNode.className = name.get().getValue();

            if (tokenManager.matchAndRemove(Token.TokenTypes.LPAREN).isEmpty())
                throw new SyntaxErrorException("LParen expected after new class identifier", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());
            Optional<ExpressionNode> expression = parseExpression();
            if (expression.isPresent()) {

                newNode.parameters.add(expression.get());

                while (tokenManager.matchAndRemove(Token.TokenTypes.COMMA).isPresent()) {

                    expression = parseExpression();
                    if (expression.isEmpty())
                        throw new SyntaxErrorException("Expression expected after comma", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());

                    newNode.parameters.add(expression.get());

                }
            }
            if (tokenManager.matchAndRemove(Token.TokenTypes.RPAREN).isEmpty())
                throw new SyntaxErrorException("RParen expected after new class identifier", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());
            return Optional.of(newNode);
        }

        return Optional.empty();

    }

    //Interface = "interface" IDENTIFIER NEWLINE INDENT MethodHeader* DEDENT
    private Optional<InterfaceNode> parseInterface() throws SyntaxErrorException {

        if (tokenManager.matchAndRemove(Token.TokenTypes.INTERFACE).isPresent()) {

            InterfaceNode i = new InterfaceNode();

            Optional<Token> name = tokenManager.matchAndRemove(Token.TokenTypes.WORD);
            if (name.isEmpty()) {
                throw new SyntaxErrorException("Interface without name", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());
            }
            i.name = name.get().getValue();

            requireNewline();
            requireIndent();

            while (checkDedent() && !tokenManager.done()) {

                Optional<MethodHeaderNode> mhn;

                try {
                    mhn = parseMethodHeader();
                } catch (SyntaxErrorException e) {
                    mhn = Optional.empty();
                }
                mhn.ifPresent(methodHeaderNode -> i.methods.add(methodHeaderNode));
            }

            if (i.methods.isEmpty()) {
                throw new SyntaxErrorException("Methods expected", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());
            }

            return Optional.of(i);

        } else
            return Optional.empty();

    }

    //MethodHeader = IDENTIFIER "(" ParameterVariableDeclarations ")" (":" ParameterVariableDeclarations)? NEWLINE
    private Optional<MethodHeaderNode> parseMethodHeader() throws SyntaxErrorException {

        MethodHeaderNode mhn = new MethodHeaderNode();

        Optional<Token> name = tokenManager.matchAndRemove(Token.TokenTypes.WORD);
        if (name.isEmpty()) {
            return Optional.empty();
        }
        mhn.name = name.get().getValue();

        if (tokenManager.matchAndRemove(Token.TokenTypes.LPAREN).isEmpty())
            throw new SyntaxErrorException("LPAREN expected after MethodHeader identifier", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());

        Optional<List<VariableDeclarationNode>> parameters = parseVariableDeclarations();
        parameters.ifPresent(variableDeclarationNodes -> mhn.parameters.addAll(variableDeclarationNodes));

        if (tokenManager.matchAndRemove(Token.TokenTypes.RPAREN).isEmpty())
            throw new SyntaxErrorException("RPAREN expected after MethodHeader parameters", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());

        if (tokenManager.matchAndRemove(Token.TokenTypes.COLON).isPresent()) {

            Optional<List<VariableDeclarationNode>> returns = parseVariableDeclarations();
            returns.ifPresent(variableDeclarationNodes -> mhn.returns.addAll(variableDeclarationNodes));

        }

        requireNewline();

        return Optional.of(mhn);
    }

    //ParameterVariableDeclarations = ParameterVariableDeclaration  ("," ParameterVariableDeclaration)*
    private Optional<List<VariableDeclarationNode>> parseVariableDeclarations() throws SyntaxErrorException {

        List<VariableDeclarationNode> vars = new ArrayList<>();
        Optional<VariableDeclarationNode> var = parseVariableDeclaration();
        if (var.isPresent()) {
            vars.add(var.get());
        } else
            return Optional.empty();

        while (tokenManager.matchAndRemove(Token.TokenTypes.COMMA).isPresent()) {

            var = parseVariableDeclaration();
            if (var.isPresent()) {
                vars.add(var.get());
            } else
                throw new SyntaxErrorException("Variable expected after comma", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());

        }

        return Optional.of(vars);
    }

    //ParameterVariableDeclaration = IDENTIFIER IDENTIFIER
    private Optional<VariableDeclarationNode> parseVariableDeclaration() throws SyntaxErrorException {

        try {

            if (tokenManager.nextTwoTokensMatch(Token.TokenTypes.WORD, Token.TokenTypes.WORD)) {

                VariableDeclarationNode var = new VariableDeclarationNode();

                Optional<Token> type = tokenManager.matchAndRemove(Token.TokenTypes.WORD);
                type.ifPresent(token -> var.type = token.getValue());

                Optional<Token> name = tokenManager.matchAndRemove(Token.TokenTypes.WORD);
                name.ifPresent(token -> var.name = token.getValue());

                if (tokenManager.matchAndRemove(Token.TokenTypes.ASSIGN).isPresent()) {
                    Optional<ExpressionNode> initializer = parseExpression();
                    if (initializer.isPresent()) {
                        var.initializer = initializer;
                    } else
                        throw new SyntaxErrorException("Initializer expected after assign", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());
                }

                return Optional.of(var);
            } else
                return Optional.empty();
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    //VariableReference = IDENTIFIER
    private Optional<VariableReferenceNode> parseVariableReference() throws SyntaxErrorException {

        Optional<Token> name = tokenManager.matchAndRemove(Token.TokenTypes.WORD);
        if (name.isEmpty())
            return Optional.empty();

        VariableReferenceNode var = new VariableReferenceNode();
        var.name = name.get().getValue();
        return Optional.of(var);

    }

    private void requireNewline() throws SyntaxErrorException {

        if (tokenManager.tokens.size() == 1) { //In the case that there is no newline but automatic end of file dedent
            if (tokenManager.matchAndRemove(Token.TokenTypes.DEDENT).isPresent())
                return;
        }

        if (tokenManager.matchAndRemove(Token.TokenTypes.NEWLINE).isEmpty()) {
            throw new SyntaxErrorException("Newline expected", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());
        } else {
            while (!tokenManager.done())
                if (tokenManager.matchAndRemove(Token.TokenTypes.NEWLINE).isEmpty()) {
                    return;
                }
        }
    }

    private void consumeWhitespace() {

        while (tokenManager.tokens.size() != 1 && tokenManager.matchAndRemove(Token.TokenTypes.NEWLINE).isPresent()) {
            continue;
        }

    }

    private void requireIndent() throws SyntaxErrorException {

        if (tokenManager.matchAndRemove(Token.TokenTypes.INDENT).isEmpty())
            throw new SyntaxErrorException("Indent expected", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());

    }

    private boolean checkDedent() throws SyntaxErrorException { //Prevents the case where a loop would occur infinitely if a dedent was missing

        if (tokenManager.tokens.size() == 1 && tokenManager.matchAndRemove(Token.TokenTypes.DEDENT).isEmpty()) {
            throw new SyntaxErrorException("Dedent missing before EOF", tokenManager.getCurrentLine(), tokenManager.getCurrentColumnNumber());
        }

        return tokenManager.matchAndRemove(Token.TokenTypes.DEDENT).isEmpty();

    }

}
