/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket.View;

/**
 *
 * @author Bao Hoang
 */
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {

    private Image backgrondImage;

    public BackgroundPanel(String imagePath) {
        this.backgrondImage = new ImageIcon(imagePath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Vẽ hình ảnh nền mờ
        g2d.drawImage(backgrondImage, 0, 0, getWidth(), getHeight(), this);

        // Vẽ lớp màu mờ phía trên hình ảnh nền
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f)); // Độ mờ 0.5
        g2d.setColor(new java.awt.Color(255, 255, 255, 150)); // Màu trắng mờ
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.dispose();
    }
}

