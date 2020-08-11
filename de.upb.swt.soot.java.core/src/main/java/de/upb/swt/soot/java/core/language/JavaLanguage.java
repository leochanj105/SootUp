package de.upb.swt.soot.java.core.language;

/*-
 * #%L
 * Soot - a J*va Optimization Framework
 * %%
 * Copyright (C) 2019 Markus Schmidt, Linghui Luo
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

import de.upb.swt.soot.core.IdentifierFactory;
import de.upb.swt.soot.core.Language;
import de.upb.swt.soot.java.core.JavaIdentifierFactory;
import de.upb.swt.soot.java.core.ModuleIdentifierFactory;
import javax.annotation.Nonnull;

// TODO: Auto-generated Javadoc
/**
 * Language specific Configuration for Java.
 *
 * @author Markus Schmidt
 * @author Linghui Luo
 */
public class JavaLanguage extends Language {

  /** The identifier factory. */
  @Nonnull private final IdentifierFactory identifierFactory;

  /** The use java modules. */
  private final boolean useJavaModules;

  /**
   * Instantiates a new java language with given version e.g 8 or 9
   *
   * @param version the version
   */
  public JavaLanguage(int version) {
    if (version <= 8) {
      identifierFactory = JavaIdentifierFactory.getInstance();
      useJavaModules = false;
    } else {
      identifierFactory = ModuleIdentifierFactory.getInstance();
      useJavaModules = true;
    }
  }

  @Override
  @Nonnull
  public String getName() {
    return "Java";
  }

  @Override
  @Nonnull
  public IdentifierFactory getIdentifierFactory() {
    return identifierFactory;
  }

  public boolean useJavaModules() {
    return useJavaModules;
  }
}
