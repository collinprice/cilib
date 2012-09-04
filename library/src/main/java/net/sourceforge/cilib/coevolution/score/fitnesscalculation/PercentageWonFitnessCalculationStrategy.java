/**
 * Computational Intelligence Library (CIlib)
 * Copyright (C) 2003 - 2010
 * Computational Intelligence Research Group (CIRG@UP)
 * Department of Computer Science
 * University of Pretoria
 * South Africa
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, see <http://www.gnu.org/licenses/>.
 */
package net.sourceforge.cilib.coevolution.score.fitnesscalculation;

import net.sourceforge.cilib.coevolution.score.EntityScoreboard;
import net.sourceforge.cilib.problem.solution.Fitness;
import net.sourceforge.cilib.problem.solution.MaximisationFitness;

/**
 * This class uses the percentage games won as the Fitness value.
 */
public class PercentageWonFitnessCalculationStrategy extends FitnessCalculationStrategy {

    private static final long serialVersionUID = 2550931405402015975L;

    public PercentageWonFitnessCalculationStrategy() {
    }

    public PercentageWonFitnessCalculationStrategy(PercentageWonFitnessCalculationStrategy other) {
        super(other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fitness calculateFitnessFromScoreBoard(EntityScoreboard score, int currentRound) {
        int amGames = score.getCompeteCount(currentRound);
        int winCount = score.getWinCount(currentRound);
        return new MaximisationFitness(((double) winCount / (double) amGames) * 100.0); //maximize the percentage of games won
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PercentageWonFitnessCalculationStrategy getClone() {
        return this;
    }
}