/*
 * Created on Oct 28, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.rohanclan.cfml.editors.dnd;

import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.text.ITextSelection;

/**
 * @author Stephen Milligan
 *
 * This listener keeps track of where the mouse is relative to the currently selected text
 * and whether or not the mouse is currently down.
 */
public class SelectionCursorListener implements MouseListener, MouseMoveListener, MouseTrackListener, ISelectionChangedListener {
    /**
     * The text editor that the selection listener is installed on
     */
    private ITextEditor editor = null;
    /**
     * The StyledText that belongs to the viewer
     */
    private StyledText textWidget = null;
    /**
     * The projection viewer for this editor
     */
    private ProjectionViewer viewer = null;
    
    /**
     * This allows us to figure out where a point is in widget co-ordinate space.
     */
    private WidgetPositionTracker widgetOffsetTracker = null;
    
    /**
     * The cursor that indicates stuff can be dragged
     */
    private Cursor arrowCursor = null;
    /**
     * The regular text I-Beam cursor 
     */
    private Cursor textCursor = null;
    
    /**
     * The offset of the start of the selected text in viewer co-ordinates
     */
    public int selectionStart = -1;
    /**
     * The contents of the selection according to the viewer
     */
    public String selection = "";
    /**
     * Is the mouse currently hovering over a selected area
     */
    private boolean hovering = false;
    /**
     * Was the mouse down the last time we were notified
     */
    private boolean mouseDown = false;
    
    /**
     * This allows us to handle the case where the user clicks and releases on a selection.
     * Mouse down sets it to true
     * Mouse move sets it to false
     * Mouse up checks it's value and calls reset() if true.
     */
    private boolean downUp = false;
    
    /**
     * This class listens to the mouse position relative to any selected text 
     * and keeps track of whether or not the mouse is currently over a selection.
     */
    public SelectionCursorListener(ITextEditor editor, ProjectionViewer viewer) {
        this.editor = editor;
        this.textWidget = viewer.getTextWidget();
        this.viewer = viewer;
        arrowCursor = new Cursor(textWidget.getDisplay(),SWT.CURSOR_ARROW);
        textCursor = new Cursor(textWidget.getDisplay(),SWT.CURSOR_IBEAM);
        widgetOffsetTracker = new WidgetPositionTracker(textWidget);
    }
    
    /**
     * Resets the listener to a state where the mouse isn't hovering over a selection.
     *
     */
    public void reset() {
        hovering = false;
        selectionStart = -1;
        selection = "";
        textWidget.setCursor(textCursor);
        mouseDown = false;
        //System.out.println("Listener reset");
    }
    
    /**
     * Allows the drag drop listener to know if it's ok to start a drag.
     * 
     * @return
     */
    public boolean doDrag() {
        if (hovering && mouseDown) {
            return true;
        }
        return false;
    }
    
    
    /**
     * Sent when the mouse pointer passes into the area of
     * the screen covered by a control.
     *
     * @param e an event containing information about the mouse enter
     */
    public void mouseEnter(MouseEvent e) {
        // do nothing
        //reset();
    }

    /**
     * Sent when the mouse pointer passes out of the area of
     * the screen covered by a control.
     *
     * @param e an event containing information about the mouse exit
     */
    public void mouseExit(MouseEvent e) {
        //reset();
    }

    /**
     * Sent when the mouse pointer hovers (that is, stops moving
     * for an (operating system specified) period of time) over
     * a control.
     *
     * @param e an event containing information about the hover
     */
    public void mouseHover(MouseEvent e) {
        // do nothing
    }
    
    
    /**
     * Sent when the mouse moves.
     *
     * @param e an event containing information about the mouse move
     */
    public void mouseMove(MouseEvent e) {
        // If the selection is draggable we want to ignore this event.
        
        if (!mouseDown) {
         
	        Point pt = new Point(e.x,e.y);
	        
	        if (pointOnSelection(pt)) {
	            textWidget.setCursor(arrowCursor);
                hovering = true;
	        }
	        else {
	            textWidget.setCursor(textCursor);
	            hovering = false;
	        }
        }
        else {
            downUp = false;
        }
    }
    
    /**
     * This is notified when the selection is changed in the viewer.
     * 
     * If a drag is in progress or the cursor is already over a selection, 
     * the selection change is ignored.
     * 
     */
    
    public void selectionChanged(SelectionChangedEvent event) {
        if (!hovering) {
	        ITextSelection sel = (ITextSelection)viewer.getSelectionProvider().getSelection();
	        selectionStart = sel.getOffset();
	        selection = sel.getText();
        }
    }


    

    /**
     * Sent when a mouse button is pressed twice within the 
     * (operating system specified) double click period.
     *
     * @param e an event containing information about the mouse double click
     *
     * @see org.eclipse.swt.widgets.Display#getDoubleClickTime()
     */
    public void mouseDoubleClick(MouseEvent e) {
        
    }

    /**
     * Sent when a mouse button is pressed.
     *
     * @param e an event containing information about the mouse button press
     */
    public void mouseDown(MouseEvent e) {
        mouseDown = true;
        downUp = true;
        
    }

    /**
     * Sent when a mouse button is released.
     *
     * @param e an event containing information about the mouse button release
     */
    public void mouseUp(MouseEvent e) {
        
        mouseDown = false;
        if (downUp) {
            reset();
        }
        
    }

	
	/**
	 * Returns true if the given point is on top of a selection.
	 * 
	 * @param pt - Point in widget co-ordinates
	 * @return
	 */
	private boolean pointOnSelection(Point pt) {
	    try {
	        if (selectionStart >= 0) {
	            int offset = widgetOffsetTracker.getWidgetOffset(pt);
	            // Convert to viewer co-ordinates
	            offset = viewer.widgetOffset2ModelOffset(offset);
	            
	            if(selectionStart <= offset 
	                    && selectionStart + selection.length() > offset) {
	                 return true;
	            }
	            else {
	                return false;
	            }
	        }
	        else {
	            return false;
	        }
	    }
        catch (Exception ex) {
            return false;
        }
	}
	
}
