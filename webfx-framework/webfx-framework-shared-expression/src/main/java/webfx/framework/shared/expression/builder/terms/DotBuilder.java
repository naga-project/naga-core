package webfx.framework.shared.expression.builder.terms;

import webfx.framework.shared.expression.Expression;
import webfx.framework.shared.expression.builder.ReferenceResolver;
import webfx.framework.shared.expression.builder.ThreadLocalReferenceResolver;
import webfx.framework.shared.expression.terms.*;

/**
 * @author Bruno Salmon
 */
public final class DotBuilder extends BinaryExpressionBuilder {
    private final boolean outerJoin;

    public DotBuilder(ExpressionBuilder left, ExpressionBuilder right, boolean outerJoin) {
        super(left, right);
        this.outerJoin = outerJoin;
    }

    @Override
    public Dot build() {
        if (operation == null) {
            propagateDomainClasses();
            Expression leftExpression = left.build(); // left should be a term when using dot
            if (leftExpression instanceof Alias)
                right.buildingClass = ((Alias) leftExpression).getDomainClass();
            else {
                Expression leftForeignSymbol = leftExpression instanceof As ? ((As) leftExpression).getOperand() : leftExpression;
                right.buildingClass = getModelReader().getSymbolForeignDomainClass(left.buildingClass, (Symbol) leftForeignSymbol);
            }
            boolean leftResolver = left instanceof ReferenceResolver;
            if (leftResolver)
                ThreadLocalReferenceResolver.pushReferenceResolver((ReferenceResolver) left);
            Expression rightExpression = right.build();
            if (leftResolver)
                ThreadLocalReferenceResolver.popReferenceResolver();
            while (rightExpression instanceof ExpressionArray) {
                Expression[] expressions = ((ExpressionArray) rightExpression).getExpressions();
                if (expressions.length != 1)
                    break;
                rightExpression = expressions[0];
            }
            operation = newBinaryOperation(leftExpression, rightExpression);
        }
        return (Dot) operation;
    }

    @Override
    protected void propagateDomainClasses() {
        super.propagateDomainClasses();
    }

    @Override
    protected Dot newBinaryOperation(Expression left, Expression right) {
        return new Dot(left, right, outerJoin);
    }
}
