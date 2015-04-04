package com.gmail.alekmiel91.decisionmaking.toolkit.loader;

import com.gmail.alekmiel91.decisionmaking.toolkit.Context;
import com.gmail.alekmiel91.decisionmaking.toolkit.DecisionMakingException;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.DecisionMatrix;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.DecisionMatrixBuilder;
import com.gmail.alekmiel91.decisionmaking.toolkit.data.RawDecisionMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Aleksander Mielczarek
 *         Created on 2015-04-01.
 */
public interface DecisionMatrixLoader {

    public static final Logger LOGGER = LoggerFactory.getLogger(DecisionMatrixLoader.class);

    public default DecisionMatrix load(File file) throws DecisionMakingException {
        RawDecisionMatrix rawDecisionMatrix = null;
        try {
            rawDecisionMatrix = loadRawDecisionMatrix(file);
        } catch (DecisionMakingLoaderException e) {
            throw new DecisionMakingException(Context.INSTANCE.getResources().getString("exception.matrix.loader.load"), e);
        }

        DecisionMatrixBuilder builder = DecisionMatrix.builder(rawDecisionMatrix.getAlternatives(),
                rawDecisionMatrix.getScenes(),
                rawDecisionMatrix.getOutputs());

        if (rawDecisionMatrix.getFactors() != null) {
            builder.withFactors(rawDecisionMatrix.getFactors());
        }

        return builder.build();
    }

    public RawDecisionMatrix loadRawDecisionMatrix(File file) throws DecisionMakingLoaderException;

    public default void save(DecisionMatrix decisionMatrix, File file) throws DecisionMakingException {
        try {
            saveRawDecisionMatrix(decisionMatrix.getRawDecisionMatrix(), file);
        } catch (DecisionMakingLoaderException e) {
            throw new DecisionMakingException(Context.INSTANCE.getResources().getString("exception.matrix.loader.save"), e);
        }
    }

    public void saveRawDecisionMatrix(RawDecisionMatrix rawDecisionMatrix, File file) throws DecisionMakingLoaderException;
}
