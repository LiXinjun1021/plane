package com.beancore.ui;

import com.beancore.config.Config;
import com.beancore.util.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

public class GameButton extends JButton implements ImageObserver, MouseListener {

    private static final long serialVersionUID = 1L;
    private String text;
    private final String BUTTON_HOVER = "BUTTON_HOVER";
    private final String BUTTON_NORMAL = "BUTTON_NORMAL";
    private String buttonStatus;
    private SoundPlayer buttonSoundPlayer;

    public GameButton() {
	super();
	this.text = "";
	initButton();
    }

    public GameButton(String text) {
	super();
	this.text = text;
	initButton();
    }

    @Override
    public void setText(String text) {
	this.text = text;
    }

    private void initButton() {
	this.buttonStatus = BUTTON_NORMAL;
	this.setBorderPainted(false);
	this.setFocusPainted(false);
	this.setContentAreaFilled(false);
	this.addMouseListener(this);
	this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	try {
	    this.buttonSoundPlayer = new SoundPlayer(Config.BUTTON_ACTION_AUDIO);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	Image buttonBgImg = null;
	if (this.buttonStatus.equals(BUTTON_NORMAL)) {
	    buttonBgImg = new ImageIcon(Config.BUTTON_BG_IMG).getImage();
	} else if (this.buttonStatus.equals(BUTTON_HOVER)) {
	    buttonBgImg = new ImageIcon(Config.BUTTON_HOVER_BG_IMG).getImage();
	}
	int buttonWidth = buttonBgImg.getWidth(this);
	int buttonHeight = buttonBgImg.getHeight(this);

	this.setSize(buttonWidth, buttonHeight);
	this.setPreferredSize(new Dimension(buttonWidth, buttonHeight));

	Graphics2D g2d = (Graphics2D) g;
	g2d.drawImage(buttonBgImg, 0, 0, buttonWidth, buttonHeight, this);
	FontMetrics metric = g.getFontMetrics();
	Rectangle2D rect = metric.getStringBounds(text, g);
	g2d.drawString(text, (float) (buttonWidth / 2 - rect.getWidth() / 2),
		(float) ((buttonHeight / 2) + ((metric.getAscent() + metric.getDescent()) / 2 - metric.getDescent())));
    }

    private void buttonHover() {
	this.buttonStatus = BUTTON_HOVER;
    }

    private void buttonNormal() {
	this.buttonStatus = BUTTON_NORMAL;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
	buttonHover();
    }

    @Override
    public void mousePressed(MouseEvent e) {
	buttonHover();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
	buttonNormal();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
	buttonHover();
	this.buttonSoundPlayer.play();
    }

    @Override
    public void mouseExited(MouseEvent e) {
	buttonNormal();
    }

}
