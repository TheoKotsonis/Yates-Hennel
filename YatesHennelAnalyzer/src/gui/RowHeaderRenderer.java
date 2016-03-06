package gui;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

class RowHeaderRenderer extends DefaultTableCellRenderer {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RowHeaderRenderer() {
        setHorizontalAlignment(JLabel.CENTER);
    }

    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row,
            int column) {
        if (table != null) {
            JTableHeader header = table.getTableHeader();

            if (header != null) {
                setForeground(header.getForeground());
                setBackground(header.getBackground());
                setFont(header.getFont());
            }
        }

        if (isSelected) {
            setFont(getFont().deriveFont(Font.BOLD));
        }

        setValue(value);
        return this;
    }
}
