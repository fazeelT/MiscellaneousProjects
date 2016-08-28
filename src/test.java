import java.awt.print.Printable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.naming.spi.DirStateFactory.Result;

public class test {
	private final static String[] comparisonOperators = { "GT", ">", "EQ", "=", "LT", "<", "GE", ">=", "LE", "<=" };
	private final static String[] logicalOperators = { "!", "|", "&" };
	private static Map<String, Integer> myVars;

	public static void main(String[] args) {
		String expression = "!( cf_Number = 16 ) & ( cf_Number > 13 & cf_Number < 20 ) & cf_Number >= 15";
		myVars = new HashMap<>();
		myVars.put("cf_Number", 15);

		System.out.println(evaluateExpression(expression));

		expression = "cf_Number = 16 & !( cf_Number > 13 & cf_Number < 20 ) & cf_Number >= 15";
		System.out.println(evaluateExpression(expression));

		expression = "cf_Number = 16 & !( cf_Number > 13 & cf_Number < 20 ) | cf_Number >= 15";
		System.out.println(evaluateExpression(expression));

		expression = "cf_Number = 16 & cf_Number > 13 & cf_Number < 20";
		System.out.println(evaluateExpression(expression));

		expression = "cf_Number = 15 & cf_Number >= 15 & cf_Number <= 15";
		System.out.println(evaluateExpression(expression));
	}

	public static boolean evaluateExpression(String expression) {
		Stack<String> compOpsStack = new Stack<String>();
		Stack<String> valStack = new Stack<String>();
		Stack<Boolean> boolStack = new Stack<>();
		Stack<String> logicalOpsStack = new Stack<String>();

		String[] tokens = expression.split(" ");
		for (int i = 0; i < tokens.length; i++) {
			String s = tokens[i];
			if (isComparisonOperator(s)) {
				compOpsStack.push(s);
			} else if (isLogicOperator(s)) {
				logicalOpsStack.push(s);
			} else if (s.contains("(")) {
				if (s.contains("!")) {
					logicalOpsStack.push(s);
					compOpsStack.push(s.substring(1));
				} else {
					logicalOpsStack.push(s);
					compOpsStack.push(s);
				}
			} else if (s.equals(")")) {
				evaluateExpression(compOpsStack, valStack, boolStack, logicalOpsStack);
			} else {
				valStack.push(s);
			}
		}
		evaluateExpression(compOpsStack, valStack, boolStack, logicalOpsStack);
		return boolStack.pop();
	}

	public static boolean isComparisonOperator(String token) {
		return Arrays.asList(comparisonOperators).contains(token);
	}

	public static boolean isLogicOperator(String token) {
		return Arrays.asList(logicalOperators).contains(token);
	}

	public static void evaluateExpression(Stack<String> compOpsStack, Stack<String> valStack, Stack<Boolean> boolStack,
			Stack<String> logicalOpsStack) {

		String operator;
		while (!compOpsStack.isEmpty() && !(operator = compOpsStack.pop()).equals("(")) {
			String val1 = valStack.pop();
			String val2 = valStack.pop();
			boolean result;
			switch (operator) {
			case "GT":
			case ">":
				result = myVars.get(val2) > Integer.parseInt(val1);
				boolStack.push(result);
				break;

			case "LT":
			case "<":
				result = myVars.get(val2) < Integer.parseInt(val1);
				boolStack.push(result);
				break;

			case "GE":
			case ">=":
				result = myVars.get(val2) >= Integer.parseInt(val1);
				boolStack.push(result);
				break;

			case "LE":
			case "<=":
				result = myVars.get(val2) <= Integer.parseInt(val1);
				boolStack.push(result);
				break;

			case "EQ":
			case "=":
				result = myVars.get(val2) == Integer.parseInt(val1);
				boolStack.push(result);
				break;

			case "NE":
			case "!=":
				result = myVars.get(val2) == Integer.parseInt(val1);
				boolStack.push(result);
				break;
			}
		}
		String logicalOp;
		boolean breakLoop = false;
		while (!logicalOpsStack.isEmpty() && !(logicalOp = logicalOpsStack.pop()).equals("(") && !breakLoop) {

			Boolean result;
			Boolean tf1, tf2;

			switch (logicalOp) {
			case "!(":
				tf1 = boolStack.pop();
				result = !tf1;
				boolStack.push(result);
				breakLoop = true;
				break;
			case "&":
				tf1 = boolStack.pop();
				tf2 = boolStack.pop();
				result = tf1 && tf2;
				boolStack.push(result);
				break;

			case "|":
				tf1 = boolStack.pop();
				tf2 = boolStack.pop();
				result = tf1 || tf2;
				boolStack.push(result);
				break;

			}
		}
	}

}
