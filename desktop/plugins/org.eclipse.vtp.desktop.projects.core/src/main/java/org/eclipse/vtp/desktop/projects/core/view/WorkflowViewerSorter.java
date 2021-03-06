package org.eclipse.vtp.desktop.projects.core.view;

import java.text.Collator;

import org.eclipse.jface.viewers.ViewerSorter;

import com.openmethods.openvxml.desktop.model.businessobjects.IBusinessObjectSet;
import com.openmethods.openvxml.desktop.model.databases.IDatabaseSet;
import com.openmethods.openvxml.desktop.model.dependencies.IDependencySet;
import com.openmethods.openvxml.desktop.model.workflow.IDesignDocument;
import com.openmethods.openvxml.desktop.model.workflow.IDesignFolder;
import com.openmethods.openvxml.desktop.model.workflow.IDesignRootFolder;

public class WorkflowViewerSorter extends ViewerSorter
{

	public WorkflowViewerSorter()
	{
	}

	public WorkflowViewerSorter(Collator collator)
	{
		super(collator);
	}

    public int category(Object element)
    {
    	if(element instanceof IDesignRootFolder)
    		return 1;
    	else if(element instanceof IBusinessObjectSet)
    		return 2;
    	else if(element instanceof IDatabaseSet)
    		return 3;
    	else if(element instanceof IDependencySet)
    		return 4;
    	else if(element instanceof IDesignFolder)
    		return 5;
    	else if(element instanceof IDesignDocument)
    		return 6;
        return Integer.MAX_VALUE;
    }
	
}
