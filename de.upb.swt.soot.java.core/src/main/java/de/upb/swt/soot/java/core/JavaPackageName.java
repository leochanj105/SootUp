package de.upb.swt.soot.java.core;

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

import de.upb.swt.soot.core.model.SootClass;
import de.upb.swt.soot.core.signatures.PackageName;
import de.upb.swt.soot.java.core.views.JavaView;
import java.util.Collections;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class JavaPackageName extends PackageName {

  @Nullable private Iterable<AnnotationType> annotations;

  /**
   * Internal: Constructs a Package Signature of a Java package. Instances should only be created by
   * a {@link de.upb.swt.soot.core.IdentifierFactory }
   *
   * @param packageName the package's name
   */
  public JavaPackageName(@Nonnull String packageName) {
    this(packageName, null);
  }

  /*
   * Internal: Constructs a Package Signature of a Java package. Instances should only be created by
   * a {@link de.upb.swt.soot.core.IdentifierFactory }
   *
   * @param annotations
   * @param packageName the package's name
   */
  public JavaPackageName(
      @Nonnull String packageName, @Nonnull Iterable<AnnotationType> annotations) {
    super(packageName);
    this.annotations = annotations;
  }

  public Iterable<AnnotationType> getAnnotations(JavaView view, String packageName) {
    if (annotations != null) {
      Optional<SootClass> sc =
          view.getClass(
              JavaIdentifierFactory.getInstance().getClassType(PACKAGE_INFO, packageName));
      annotations =
          sc.isPresent() ? ((JavaSootClass) sc.get()).getAnnotations() : Collections.emptyList();
    }
    return annotations;
  }
}
