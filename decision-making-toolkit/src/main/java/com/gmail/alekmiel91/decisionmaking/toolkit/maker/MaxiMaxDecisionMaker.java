package com.gmail.alekmiel91.decisionmaking.toolkit.maker;

import com.gmail.alekmiel91.decisionmaking.toolkit.data.DecisionMatrix;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.Output;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-01.
 */
public class MaxiMaxDecisionMaker implements DecisionMaker {
    @Override
    public String makeDecision(DecisionMatrix decisionMatrix) {
        final Map<String, Double> alternativesValues = new HashMap<>(decisionMatrix.getDecisionTable().rowKeySet().size());

        decisionMatrix.getDecisionTable().rowKeySet().stream().forEach(alternative -> {
            double max = decisionMatrix.getDecisionTable().row(alternative).values().stream()
                    .map(Output::getValue)
                    .mapToDouble(Double::valueOf)
                    .max().getAsDouble();

            alternativesValues.put(alternative.getName(), max);
        });

        return alternativesValues.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
    }
}
