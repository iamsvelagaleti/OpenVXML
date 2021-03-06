package org.eclipse.vtp.desktop.export.internal.main;

import org.w3c.dom.Document;

import com.openmethods.openvxml.desktop.model.workflow.IDesignDocument;

public class DesignReference
{
	private IDesignDocument designDocument;
	private Document document;

	public DesignReference(IDesignDocument designDocument, Document document)
	{
		super();
		this.designDocument = designDocument;
		this.document = document;
	}

	public IDesignDocument getDesignDocument()
	{
		return this.designDocument;
	}
	
	public Document getXMLDocument()
	{
		return this.document;
	}
}
