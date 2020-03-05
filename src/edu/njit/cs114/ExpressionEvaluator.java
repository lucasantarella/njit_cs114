package edu.njit.cs114;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import static edu.njit.cs114.OperatorToken.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 3/2/20
 */
public class ExpressionEvaluator {

    /**
     * Parse an expression into a list of operator and operand tokens
     *
     * @param str
     * @return
     */
    public static List<ExpressionToken> parseExpr(String str) {
        List<ExpressionToken> expr = new ArrayList<>();
        String[] strTokList = str.split("\\s+");
        for (String strTok : strTokList) {
            String str1 = strTok.trim();
            if (str1.isEmpty()) {
                continue;
            }
            OperatorToken operToken = opType(str1);
            expr.add(operToken == null ? new OperandToken(str1) : operToken);
        }
        return expr;
    }

    /**
     * Convert Infix expression given as a list of operator and operand tokens to
     * a postfix expression as a list of operator and operand tokens
     *
     * @param infixExpr
     * @return
     * @throws Exception when the expression is not valid
     *                   such as insufficient number of operators or operands e.g. 4 * 2 5, 4 * 2 +
     *                   or not having balanced parentheses e.g. (4 * ( 5 + 3 )
     */
    public static List<ExpressionToken> convertToPostFix(List<ExpressionToken> infixExpr) throws Exception {
        /**
         * Complete the code here only for homework.
         * After completing the code here, remove the following return statement
         */
        return null;
    }

    /**
     * Evaluate post fix expression given as a list of operator and operand tokens
     * and return the result
     *
     * @param postfixExpr
     * @return
     * @throws Exception when the expression is not valid
     *                   such as insufficient number of operators or operands e.g. 4 5 2 *, 4 *
     */
    public static double postFixEval(List<ExpressionToken> postfixExpr) throws Exception {
        Stack<ExpressionToken> stack = new Stack<>();

        for (ExpressionToken token : postfixExpr) {
            if (token instanceof OperandToken)
                stack.push(token);
            if (token instanceof OperatorToken) {
                if (stack.size() < 2)
                    throw new Exception("Insufficient number of operands");
                OperandToken x = (OperandToken) stack.pop();
                OperandToken y = (OperandToken) stack.pop();
                if (x == null || y == null)
                    throw new Exception("Invalid operands");

                OperandToken newToken = null;
                switch ((OperatorToken) token) {
                    // Exponent y^x
                    case EXP:
                        newToken = new OperandToken(Math.pow(y.getValue(), x.getValue()));
                        break;
                    // Multiplication - y * x
                    case MULTIPLY:
                        newToken = new OperandToken(y.getValue() * x.getValue());
                        break;
                    // Division - y / x
                    case DIVIDE:
                        newToken = new OperandToken(y.getValue() / x.getValue());
                        break;
                    // Subtraction - y - x
                    case SUBTRACT:
                        newToken = new OperandToken(y.getValue() - x.getValue());
                        break;
                    // Addition - y + x
                    case OPENPAR:
                        break;
                    case ADD:
                        newToken = new OperandToken(y.getValue() + x.getValue());
                        break;
                    // Default - throw error
                    default:
                        throw new Exception("Operator token " + ((OperatorToken) token).symbol + " not allowed for postfix evaluation");
                }

                stack.push(newToken); // Add result operand to the stack
            }
        }

        if (stack.size() != 1)
            throw new Exception("Invalid number of operators");

        ExpressionToken result = stack.pop();
        if (!(result instanceof OperandToken))
            throw new Exception("Invalid postfix expression");

        return ((OperandToken) result).getValue();
    }

    /**
     * Evaluate an infix expression string using postfix
     *
     * @param str
     * @return
     * @throws Exception when the expression is not valid (e.g 2 + 3 5)
     */
    public static double eval(String str) throws Exception {
        return postFixEval(convertToPostFix(parseExpr(str)));
    }

    /**
     * Evaluate an infix expression string directly
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static double evalDirect(String str) throws Exception {
        List<ExpressionToken> tokens = parseExpr(str);
        /**
         * For extra credit only!!
         */
        return 0;
    }

    public static void main(String[] args) throws Exception {
 /*       Scanner scanner = new Scanner(System.in);
        String str = null;
        while (!(str = scanner.nextLine()).isEmpty()) {
            System.out.println(postFixEval(parseExpr(str)));
        }*/
        /** Uncomment lines below after you have finished the homework implementations **/
//        System.out.println(String.format("postfix notation for %s : %s", "-6.5",
//                convertToPostFix(parseExpr("-6.5"))));
        System.out.println(String.format("postfix expr. %s evaluated as %.4f", "-6.5",
                postFixEval(parseExpr("-6.5"))));
//        System.out.println(String.format("%s evaluated as %.4f", "-6.5",
//                eval("-6.5")));
//        System.out.println(String.format("postfix notation for %s : %s", " 5 * -2",
//                convertToPostFix(parseExpr(" 5 * -2"))));
        System.out.println(String.format("postfix expr. %s evaluated as %.4f", " 5 -2 *",
                postFixEval(parseExpr(" 5 -2 *"))));
//        System.out.println(String.format("%s evaluated as %.4f", " 5 * -2",
//                eval(" 5 * -2")));
//        System.out.println(String.format("postfix notation for %s : %s", "( 4 + -2 ) * 7",
//                convertToPostFix(parseExpr("( 4 + -2 ) * 7"))));
//        System.out.println(String.format("%s evaluated as %.4f", "( 4 + -2 ) * 7",
//                eval("( 4 + -2 ) * 7")));
//        System.out.println(String.format("postfix notation for %s : %s", " 4 * ( 3 - 2 ) ) ** -2 ",
//                convertToPostFix(parseExpr(" 4 * ( 3 - 2 ) ) ** -2 "))));
//        System.out.println(String.format("%s evaluated as %.4f", " 4 * ( 3 - 2 ) ) ** -2",
//                   eval(" 4 * ( 3 - 2 ) ) ** -2")));
//        System.out.println(String.format("postfix notation for %s : %s",
//                " ( ( 1.5 + 2.1 ) ** 2  - 7 ) * -1.4",
//                convertToPostFix(parseExpr(" 4 * ( 3 - 2 ) ) ** -2 "))));
        System.out.println(String.format("postfix expr. %s evaluated as %.4f",
                "1.5 2.1 + 2 ** 7 - -1.4 *",
                postFixEval(parseExpr("1.5 2.1 + 2 ** 7 - -1.4 *"))));
//        System.out.println(String.format("%s evaluated as %.4f",
//                " ( ( 1.5 + 2.1 ) ** 2  - 7 ) * -1.4",
//                eval(" ( ( 1.5 + 2.1 ) ** 2  - 7 ) * -1.4")));
        System.out.println(String.format("postfix expr. %s evaluated as %.4f", "4 -2 7 * +",
                postFixEval(parseExpr("4 -2 7 * +"))));
        System.out.println(String.format("postfix expr. %s evaluated as %.4f", "3.5 2 3 + /",
                postFixEval(parseExpr("3.5 2 3 + /"))));
//        System.out.println(String.format("%s evaluated as %.4f", "3.5 / ( 2 + 3 )",
//                eval("3.5 / ( 2 + 3 )")));
//        System.out.println(String.format("postfix notation for %s : %s", "2 ** 3 ** 2",
//                convertToPostFix(parseExpr("2 ** 3 ** 2"))));
//        System.out.println(String.format("%s evaluated as %.4f", "2 ** 3 ** 2",
//                eval("2 ** 3 ** 2")));
        try {
            System.out.println(String.format("postfix expr. %s evaluated as %.4f", "2 2.5 + 3 4 *",
                    postFixEval(parseExpr("2 2.5 + 3 4 *"))));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(String.format("postfix expr. %s evaluated as %.4f", "2 5 * + ",
                    postFixEval(parseExpr("2 5 * + "))));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
