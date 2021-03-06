package org.blacksmith.commons.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import org.blacksmith.commons.tree.traverser.PreOrderTreeTraverser;

public class BTreeNode<T> implements TreeNode<T> {

  public static final TreeNode.TreeTraverser TRAVERSER = new PreOrderTreeTraverser();
  public static final TreeNode.TreeTraverser SIZE_TRAVERSER = new PreOrderTreeTraverser();
  private T data;
  private BTreeNode<T> parent;
  private List<TreeNode<T>> children = Collections.emptyList();

  public BTreeNode(T data) {
    this.data = data;
  }

  public static <T> BTreeNode<T> of(T data) {
    return new BTreeNode<>(data);
  }

  @Override
  public T getData() {
    return data;
  }

  @Override
  public void setData(T data) {
    this.data = data;
  }

  @Override
  public BTreeNode<T> getParent() {
    return this.parent;
  }

  @Override
  public void setParent(TreeNode<T> parent) {
    this.parent = (BTreeNode<T>) parent;
  }

  @Override
  public List<TreeNode<T>> getChildren() {
    return this.children;
  }

  @Override
  public BTreeNode<T> addChild(TreeNode<T> child) {
    if (children.isEmpty()) {
      children = new ArrayList<>();
    }
    child.setParent(this);
    children.add(child);
    return (BTreeNode<T>) child;
  }

  @Override
  public BTreeNode<T> removeChild(TreeNode<T> child) {
    if (child != null && children.remove(child)) {
      child.setParent(null);
      return (BTreeNode<T>) child;
    }
    return null;
  }

  @Override
  public BTreeNode<T> addChildWith(T o) {
    return addChild(new BTreeNode<>(o));
  }

  @Override
  public boolean isParentOf(TreeNode<T> n) {
    if (n == null) {
      return false;
    }
    return this.equals(n.getParent());
  }

  @Override
  public BTreeNode<T> removeDescendantWith(T o) {
    var node = (BTreeNode<T>) findDescendantWith(o);
    if (node != null && node.parent != null) {
      node.getParent().removeChild(node);
      node.setParent(null);
    }
    return node;
  }

  @Override
  public List<TreeNode<T>> removeDescendantsWith(T o) {
    var nodes = findTopDescendantsWith(o);
    for (TreeNode<T> node : nodes) {
      node.getParent().removeChild(node);
      node.setParent(null);
    }
    return nodes;
  }

  @Override
  public boolean contains(T o) {
    return findDescendantWith(o) != null;
  }

  @Override
  public void clear() {
    children.clear();
    parent = null;
    data = null;
  }

  @Override
  public boolean isLeaf() {
    return (this.parent != null) && this.children.isEmpty();
  }

  @Override
  public boolean isRoot() {
    return this.parent == null;
  }

  @Override
  public int size() {
    int size = 1;
    List<TreeNode<T>> children = getChildren();
    for (int i = 0; i < children.size(); ++i) {
      size += children.get(i).size();
    }
    return size;
  }

  @Override
  public Object[] toArray() {
    return toArray(TRAVERSER);
  }

  @Override
  public T[] toDataArray(T[] a) {
    return toDataArray(a, TRAVERSER);
  }

  @Override
  public List<TreeNode<T>> toList() {
    return toList(TRAVERSER);
  }

  @Override
  public List<T> toDataList() {
    return toDataList(TRAVERSER);
  }

  @SuppressWarnings("unchecked")
  @Override
  public TreeNode<T> findDescendantWith(final T o) {
    List<TreeNode<T>> descendants = findDescendantsWith(o);
    if (descendants.size() == 1) {
      return descendants.get(0);
    } else {
      return null;
    }
  }

  @Override
  public List<TreeNode<T>> findTopDescendantsWith(final T o) {
    final List<TreeNode<T>> found = new ArrayList<>();
    TRAVERSER.traverse(this, new NodeVisitor<T>() {
      @Override
      public void visit(TreeNode<T> node) {
        if (node.getData().equals(o)) {
          found.add(node);
        }
      }

      @Override
      public boolean acceptChildren(TreeNode<T> node) {
        return !node.getData().equals(o);
      }
    });
    return found;
  }

  @SuppressWarnings("unchecked")
  @Override
  public TreeNode<T>[] findDescendantsArrayWith(T o) {
    return findDescendantsWith(o).toArray(new TreeNode[0]);
  }

  @SuppressWarnings("unchecked")
  @Override
  public TreeNode<T>[] findDescendantsArrayWith(Predicate<T> p) {
    return findDescendantsWith(p).toArray(new TreeNode[0]);
  }

  @Override
  public List<TreeNode<T>> findDescendantsWith(T o) {
    final List<TreeNode<T>> found = new ArrayList<>();
    TRAVERSER.fullTraverse(this, (node) -> {
      if (node.getData().equals(o)) {
        found.add(node);
      }
    });
    return found;
  }

  @Override
  public List<TreeNode<T>> findDescendantsWith(Predicate<T> p) {
    final List<TreeNode<T>> found = new ArrayList<>();
    TRAVERSER.fullTraverse(this, (node) -> {
      if (p.test(node.getData())) {
        found.add(node);
      }
    });
    return found;
  }
}
