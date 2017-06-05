package pro.codecare.java;

import org.junit.Assert;
import org.junit.Test;
import pro.codecare.lib.Matrix;

/**
 *
 */
public class EntityTests {

  public EntityTests() {

  }

  @Test
  public void testConcurrentOne() {
    Matrix matrix = new Matrix();
    Assert.assertNotNull(matrix);

    matrix.MultiplicationConcurrent(new Matrix(55, 10), new Matrix(10, 50), 0);

    Assert.assertNotNull(matrix);
    Assert.assertEquals((int) matrix.getRows(), 55);
    Assert.assertEquals((int) matrix.getColumns(), 50);
    System.out.println("Multithread sync calculator for matrix "+matrix.toString()+" is works");
  }

  @Test
  public void testConcurrentTwo() {
    Matrix matrix = new Matrix();
    Assert.assertNotNull(matrix);

    matrix.MultiplicationConcurrent(new Matrix(55, 10), new Matrix(10, 50), 1);

    Assert.assertNotNull(matrix);
    Assert.assertEquals((int) matrix.getRows(), 55);
    Assert.assertEquals((int) matrix.getColumns(), 50);
    System.out.println("Multithread async calculator for matrix "+matrix.toString()+" is works");
  }

}