/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assets;

/**
 *
 * @author ASUS
 */
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class NumberInputField extends JTextField {

    public NumberInputField(int columns) {
        super(columns);
        ((AbstractDocument) this.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                String text = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
                if (text.isEmpty() || text.matches("\\d{0,10}")) {
                    super.insertString(fb, offset, string, attr);
                } else {
                    Toolkit.getDefaultToolkit().beep(); // Phát ra tiếng bíp khi có lỗi
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                if (newText.isEmpty() || newText.matches("\\d{0,10}")) {
                    super.replace(fb, offset, length, text, attrs);
                } else {
                    Toolkit.getDefaultToolkit().beep(); // Phát ra tiếng bíp khi có lỗi
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
