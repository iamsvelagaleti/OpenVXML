package com.openmethods.openvxml.desktop.model.workflow.internal;

import org.eclipse.core.resources.IFolder;
import org.eclipse.vtp.desktop.model.core.IWorkflowResource;

import com.openmethods.openvxml.desktop.model.workflow.IDesignFolder;
import com.openmethods.openvxml.desktop.model.workflow.IDesignItemContainer;

/**
 * @author trip
 */
public class DesignFolder extends DesignItemContainer implements IDesignFolder
{
	private IDesignItemContainer parent = null;

	/**
	 * 
	 */
	public DesignFolder(IDesignItemContainer parent, IFolder folder)
	{
		super(folder);
		this.parent = parent;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.vtp.desktop.model.core.IDesignFolder#getParentDesignContainer()
	 */
	public IDesignItemContainer getParentDesignContainer()
	{
		return parent;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.vtp.desktop.model.core.IWorkflowResource#getParent()
	 */
	public IWorkflowResource getParent()
	{
		return parent;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.vtp.desktop.model.core.internal.WorkflowResource#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapterClass)
	{
		if(IDesignFolder.class.isAssignableFrom(adapterClass))
			return this;
		return super.getAdapter(adapterClass);
	}
}
