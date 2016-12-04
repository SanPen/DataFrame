package com.dataframe;

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresBuilder;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.fitting.leastsquares.GaussNewtonOptimizer;

import java.util.Arrays;


/**
 * Non linear least squares fit
 */
public class Lstsq {

    /**
     * Coefficient matrix
     */
    double[][] A;

    /**
     * Vector to fit
     */
    double[] b;

    /**
     * Found solution
     */
    double[] x_sol;

    /**
     * Class constructor
     *
     * @param A_mat
     * @param b_vec
     */
    public Lstsq(double[][] A_mat, double[] b_vec) {
        A = A_mat;
        b = b_vec;
    }


    /**
     * || b - A x ||^2
     *
     * @param x
     * @return
     */
    private double euclidean_norm(double[] x) {
        double norm = 0;
        double b_ax = 0;
        double ax;

        for (int i = 0; i < b.length; i++) {
            ax = 0;
            for (int j = 0; j < x.length; j++) {
                ax += A[i][j] * x[j];
            }
            b_ax += Math.abs(b[i] - ax);
        }

        norm = Math.pow(b_ax, 2);

        return norm;
    }

/*
    MultivariateJacobianFunction euclidean_norm = new MultivariateJacobianFunction() {
        public RealVector value(RealVector point) {

            Vector2D center = new Vector2D(point.getEntry(0), point.getEntry(1));

            RealVector value = new ArrayRealVector(observedPoints.length);
            RealMatrix jacobian = new Array2DRowRealMatrix(observedPoints.length, 2);

            for (int i = 0; i < observedPoints.length; ++i) {
                Vector2D o = observedPoints[i];
                double modelI = Vector2D.distance(o, center);
                value.setEntry(i, modelI);
                // derivative with respect to p0 = x center
                jacobian.setEntry(i, 0, (center.getX() - o.getX()) / modelI);
                // derivative with respect to p1 = y center
                jacobian.setEntry(i, 1, (center.getX() - o.getX()) / modelI);
            }

            return value;

        }
    };


    private void run() {

        int n = A[0].length;

        // the target is to have all points at the specified radius from the center
        double[] target = new double[n];
        Arrays.fill(target, 0);

        // initial solution
        double[] x0 = new double[n];
        Arrays.fill(x0, 0);


        LeastSquaresOptimizer optimizer = new GaussNewtonOptimizer().withDecomposition(GaussNewtonOptimizer.Decomposition.QR);

        // least squares problem to solve : modeled radius should be close to target radius
        LeastSquaresProblem problem = new LeastSquaresBuilder().
                start(x0).
                model(euclidean_norm).
                target(target).
                lazyEvaluation(false).
                maxEvaluations(1000).
                maxIterations(1000).
                build();
        LeastSquaresOptimizer.Optimum optimum = new LevenbergMarquardtOptimizer().optimize(problem);

//        Vector2D fittedCenter = new Vector2D(optimum.getPoint().getEntry(0), optimum.getPoint().getEntry(1));
//        System.out.println("fitted center: " + fittedCenter.getX() + " " + fittedCenter.getY());
//        System.out.println("RMS: "           + optimum.getRMS());
//        System.out.println("evaluations: "   + optimum.getEvaluations());
//        System.out.println("iterations: "    + optimum.getIterations());
    }
    */

}
