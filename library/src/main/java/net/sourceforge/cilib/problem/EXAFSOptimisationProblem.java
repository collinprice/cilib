package net.sourceforge.cilib.problem;

import net.sourceforge.cilib.problem.solution.Fitness;
import net.sourceforge.cilib.type.types.Type;
import net.sourceforge.cilib.type.types.container.Vector;

public class EXAFSOptimisationProblem extends AbstractProblem {

	private static final long serialVersionUID = 135344234234L;
	
	
	public EXAFSOptimisationProblem(EXAFSOptimisationProblem copy) {
		super(copy);
	}

	@Override
	public AbstractProblem getClone() {
		return new EXAFSOptimisationProblem(this);
	}

	@Override
	protected Fitness calculateFitness(Type solution) {
		return null;
//		return objective.evaluate(function.f((Vector) solution).doubleValue());
	}

}
