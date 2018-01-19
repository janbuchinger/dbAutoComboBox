package net.janbuchinger.code.dbAutoCombobox;

import java.util.List;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import net.janbuchinger.code.mishmash.dbo.DBOLabel;



/**
 * This is a <code>ComboBoxModel</code> that is used in
 * <code>DBAutoComboBox</code>.
 * 
 * @author Jan Buchinger
 * 
 */
@SuppressWarnings("serial")
public class DBAutoComboBoxModel extends AbstractListModel<DBOLabel> implements ComboBoxModel<DBOLabel> {

	private Object selectedItem;
	private List<DBOLabel> labels;

	public DBAutoComboBoxModel(List<DBOLabel> list) {
		this.labels = list;
	}

	public Object getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Object newValue) {
		if (newValue instanceof String) {
			int eid = getElementArrayId((String) newValue);

			if (eid > -1)
				selectedItem = labels.get(eid);
			else
				selectedItem = new DBOLabel(-1, (String) newValue);
		} else if (newValue instanceof DBOLabel) {
			selectedItem = newValue;
		} else
			System.err.println("NOT SELECTED: " + newValue.getClass().toString());
		fireContentsChanged(this, 0, getSize());
	}

	public int getSize() {
		return labels.size();
	}

	public DBOLabel getElementAt(int i) {
		return labels.get(i);
	}

	public int getElementId(String s) {
		int r = -1;

		for (int i = 0; i < labels.size(); i++) {
			if (labels.get(i).toString().equals(s)) {
				r = labels.get(i).getId();
				break;
			}
		}
		return r;
	}

	private int getElementArrayId(String s) {
		int r = -1;
		for (int i = 0; i < labels.size(); i++) {
			if (labels.get(i).toString().equals(s)) {
				r = i;
				break;
			}
		}
		return r;
	}

	public void reload(Vector<DBOLabel> labels) {
		this.labels = labels;
		fireContentsChanged(this, 0, getSize() - 1);
	}

	private boolean hasNewItem = false;

	public void setNewItem(String label) {
		if (!hasNewItem) {
			labels.add(new DBOLabel(-1, label));
			fireContentsChanged(this, 0, getSize() - 1);
			hasNewItem = true;
		} else {
			labels.set(getSize() - 1, new DBOLabel(-1, label));
			fireContentsChanged(this, 0, getSize() - 1);
		}
		setSelectedItem(label);
	}
}
