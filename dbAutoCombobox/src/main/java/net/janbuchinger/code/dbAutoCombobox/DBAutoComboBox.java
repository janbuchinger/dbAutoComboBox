/*
 * Copyright (c) 2006 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * -Redistribution of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MIDROSYSTEMS, INC. ("SUN")
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL,
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */
package net.janbuchinger.code.dbAutoCombobox;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicComboBoxEditor;

import net.janbuchinger.code.mishmash.dbo.DBOLabel;


/**
 * This class is a <code>JComboBox</code> that automatically completes a
 * <code>String</code> that is entered by the user.
 * <p>
 * It is a modification of <code>Java2sAutoTextField</code> downloaded from <a
 * href=
 * "http://www.java2s.com/Code/Java/Swing-Components/AutocompleteComboBox.htm"
 * >http
 * ://www.java2s.com/Code/Java/Swing-Components/AutocompleteComboBox.htm</a>.
 * <p>
 * For use in a database application <code>DBAutoComboBox</code> receives a
 * <code>List</code> of <code>DBOLabel</code>s which have an id.
 * <p>
 * <b><u>Attention</u>:</b> The used data model is not suitable for
 * distinguishing between multiple records with the same label. The source data
 * list should consist of unique strings.
 * 
 * @author Java2s.com (Sun Microsystems?)
 * @author Jan Buchinger
 * 
 * @see DBAutoTextField
 * @see net.janbuchinger.code.mishmash.dbo.DBOLabel
 * @see DBAutoComboBoxModel
 * @see Java2sAutoComboBox
 * @see Java2sAutoTextField
 */
@SuppressWarnings("serial")
public class DBAutoComboBox extends JComboBox<DBOLabel> {
	// public DBAutoComboBox(String label) {
	// this(new Vector<DBOLabel>());
	// }
	private class AutoTextFieldEditor extends BasicComboBoxEditor {

		private DBAutoTextField getAutoTextFieldEditor() {
			return (DBAutoTextField) editor;
		}

		AutoTextFieldEditor(java.util.List<DBOLabel> list, final Object ic) {
			editor = new DBAutoTextField(list, DBAutoComboBox.this);
			if (ic != null)
				if (ic instanceof KeyListener)
					editor.addKeyListener((KeyListener) ic);
		}
	}

	private final JLabel label;

	// @SuppressWarnings("unchecked")
	// public Java2sAutoComboBox(java.util.List list) {
	// isFired = false;
	// autoTextFieldEditor = new AutoTextFieldEditor(list);
	// setEditable(true);
	// // setModel(new DBOCBModel(list, this));
	// setModel(new DefaultComboBoxModel(list.toArray()) {
	//
	// protected void fireContentsChanged(Object obj, int i, int j) {
	// if (!isFired)
	// super.fireContentsChanged(obj, i, j);
	// }
	//
	// });
	// setEditor(autoTextFieldEditor);
	// }

	/*
	 * ADDED
	 */

	/* ADDED */

	// public boolean getFired() {
	// return isFired;
	// }

	private DBAutoComboBoxModel acbModel;

	private AutoTextFieldEditor autoTextFieldEditor;

	private boolean isFired;

	/**
	 * Constructs a <code>DBAutoComboBox</code> with an empty list.
	 */
	public DBAutoComboBox() {
		this(new Vector<DBOLabel>());
	}

	/**
	 * Constructs a <code>DBAutoComboBox</code>.
	 * 
	 * @param list
	 *            The data for <code>JComboBox</code> and auto completion.
	 */
	public DBAutoComboBox(java.util.List<DBOLabel> list) {
		this(list, null);
	}

	/**
	 * Constructs a <code>DBAutoComboBox</code>.
	 * 
	 * @param list
	 *            The data for <code>JComboBox</code> and auto completion.
	 * @param listener
	 *            An object that implements <code>ItemListener</code> and / or
	 *            <code>KeyListener</code> to listen for changes.
	 */
	public DBAutoComboBox(java.util.List<DBOLabel> list, final Object listener) {
		this(list, "", listener);
	}

	/**
	 * Constructs a <code>DBAutoComboBox</code> with an empty list.
	 * 
	 * @param label
	 *            The String to initiate a <code>JLabel</code> with.
	 */
	public DBAutoComboBox(String label) {
		this(new Vector<DBOLabel>(), label, null);
	}

	/**
	 * Constructs a <code>DBAutoComboBox</code>.
	 * 
	 * @param label
	 *            The String to initiate a <code>JLabel</code> with.
	 * @param data
	 *            The data for <code>JComboBox</code> and auto completion.
	 */
	public DBAutoComboBox(String label, Vector<DBOLabel> data) {
		this(data, label, null);
	}

	/**
	 * Constructs a <code>DBAutoComboBox</code>.
	 * 
	 * @param list
	 *            The data for <code>JComboBox</code> and auto completion.
	 * @param label
	 *            The String to initiate a <code>JLabel</code> with.
	 * @param listener
	 *            An object that implements <code>ItemListener</code> and / or
	 *            <code>KeyListener</code> to listen for changes.
	 */
	public DBAutoComboBox(java.util.List<DBOLabel> list, String label, Object listener) {
		isFired = false;
		if (listener != null)
			if (listener instanceof ItemListener)
				addItemListener((ItemListener) listener);
		if (list == null)
			list = new Vector<DBOLabel>();
		autoTextFieldEditor = new AutoTextFieldEditor(list, listener);
		setEditable(true);
		acbModel = new DBAutoComboBoxModel(list) {
			protected void fireContentsChanged(Object obj, int i, int j) {
				if (!isFired)
					super.fireContentsChanged(obj, i, j);
			}
		};
		setModel(acbModel);
		setEditor(autoTextFieldEditor);
		setSelectedIndex(0);
		this.label = label != null ? new JLabel(label) : null;
	}

	/**
	 * Adds an <code>ActionListener</code> to the editor <code>Component</code>
	 * and the <code>JComboBox</code>.
	 * 
	 * @param listener
	 *            The listener to add.
	 * @param command
	 *            A command that is set as the <code>JComboBox</code>es action
	 *            command.
	 */
	public void addActionListenerToTextField(ActionListener listener, String command) {
		editor.addActionListener(listener);
		addActionListener(listener);
		setActionCommand(command);
	}

	/**
	 * Resets the current data <code>List</code> in the <code>JComboBox</code>
	 * and the <code>DBAutoTextField</code>.
	 * 
	 * @param newData
	 *            The data to be set as current list.
	 */
	public void update(Vector<DBOLabel> newData) {
		((DBAutoTextField) autoTextFieldEditor.getAutoTextFieldEditor()).setDataList(newData);
		((DBAutoComboBoxModel) getModel()).reload(newData);
		try {
			setSelectedIndex(0);
		} catch (IllegalArgumentException e) {}
	}

	/**
	 * Sets the current item, if the label already exists it will be selected,
	 * else it will be initiated with the index -1 to indicate a new value /
	 * record.
	 * 
	 * @param label
	 *            The label to be set as current item.
	 */
	public void setNewItem(String label) {
		// ((DBAutoTextField)
		// autoTextFieldEditor.getAutoTextFieldEditor()).getDataList().add(new
		// DBOLabel(-1, label));
		// setSelectedId(-1);
		// setSelectedIndex(0);
		int id = -1;
		if ((id = ((DBAutoComboBoxModel) getModel()).getElementId(label)) < 0)
			((DBAutoComboBoxModel) getModel()).setNewItem(label);
		else
			setSelectedId(id);
		// setSelectedId(-1);
	}

	/**
	 * Finds the currently selected id.
	 * 
	 * @return The id of the currently selected item, -1 if the selected element
	 *         is a new entry.
	 */
	public int getSelectedId() {
		int selId = -1;
		try {
			Object selItem = getSelectedItem();
			if (selItem instanceof String) {
				selId = acbModel.getElementId((String) selItem);
			} else if (selItem instanceof DBOLabel)
				selId = ((DBOLabel) selItem).getId();
			return selId;
		} catch (ArrayIndexOutOfBoundsException aioobe) {
			return selId;
		} catch (NullPointerException npe) {
			return selId;
		}
	}

	/**
	 * Gets the id of the element <code>String</code>.
	 * 
	 * @param element
	 *            The element <code>String</code> to resolve.
	 * @return If the element <code>String</code> was found its id, else -1.
	 */
	public final int getElementId(String element) {
		return acbModel.getElementId(element);
	}

	/**
	 * Selects the specified id if found.
	 * 
	 * @param id
	 *            The id of the label to display.
	 */
	public void setSelectedId(int id) {
		int z = acbModel.getSize();
		for (int i = 0; i < z; i++) {
			if (((DBOLabel) acbModel.getElementAt(i)).getId() == id) {
				setSelectedItem(acbModel.getElementAt(i));
				return;
			}
		}
	}

	/**
	 * Checks whether the search mode is case sensitive.
	 * 
	 * @return <code>true</code> if search mode is case sensitive.
	 */
	public boolean isCaseSensitive() {
		return autoTextFieldEditor.getAutoTextFieldEditor().isCaseSensitive();
	}

	/**
	 * Sets search mode case sensitive.
	 * 
	 * @param flag
	 *            <code>true</code> to enable, false to disable case sensitive.
	 */
	public void setCaseSensitive(boolean flag) {
		autoTextFieldEditor.getAutoTextFieldEditor().setCaseSensitive(flag);
	}

	/**
	 * Checks whether input mode is strict.
	 * 
	 * @return true if strict mode is enabled.
	 */
	public boolean isStrict() {
		return autoTextFieldEditor.getAutoTextFieldEditor().isStrict();
	}

	/**
	 * Sets input mode strict.
	 * 
	 * @param flag
	 *            <code>true</code> to enable, false to disable strict mode.
	 */
	public void setStrict(boolean flag) {
		autoTextFieldEditor.getAutoTextFieldEditor().setStrict(flag);
	}

	/**
	 * Gets the current data <code>List</code>.
	 * 
	 * @return The current data <code>List</code>.
	 */
	public java.util.List<DBOLabel> getDataList() {
		return autoTextFieldEditor.getAutoTextFieldEditor().getDataList();
	}

	// @SuppressWarnings({ "unchecked", "rawtypes" })
	// public void setDataList(java.util.List<DBOComboLabel> list) {
	// autoTextFieldEditor.getAutoTextFieldEditor().setDataList(list);
	// setModel(new DefaultComboBoxModel(list.toArray()));
	// }

	// public fireI

	/**
	 * Sets the value to select.
	 * 
	 * @param value
	 *            The value to select.
	 */
	void setSelectedValue(Object value) {
		if (isFired) {
			return;
		} else {
			isFired = true;
			setSelectedItem(value);
			fireItemStateChanged(new ItemEvent(this, 701, selectedItemReminder, 1));
			isFired = false;
			return;
		}
	}

	/**
	 * Fires <code>ActionEvent</code>...
	 */
	protected void fireActionEvent() {
		if (!isFired)
			super.fireActionEvent();
	}

	/* ADDED */

	// public boolean getFired() {
	// return isFired;
	// }

	/**
	 * Gets the <code>List</code> size.
	 * 
	 * @return The <code>List</code> size.
	 */
	public int collectionSize() {
		return ((DBAutoComboBoxModel) getModel()).getSize();
	}

	/**
	 * Gets the <code>JLabel</code> that was initiated during construction.
	 * 
	 * @return The <code>JLabel</code> that was initiated during construction.
	 */
	public JLabel getLabel() {
		return label;
	}

	/**
	 * Resets the selection to the first item if there is one.
	 */
	public void clear() {
		try {
			setSelectedIndex(0);
		} catch (IllegalArgumentException e) {

		}
	}

	/**
	 * Sets the selected index if possible and swallows
	 * <code>IllegalArgumentException</code>.
	 */
	@Override
	public void setSelectedIndex(int anIndex) {
		try {
			super.setSelectedIndex(anIndex);
		} catch (IllegalArgumentException e) {}
	}

	/**
	 * Gets the label <code>String</code> of the currently selected item.
	 * 
	 * @return The label <code>String</code> of the currently selected item.
	 */
	public String getSelectedLabel() {
		return getSelectedItem().toString();
	}

	/**
	 * Gets the size of the data <code>List</code>.
	 * 
	 * @return The size of the data <code>List</code>.
	 */
	public int getElementCount() {
		return acbModel.getSize();
	}
}
// import java.awt.event.ItemEvent;
// import javax.swing.DefaultComboBoxModel;
// import javax.swing.JComboBox;
// import javax.swing.plaf.basic.BasicComboBoxEditor;
//
// public class Java2sAutoComboBox extends JComboBox {
// private class AutoTextFieldEditor extends BasicComboBoxEditor {
//
// private Java2sAutoTextField getAutoTextFieldEditor() {
// return (Java2sAutoTextField) editor;
// }
//
// AutoTextFieldEditor(java.util.List list) {
// editor = new Java2sAutoTextField(list, Java2sAutoComboBox.this);
// }
// }
//
// public Java2sAutoComboBox(java.util.List list) {
// isFired = false;
// autoTextFieldEditor = new AutoTextFieldEditor(list);
// setEditable(true);
// setModel(new DefaultComboBoxModel(list.toArray()) {
//
// protected void fireContentsChanged(Object obj, int i, int j) {
// if (!isFired)
// super.fireContentsChanged(obj, i, j);
// }
//
// });
// setEditor(autoTextFieldEditor);
// }
//
// public boolean isCaseSensitive() {
// return autoTextFieldEditor.getAutoTextFieldEditor().isCaseSensitive();
// }
//
// public void setCaseSensitive(boolean flag) {
// autoTextFieldEditor.getAutoTextFieldEditor().setCaseSensitive(flag);
// }
//
// public boolean isStrict() {
// return autoTextFieldEditor.getAutoTextFieldEditor().isStrict();
// }
//
// public void setStrict(boolean flag) {
// autoTextFieldEditor.getAutoTextFieldEditor().setStrict(flag);
// }
//
// public java.util.List getDataList() {
// return autoTextFieldEditor.getAutoTextFieldEditor().getDataList();
// }
//
// public void setDataList(java.util.List list) {
// autoTextFieldEditor.getAutoTextFieldEditor().setDataList(list);
// setModel(new DefaultComboBoxModel(list.toArray()));
// }
//
// void setSelectedValue(Object obj) {
// if (isFired) {
// return;
// } else {
// isFired = true;
// setSelectedItem(obj);
// fireItemStateChanged(new ItemEvent(this, 701, selectedItemReminder,
// 1));
// isFired = false;
// return;
// }
// }
//
// protected void fireActionEvent() {
// if (!isFired)
// super.fireActionEvent();
// }
//
// private AutoTextFieldEditor autoTextFieldEditor;
//
// private boolean isFired;
//
// }