/*--------------------------------------------------------------------------
 * Copyright (c) 2004, 2006-2007 OpenMethods, LLC
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Trip Gilman (OpenMethods), Lonnie G. Pryor (OpenMethods)
 *    - initial API and implementation
 -------------------------------------------------------------------------*/
package com.openmethods.openvxml.desktop.model.databases;


/**
 * This interface represents a column defined for a database table
 * definition.
 *
 * @author Trip Gilman
 * @version 2.0
 */
public interface IDatabaseTableColumn
{
	/**
	 * @return The name of this database table column
	 */
	public String getName();

	/**
	 * @return The data type definition of this database table column
	 */
	public ColumnType getColumnType();
}
