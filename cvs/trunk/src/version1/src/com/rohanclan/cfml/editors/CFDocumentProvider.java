/*
 * Created on Jan 30, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package com.rohanclan.cfml.editors;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.editors.text.FileDocumentProvider;
import org.eclipse.ui.internal.editors.text.JavaFileEditorInput;
import org.eclipse.ui.part.FileEditorInput;

import org.apache.commons.net.ftp.FTPClient;

import com.rohanclan.cfml.editors.partitioner.CFEDefaultPartitioner;
import com.rohanclan.cfml.editors.partitioner.scanners.CFPartitionScanner;


/**
 * 
 * This document handles the opening and closing of CF documents.
 * It assigns and runs a the parser over a document.
 * 
 * @author Rob
 * 
 */
public class CFDocumentProvider extends FileDocumentProvider {

	protected IDocument createDocument(Object element) throws CoreException {
		ICFDocument document = null;
		
		document = new ICFDocument();
		if (setDocumentContent(document, (IEditorInput) element,
				getEncoding(element))) {
			setupDocument(element, document);
		}
		
		if (document != null) {
			IDocumentPartitioner partitioner = new CFEDefaultPartitioner(
					new CFPartitionScanner(), new String[] {
							CFPartitionScanner.ALL_TAG,
							CFPartitionScanner.CF_COMMENT,
							CFPartitionScanner.HTM_COMMENT,
							CFPartitionScanner.DOCTYPE,
							CFPartitionScanner.CF_TAG,
							CFPartitionScanner.CF_END_TAG,
							CFPartitionScanner.CF_SCRIPT,
							CFPartitionScanner.J_SCRIPT,
							CFPartitionScanner.CSS_TAG,
							CFPartitionScanner.UNK_TAG,
							CFPartitionScanner.FORM_TAG,
							CFPartitionScanner.TABLE_TAG });

			partitioner.connect(document);

			//returns an IFile which is a subclass of IResource
			try {
				if (element instanceof FileEditorInput 
						|| element instanceof JavaFileEditorInput) 
				{
					document.setParserResource(((FileEditorInput)element).getFile());
					document.clearAllMarkers();
					document.parseDocument();
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}

			document.setDocumentPartitioner(partitioner);
		}
		
		return document;
	}

	protected boolean setDocumentContent(IDocument document,
			IEditorInput editorInput, String encoding) throws CoreException {
		if (editorInput instanceof JavaFileEditorInput) {
			JavaFileEditorInput input = (JavaFileEditorInput) editorInput;
			FileInputStream contentStream = null;
			try {
				contentStream = new FileInputStream(input.getPath(editorInput)
						.toFile());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			setDocumentContent(document, contentStream, encoding);
			return true;
		}
		return super.setDocumentContent(document, editorInput, encoding);
	}

	protected void doSaveDocument(IProgressMonitor monitor, Object element,
			IDocument document, boolean overwrite) throws CoreException {
		if (document instanceof ICFDocument) {
			((ICFDocument) document).clearAllMarkers();
			((ICFDocument) document).parseDocument();
			
		}
		if (element instanceof JavaFileEditorInput) {
		   try {
		       saveExternalFile((JavaFileEditorInput)element,document);
		   }
		   catch (IOException e) {
		       Status status = new Status(IStatus.ERROR,"com.rohanclan.cfml",IStatus.OK,e.getMessage(),e);
		       throw new CoreException(status);
		   }
		   
		}
		super.doSaveDocument(monitor, element, document, overwrite);

	}
	
	private void saveExternalFile(JavaFileEditorInput input, IDocument doc) throws IOException {

	    FileWriter writer = new FileWriter(input.getPath(input).toFile());
	    writer.write(doc.get());
	    writer.close();
	}
	
	
	public boolean isModifiable(Object element) {
		if (!isStateValidated(element)) {
			if (element instanceof IFileEditorInput) {
				return true;
			}
		}
		if (element instanceof JavaFileEditorInput) {
		    JavaFileEditorInput input = (JavaFileEditorInput)element;
	        return input.getPath(input).toFile().canWrite();
		}
		return super.isModifiable(element);
	}
	
	public boolean isReadOnly(Object element) {
	    if (element instanceof JavaFileEditorInput) {
	        JavaFileEditorInput input = (JavaFileEditorInput)element;
	        return !input.getPath(input).toFile().canWrite();
		    
		}
	    return super.isReadOnly(element);
	}
	
}