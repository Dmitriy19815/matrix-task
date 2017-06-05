package pro.codecare.java;

import pro.codecare.lib.Matrix;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * This is test application for deep checking logic of functions.
 *
 * Checked the results for concurrent matrix calculations in asynchronous and synchronous thread concepts
 * and compares results of them with default implementation of matrix multiply calculation.
 */
public class GeneralLogicTests {

  public static void main(String ... args) throws Exception {
    int dim = 10 + (new Random()).nextInt(99);

    try {
      /**
       * First simple matrix for calculations
       */
      Matrix mtr1 = new Matrix(10 + (new Random()).nextInt(999), dim);
      System.out.println("First (left) matrix: "+mtr1.toString());

      /**
       * Second simple matrix for calculations
       */
      Matrix mtr2 = new Matrix(dim, 10 + (new Random()).nextInt(999));
      System.out.println("Second (right) matrix: "+mtr2.toString());

      /**
       * Matrix for compare results of calculations by default method
       */
      Matrix mtrDefault = new Matrix();
      mtrDefault.Multiplication(mtr1, mtr2);
      System.out.println("Multiply with NO concurrent (default) algorithm for matrix " + mtrDefault.toString());
      printResults("Multiply.NOconcurrent.results.log", mtr1, mtr2, mtrDefault);
      System.out.println("\tOutput file was generated\n");

      /**
       * Matrix for synchronous thread concept of calculations
       */
      Matrix mtrSync = new Matrix();
      mtrSync.MultiplicationConcurrent(mtr1, mtr2, 0);
      System.out.println("Multiply in sync concurrency algorithm for matrix " + mtrSync.toString());

      printResults("Multiply.concurrent0.results.log", mtr1, mtr2, mtrSync);
      System.out.println("\tOutput file was generated\n");

      /**
       * Compare results of default and synchronous thread concept of calculations
       */
      for (int row = 0; row < mtr1.getRows(); ++row) {
        for (int col = 0; col < mtr2.getColumns(); ++col) {
          if (mtrDefault.getValue(row, col) != mtrSync.getValue(row, col)) {
            System.out.println("Error in checking of algorithm results is occurred, the elements in position ["+row+"],["+col+"] is not equal");
            return;
          }
        }
      }

      /**
       * Matrix for asynchronous thread concept of calculations
       */
      Matrix mtrAsync = new Matrix();
      mtrAsync.MultiplicationConcurrent(mtr1, mtr2, 1);
      System.out.println("Multiply in async concurrency algorithm for matrix " + mtrAsync.toString());
      printResults("Multiply.concurrent1.results.log", mtr1, mtr2, mtrAsync);
      System.out.println("\tOutput file was generated\n");

      /**
       * Compare results of default and asynchronous thread concept of calculations
       */
      for (int row = 0; row < mtr1.getRows(); ++row) {
        for (int col = 0; col < mtr2.getColumns(); ++col) {
          if (mtrDefault.getValue(row, col) != mtrAsync.getValue(row, col)) {
            System.out.println("Error in checking of algorithm results is occurred, the elements in position ["+row+"],["+col+"] is not equal");
            return;
          }
        }
      }

    }
    catch (Exception e) {
      System.err.println(e.toString());
    }
  }


  /**
   * Output into file stream results of calculation
   *
   * @param fileName
   * @param first         First matrix object
   * @param second        Second matrix object
   * @param result        Destination (result) matrix object
   */
  private static void printResults(final String fileName, Matrix first, Matrix second, Matrix result) {

    try (final FileWriter fileWriter = new FileWriter(fileName, false)) {
      fileWriter.write("\tFirst (left) matrix:\n");
      first.Output(fileWriter);

      fileWriter.write("\n\tSecond (right) matrix:\n");
      second.Output(fileWriter);

      fileWriter.write("\n\tCalculated matrix:\n");
      result.Output(fileWriter);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

}