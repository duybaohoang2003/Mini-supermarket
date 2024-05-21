/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author ASUS
 */
public class SetIcon {
    public static ImageIcon SetSize(String IconPath, int width, int height){
        ImageIcon OldIcon = new ImageIcon(IconPath);
        
        // Tạo một bản sao của icon với kích thước mới
        Image scaledImage = OldIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
