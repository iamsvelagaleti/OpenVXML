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

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.vtp.desktop.model.core.IWorkflowResourceContainer;

/**
 * This interface represents the folder resource of an application project
 * that contains the database configuration information file and folder
 * resources.
 *
 * @author Trip Gilman
 * @version 2.0
 */
public interface IDatabaseSet extends IWorkflowResourceContainer
{
	/**
	 * @return The list of <code>IDatabase</code>s that are defined in
	 * this application project
	 */
	public List<IDatabase> getDatabases();

	/**
	 * Creates a new database folder resource with the given name.  The
	 * database will not initially contain any database table definitions.
	 *
	 * @param name The name of the database folder resource
	 * @return The newly created database folder resource
	 * @throws CoreException If an error occured during the creation of
	 * the required file or folder resources of the database definition
	 */
	public IDatabase createDatabase(String name) throws CoreException;

	/**
	 * Removes the given database from this application.  This will also
	 * remove all database table definitions contained by the database
	 * folder resource.
	 *
	 * @param database The database definition to remove
	 */
	public void deleteDatabase(IDatabase database);
}
