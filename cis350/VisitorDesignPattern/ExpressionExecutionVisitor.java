
public class ExpressionExecutionVisitor implements ExpressionVisitorInterface {

	double currentValue;
	public ExpressionExecutionVisitor() {
		currentValue = 0.0;
	}
	public double getComputedValue() {
		return currentValue;
	}
	
	public void visit(Addition addition) {
		
		addition.leftExpression.accept(this);
		double leftValue = currentValue;
		addition.rightExpression.accept(this);
		double rightValue = currentValue;
		
		currentValue = leftValue + rightValue;
	}
    public void visit(Subtraction subtraction) {
    	
    	subtraction.leftExpression.accept(this);
    	double leftValue = currentValue;
    	subtraction.rightExpression.accept(this);
    	double rightValue = currentValue;
    	
    	currentValue = leftValue - rightValue;
    }
    public void visit(Multiplication multiplication) {
    	
    	multiplication.leftExpression.accept(this);
    	double leftValue = currentValue;
    	multiplication.rightExpression.accept(this);
    	double rightValue = currentValue;
    	
    	currentValue = leftValue * rightValue;
    }
    public void visit(Division division) {
    	
    	division.leftExpression.accept(this);
    	double leftValue = currentValue;
    	division.rightExpression.accept(this);
    	double rightValue = currentValue;
    	
    	currentValue = leftValue / rightValue;
    }
    public void visit(Literal literal) {
    	
    	double value = literal.value;
 
    	currentValue = value;
    }
	
}
