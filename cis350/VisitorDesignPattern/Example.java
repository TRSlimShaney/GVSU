
public class Example {

    public static void main(String[] args) {

        ExpressionInterface expression = 
            new Multiplication(new Literal(-1.0),
                new Addition(
                    new Subtraction(
                        new Literal(4.0), 
                        new Literal(2.0)),
                    new Literal(6.0)));
                
        ExpressionExecutionVisitor executionVisitor = null;
        ExpressionPrintingVisitor printVisitor = null;

        expression.accept(executionVisitor = new ExpressionExecutionVisitor());
        expression.accept(printVisitor = new ExpressionPrintingVisitor());
        System.out.println(printVisitor.toString() + " = " + executionVisitor.getComputedValue());
        
        ExpressionPrefixPrintingVisitor printVisitor2 = null;
        
        expression.accept(printVisitor2 = new ExpressionPrefixPrintingVisitor());
        System.out.println(printVisitor2.toString() + " = " + executionVisitor.getComputedValue());
    }

}
