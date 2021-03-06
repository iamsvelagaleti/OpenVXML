package org.eclipse.vtp.desktop.model.interactive.core.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.vtp.desktop.model.interactive.core.IMediaLibrariesFolder;
import org.eclipse.vtp.desktop.model.interactive.core.IMediaLibrary;
import org.eclipse.vtp.desktop.model.interactive.core.IMediaObject;
import org.eclipse.vtp.desktop.model.interactive.core.IMediaObjectContainer;
import org.eclipse.vtp.desktop.model.interactive.core.IMediaProject;

public class MediaLibrariesFolder extends MediaObject implements
		IMediaLibrariesFolder
{
	private static final String HASHPREFIX = "MEDIALIBRARIESFOLDER";
	private IMediaProject parent;
	private IFolder folder;

	public MediaLibrariesFolder(IMediaProject parent, IFolder folder)
	{
		super();
		this.parent = parent;
		this.folder = folder;
	}

	@Override
	public List<IMediaObject> getChildren()
	{
		try
		{
			return new ArrayList<IMediaObject>(getMediaLibraries());
		}
		catch (CoreException e)
		{
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	@Override
	public String getName()
	{
		return folder.getName();
	}

	@Override
	public IMediaObjectContainer getParent()
	{
		return parent;
	}

	public IMediaLibrary getMediaLibrary(String name)
	{
		return new MediaLibrary(this, folder.getFolder(name));
	}
	
	@Override
	public List<IMediaLibrary> getMediaLibraries() throws CoreException
	{
		List<IMediaLibrary> ret = new ArrayList<IMediaLibrary>();
		IResource[] res = folder.members();

		for(int i = 0; i < res.length; i++)
		{
			if(res[i].getName().startsWith("."))
			{
				continue;
			}

			if(res[i] instanceof IFolder)
			{
				IFolder f = (IFolder)res[i];
				ret.add(new MediaLibrary(this, f));
			}
		}

		return ret;
	}

	@Override
	protected String getObjectId()
	{
		return folder.getFullPath().toPortableString();
	}
	
	public IFolder getUnderlyingFolder()
	{
		return folder;
	}

	@SuppressWarnings("unchecked")
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapterClass)
	{
		if(adapterClass.isAssignableFrom(IFolder.class))
			return folder;
		return super.getAdapter(adapterClass);
	}

	public boolean equals(Object obj)
	{
		if(obj instanceof MediaLibrariesFolder)
		{
			return folder.equals(((MediaLibrariesFolder)obj).getUnderlyingFolder());
		}
		return false;
	}
	
	public int hashCode()
	{
		return (HASHPREFIX + folder.toString()).hashCode();
	}
}
