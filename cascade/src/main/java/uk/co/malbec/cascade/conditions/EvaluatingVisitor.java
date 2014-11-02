package uk.co.malbec.cascade.conditions;

import java.util.List;

public class EvaluatingVisitor implements Visitor {

    private boolean result;
    private List<Class> steps;

    public EvaluatingVisitor(List<Class> steps){
        this.steps = steps;
    }

    @Override
    public void visit(AndPredicate andPredicate) {
        for (Predicate predicate : andPredicate.getPredicates()){
            predicate.accept(this);
            if (!result){
                return;
            }
        }
        this.result = true;
    }

    @Override
    public void visit(OrPredicate orPredicate) {
        for (Predicate predicate : orPredicate.getPredicates()){
            predicate.accept(this);
            if (result){
                return;
            }
        }
        result = false;
    }

    @Override
    public void visit(WithStepPredicate withStepPredicate) {
        //TODO - add code so that I can define a step interface here as well as a concrete class.
        result = steps.contains(withStepPredicate.getStep());
    }

    @Override
    public void visit(NotPredicate notPredicate) {
        notPredicate.getPredicate().accept(this);
        result = !result;
    }

    public boolean getResult(){
        return result;
    }
}