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
package org.eclipse.vtp.desktop.projects.core.wizards;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.openmethods.openvxml.desktop.model.databases.IDatabase;
import com.openmethods.openvxml.desktop.model.databases.IDatabaseSet;
import com.openmethods.openvxml.desktop.model.databases.IDatabaseTable;

/**
 * This wizard walks the user through the steps required to create a new
 * database for an application. The user is prompted to enter a name for the new
 * database. This name must be unique among the current databases in the
 * application. The database is automatically created by this wizard and so
 * requires no actions from the caller of the wizard.
 *
 * @author Trip
 */
public class CreateDatabaseWizard extends Wizard implements INewWizard,
IExecutableExtension
{
	/**
	 * The database set that will contain the new database descriptor
	 */
	private IDatabaseSet databaseSet = null;

	/**
	 * The page that collects the database name.
	 */
	private DatabaseWizardPage bwp = null;

	IConfigurationElement configElement = null;
	
	/**
	 * Creates a new <code>CreateDatabaseWizard</code> instance in the
	 * given database set.
	 *
	 * @param databaseSet The database set that will contain the new database descriptor
	 */
	public CreateDatabaseWizard()
	{
		super();
		bwp = new DatabaseWizardPage();
		addPage(bwp);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
	 */
	public void setInitializationData(IConfigurationElement cfig,
		String propertyName, Object data)
	{
		configElement = cfig;
		System.out.println(data);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection)
	{
		if(selection.isEmpty())
			try
			{
				selection = (IStructuredSelection)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		Object obj = selection.getFirstElement();
		System.out.println("Selection: " + obj);
		if(obj instanceof IDatabaseSet)
			this.databaseSet = (IDatabaseSet)obj;
		else if(obj instanceof IDatabase)
			this.databaseSet = (IDatabaseSet)((IDatabase)obj).getParent();
		else if(obj instanceof IDatabaseTable)
			this.databaseSet = (IDatabaseSet)((IDatabase)((IDatabaseTable)obj).getParent()).getParent();
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.IWizard#performFinish()
	 */
	public boolean performFinish()
	{
		try
		{
			databaseSet.createDatabase(bwp.brandNameField.getText());
			databaseSet.refresh();
			return true;
		}
		catch(CoreException e)
		{
			e.printStackTrace();
		}

		return false;
	}

	private class DatabaseWizardPage extends WizardPage
	{
		Text brandNameField = null;

		public DatabaseWizardPage()
		{
			super("DatabasePage", "Enter a name for the database", null);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
		 */
		public void createControl(Composite parent)
		{
			setPageComplete(false);

			Composite comp = new Composite(parent, SWT.NONE);
			Label brandNameLabel = new Label(comp, SWT.NONE);
			brandNameLabel.setText("Database Name:");
			brandNameLabel.setSize(brandNameLabel.computeSize(SWT.DEFAULT,
					SWT.DEFAULT));
			brandNameField = new Text(comp, SWT.SINGLE | SWT.BORDER);
			brandNameField.addVerifyListener(new VerifyListener()
			{

				public void verifyText(VerifyEvent e)
                {
	                String text = e.text;
	                char[] chars = text.toCharArray();
	                for(int i = 0; i < chars.length; i++)
	                {
	                	if(Character.isLetterOrDigit(chars[i]))
	                		continue;
	                	if(chars[i] == '$')
	                		continue;
	                	if(chars[i] == '_')
	                		continue;
	                	e.doit = false;
	                	return;
	                }
	                
                }
				
			});
			brandNameField.addModifyListener(new ModifyListener()
				{
					public void modifyText(ModifyEvent e)
					{
						String n = brandNameField.getText();
						for(IDatabase d : databaseSet.getDatabases())
						{
							if(d.getName().equals(n))
							{
								setErrorMessage(
									"A Database already exists with that name.");
								setPageComplete(false);

								return;
							}
						}

						setErrorMessage(null);
						setPageComplete(true);
					}
				});
			brandNameField.addVerifyListener(new VerifyListener()
			{

				public void verifyText(VerifyEvent e)
                {
					String currentName = brandNameField.getText().substring(0, e.start) + e.text + brandNameField.getText(e.end, (brandNameField.getText().length() - 1));
	                if(currentName.length() > 255)
	                {
	                	e.doit = false;
	                	return;
	                }
                }
				
			});
			comp.setLayout(new FormLayout());

			FormData brandNameLabelData = new FormData();
			brandNameLabelData.left = new FormAttachment(0, 10);
			brandNameLabelData.top = new FormAttachment(0, 30);
			brandNameLabelData.right = new FormAttachment(0,
					10 + brandNameLabel.getSize().x);
			brandNameLabelData.bottom = new FormAttachment(0,
					30 + brandNameLabel.getSize().y);
			brandNameLabel.setLayoutData(brandNameLabelData);

			FormData brandNameFieldData = new FormData();
			brandNameFieldData.left = new FormAttachment(brandNameLabel, 10);
			brandNameFieldData.top = new FormAttachment(0, 29);
			brandNameFieldData.right = new FormAttachment(100, -10);
			brandNameField.setLayoutData(brandNameFieldData);
			setControl(comp);
		}
	}
}
