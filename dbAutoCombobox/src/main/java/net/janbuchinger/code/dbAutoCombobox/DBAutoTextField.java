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

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import net.janbuchinger.code.mishmash.dbo.DBOLabel;


/**
 * This class is used by <code>DBAutoComboBox</code>.
 * <p>
 * This class is a modification of <code>Java2sAutoTextField</code> downloaded
 * from <a href=
 * "http://www.java2s.com/Code/Java/Swing-Components/AutocompleteComboBox.htm"
 * >http
 * ://www.java2s.com/Code/Java/Swing-Components/AutocompleteComboBox.htm</a>.
 * 
 * @author Sun Microsystems
 * @author Jan Buchinger
 * 
 * @see DBAutoComboBox
 * @see Java2sAutoComboBox
 * @see Java2sAutoTextField
 */
@SuppressWarnings("serial")
public class DBAutoTextField extends JTextField implements FocusListener {
	class AutoDocument extends PlainDocument {

		public void replace(int i, int j, String s, AttributeSet attributeset) throws BadLocationException {
			super.remove(i, j);
			insertString(i, s, attributeset);
		}

		public void insertString(int i, String s, AttributeSet attributeset) throws BadLocationException {
			if (s == null || "".equals(s))
				return;
			String s1 = getText(0, i);
			String s2 = getMatch(s1 + s);
			int j = (i + s.length()) - 1;
			if (isStrict && s2 == null) {
				s2 = getMatch(s1);
				j--;
			} else if (!isStrict && s2 == null) {
				super.insertString(i, s, attributeset);
				return;
			}
			if (autoComboBox != null && s2 != null)
				autoComboBox.setSelectedValue(s2);
			super.remove(0, getLength());
			super.insertString(0, s2, attributeset);
			setSelectionStart(j + 1);
			setSelectionEnd(getLength());
		}

		public void remove(int i, int j) throws BadLocationException {
			int k = getSelectionStart();
			if (k > 0)
				k--;
			String s = getMatch(getText(0, k));
			if (!isStrict && s == null) {
				super.remove(i, j);
			} else {
				super.remove(0, getLength());
				super.insertString(0, s, null);
			}
			if (autoComboBox != null && s != null)
				autoComboBox.setSelectedValue(s);
			try {
				setSelectionStart(k);
				setSelectionEnd(getLength());
			} catch (Exception exception) {}
		}

	}

	public DBAutoTextField(List<DBOLabel> list) {
		addFocusListener(this);
		isCaseSensitive = false;
		isStrict = true;
		autoComboBox = null;
		if (list == null) {
			throw new IllegalArgumentException("values can not be null");
		} else {
			dataList = list;
			init();
			return;
		}
	}

	DBAutoTextField(List<DBOLabel> list, DBAutoComboBox b) {
		addFocusListener(this);
		isCaseSensitive = false;
		isStrict = true;
		autoComboBox = null;
		if (list == null) {
			throw new IllegalArgumentException("values can not be null");
		} else {
			dataList = list;
			autoComboBox = b;
			init();
			return;
		}
	}

	private void init() {
		setDocument(new AutoDocument());
		if (isStrict && dataList.size() > 0)
			setText(dataList.get(0).toString());
	}

	private String getMatch(String s) {
		for (int i = 0; i < dataList.size(); i++) {
			String s1 = dataList.get(i).toString();
			if (s1 != null) {
				if (!isCaseSensitive && s1.toLowerCase().startsWith(s.toLowerCase()))
					return s1;
				if (isCaseSensitive && s1.startsWith(s))
					return s1;
			}
		}

		return null;
	}

	public void replaceSelection(String s) {
		AutoDocument _lb = (AutoDocument) getDocument();
		if (_lb != null)
			try {
				int i = Math.min(getCaret().getDot(), getCaret().getMark());
				int j = Math.max(getCaret().getDot(), getCaret().getMark());
				_lb.replace(i, j - i, s, null);
			} catch (Exception exception) {}
	}

	public boolean isCaseSensitive() {
		return isCaseSensitive;
	}

	public void setCaseSensitive(boolean flag) {
		isCaseSensitive = flag;
	}

	public boolean isStrict() {
		return isStrict;
	}

	public void setStrict(boolean flag) {
		isStrict = flag;
	}

	public List<DBOLabel> getDataList() {
		return dataList;
	}

	public void setDataList(List<DBOLabel> list) {
		if (list == null) {
			throw new IllegalArgumentException("values can not be null");
		} else {
			dataList = list;
			return;
		}
	}

	private List<DBOLabel> dataList;

	private boolean isCaseSensitive;

	private boolean isStrict;

	private DBAutoComboBox autoComboBox;

	@Override
	public void focusGained(FocusEvent e) {
		selectAll();
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (autoComboBox != null)
			if (getSelectionStart() > 0) {
				int selId = autoComboBox.getElementId(getText().substring(0, getSelectionStart()));
				if (selId > 1)
					replaceSelection("");
				else if (!isStrict)
					replaceSelection("");
			}
	}
}
