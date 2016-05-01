package gui;

import java.awt.Image;

import javax.swing.ImageIcon;

import utilities.ImageTypes;
import utilities.ImageUtils;

public class GUIRLoader {
	//public ImageIcon	logotipo	= new javax.swing.ImageIcon(getClass().getResource("/presentation/logo.png"));
	public ImageIcon logotipo= new ImageIcon(ImageUtils.decodeToImage(ImageTypes.LOGO));
	//public Image		icono		= new javax.swing.ImageIcon(getClass().getResource("/presentation/icon.png")).getImage();
	public Image icono = new ImageIcon(ImageUtils.decodeToImage(ImageTypes.ICON)).getImage();
	
	}