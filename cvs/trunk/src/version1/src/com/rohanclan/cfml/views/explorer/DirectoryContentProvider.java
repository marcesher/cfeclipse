/*
 * Created on Nov 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.views.explorer;

import java.io.File;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.part.ViewPart;

import com.rohanclan.cfml.ftp.*;
import com.rohanclan.cfml.util.AlertUtils;



class DirectoryContentProvider implements IStructuredContentProvider, ITreeContentProvider {
    

    private FileNameFilter directoryFilter = new FileNameFilter();
    private IFileProvider fileProvider = null;
    private Viewer viewer = null;
    private ViewPart viewpart = null;
    
    public DirectoryContentProvider(ViewPart viewpart) {
        directoryFilter.allowFiles(false);
        this.viewpart = viewpart;
    }
    
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        this.viewer = viewer;
        try {
	    	if (fileProvider != null) {
	    		fileProvider.dispose();
	    	}
	        if (newInput instanceof IFileProvider) {

		        AlertUtils.showStatusErrorMessage(null,viewpart);
		        AlertUtils.showStatusMessage("Connected to: Local Filesystem",viewpart);
	            fileProvider = (IFileProvider)newInput;
	        }
	        else if (newInput instanceof FtpConnectionProperties) {
	        	fileProvider = FtpConnection.getInstance();
	        	((FtpConnection)fileProvider).connect((FtpConnectionProperties)newInput);
	        }
	        else {
	        	
	            fileProvider = null;
	        }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void dispose() {
    	if (fileProvider != null) {
    		fileProvider.dispose();
    	}
    }
    
   
    
    public Object[] getElements(Object inputElement) {
        
        return getChildren(inputElement);
    }
    
    public Object[] getChildren(Object parentElement) {
       
    	try {
    		
	        if (fileProvider == null){
	            return new Object[] {IFileProvider.INVALID_FILESYSTEM};
	        }
	        if (parentElement instanceof LocalFileSystem
	        		|| parentElement instanceof FtpConnectionProperties) {
	        	
	        	return fileProvider.getRoots();
	        	
	        } else if (parentElement instanceof RemoteFile) {
	            RemoteFile file = (RemoteFile)parentElement;
	            
	            return fileProvider.getChildren(((RemoteFile)parentElement).getAbsolutePath(),directoryFilter);
	            
	        } else {
                return fileProvider.getChildren(parentElement.toString(),directoryFilter);
	        }
    	}
        catch (Exception e) {
        	e.printStackTrace();
            return new Object[] {"Error! " + e.getMessage()};
        }
        
    }
    public Object getParent(Object element) {
        return null;
    }
    
    public boolean hasChildren(Object element) {
    	try {
    		if (element instanceof RemoteFile) {
    		   return ((RemoteFile)element).isDirectory() & ((RemoteFile)element).canRead();
    		}
    		else if (element instanceof File) {
    		    return ((File)element).isDirectory();
    		}
    		else if (element instanceof String
    		        	&& element.toString().equals(FtpConnection.CONNECT_FAILED)) {
    		        return false;
    		}
    		return true;
    	}
    	catch (Exception e) {
    		return false;
    	}
    }
}