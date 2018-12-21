/* Soot - a J*va Optimization Framework
 * Copyright (C) 1999 Patrick Lam
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

/*
 * Modified by the Sable Research Group and others 1997-1999.  
 * See the 'credits' file distributed with Soot for the complete list of
 * contributors.  (Soot is distributed at http://www.sable.mcgill.ca/soot)
 */

package de.upb.soot.jimple.common.expr;

import de.upb.soot.jimple.Jimple;
import de.upb.soot.jimple.basic.Value;
import de.upb.soot.jimple.basic.ValueBox;
import de.upb.soot.jimple.common.type.ArrayType;
import de.upb.soot.jimple.common.type.Type;
import de.upb.soot.jimple.visitor.IExprVisitor;
import de.upb.soot.jimple.visitor.IVisitor;
import de.upb.soot.util.printer.IStmtPrinter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class JNewMultiArrayExpr implements Expr {
  /**
   * 
   */
  private static final long serialVersionUID = -473132292740722571L;
  private ArrayType baseType;
  protected final ValueBox[] sizeBoxes;

  /**
   * Initiates a JNewMultiArrayExpr.
   * 
   * @param type
   *          the type of the array
   * @param sizes
   *          the sizes
   * 
   */
  public JNewMultiArrayExpr(ArrayType type, List<? extends Value> sizes) {
    this.baseType = type;
    this.sizeBoxes = new ValueBox[sizes.size()];
    for (int i = 0; i < sizes.size(); i++) {
      sizeBoxes[i] = Jimple.newImmediateBox(sizes.get(i));
    }
  }

  @Override
  public Object clone() {
    List<Value> clonedSizes = new ArrayList<Value>(getSizeCount());

    for (int i = 0; i < getSizeCount(); i++) {
      clonedSizes.add(i, Jimple.cloneIfNecessary(getSize(i)));
    }

    return new JNewMultiArrayExpr(baseType, clonedSizes);
  }

  @Override
  public boolean equivTo(Object o) {
    if (o instanceof JNewMultiArrayExpr) {
      JNewMultiArrayExpr ae = (JNewMultiArrayExpr) o;
      if (!baseType.equals(ae.baseType) || sizeBoxes.length != ae.sizeBoxes.length) {
        return false;
      }
      return true;
    }
    return false;
  }

  /** Returns a hash code for this object, consistent with structural equality. */
  @Override
  public int equivHashCode() {
    return baseType.hashCode();
  }

  @Override
  public String toString() {
    StringBuffer buffer = new StringBuffer();

    Type t = baseType.baseType;
    buffer.append(Jimple.NEWMULTIARRAY + " (" + t.toString() + ")");

    for (ValueBox element : sizeBoxes) {
      buffer.append("[" + element.getValue().toString() + "]");
    }

    for (int i = 0; i < baseType.numDimensions - sizeBoxes.length; i++) {
      buffer.append("[]");
    }

    return buffer.toString();
  }

  @Override
  public void toString(IStmtPrinter up) {
    Type t = baseType.baseType;

    up.literal(Jimple.NEWMULTIARRAY);
    up.literal(" (");
    up.type(t);
    up.literal(")");

    for (ValueBox element : sizeBoxes) {
      up.literal("[");
      element.toString(up);
      up.literal("]");
    }

    for (int i = 0; i < baseType.numDimensions - sizeBoxes.length; i++) {
      up.literal("[]");
    }
  }

  public ArrayType getBaseType() {
    return baseType;
  }

  public void setBaseType(ArrayType baseType) {
    this.baseType = baseType;
  }

  public ValueBox getSizeBox(int index) {
    return sizeBoxes[index];
  }

  public int getSizeCount() {
    return sizeBoxes.length;
  }

  public Value getSize(int index) {
    return sizeBoxes[index].getValue();
  }

  /**
   * Returns a list of values of sizeBoxes.
   */
  public List<Value> getSizes() {
    List<Value> toReturn = new ArrayList<Value>();

    for (ValueBox element : sizeBoxes) {
      toReturn.add(element.getValue());
    }

    return toReturn;
  }

  public void setSize(int index, Value size) {
    sizeBoxes[index].setValue(size);
  }

  @Override
  public final List<ValueBox> getUseBoxes() {
    List<ValueBox> list = new ArrayList<ValueBox>();
    Collections.addAll(list, sizeBoxes);

    for (ValueBox element : sizeBoxes) {
      list.addAll(element.getValue().getUseBoxes());
    }

    return list;
  }

  @Override
  public Type getType() {
    return baseType;
  }

  @Override
  public void accept(IVisitor sw) {
    ((IExprVisitor) sw).caseNewMultiArrayExpr(this);
  }

  @Override
  public boolean equivTo(Object o, Comparator comparator) {
    return comparator.compare(this, o) == 0;
  }

}