package de.upb.swt.soot.java.core.jimple.basic;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2019
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */

import de.upb.swt.soot.core.jimple.basic.Local;
import de.upb.swt.soot.core.types.Type;
import de.upb.swt.soot.java.core.AnnotationType;
import java.util.Objects;
import javax.annotation.Nonnull;

public class JavaLocal extends Local {

  // TODO: [ms] add to JavaJimple
  // TODO: [ms] make use of this class in both Java Frontends

  @Nonnull private final Iterable<AnnotationType> annotations;

  /**
   * Constructs a JimpleLocal of the given name and type.
   *
   * @param name
   * @param type
   */
  public JavaLocal(
      @Nonnull String name, @Nonnull Type type, @Nonnull Iterable<AnnotationType> annotations) {
    super(name, type);
    this.annotations = annotations;
  }

  @Nonnull
  public Iterable<AnnotationType> getAnnotations() {
    return annotations;
  }

  @Override
  public boolean equals(Object o) {
    return equivTo(o) && ((JavaLocal) o).getAnnotations().equals(getAnnotations());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getType(), getAnnotations());
  }

  @Nonnull
  public Local withName(@Nonnull String name) {
    return new JavaLocal(name, getType(), getAnnotations());
  }

  @Nonnull
  public Local withType(@Nonnull Type type) {
    return new JavaLocal(getName(), type, getAnnotations());
  }

  @Nonnull
  public Local withAnnotations(@Nonnull Iterable<AnnotationType> annotations) {
    return new JavaLocal(getName(), getType(), annotations);
  }
}
