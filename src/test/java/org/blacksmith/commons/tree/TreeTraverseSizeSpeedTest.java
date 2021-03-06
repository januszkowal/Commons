package org.blacksmith.commons.tree;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

public class TreeTraverseSizeSpeedTest {

  private static final int TEST_REPEAT = 3;
  private static final int NODES_COUNT = 5_000_000;
  private static final int MAX_CHILDREN_COUNT = 5;
  private static final BTreeNode<Integer> root = BTreeNode.of(0);

  @BeforeAll
  public static void setUp() {
    System.out.println("Set up");
    new TreeFactory<>(Integer::intValue).populateTotal(root, NODES_COUNT, MAX_CHILDREN_COUNT);
  }

  @RepeatedTest(TEST_REPEAT)
  public void testSize(RepetitionInfo ri) {
    System.out.println(root.size());
  }
}
