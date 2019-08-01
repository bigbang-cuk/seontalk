package seontalk.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import seontalk.util.FilePath;

public class ProfileImg extends JPanel{
	String 				imgName = new String();			//사용자 이미지이름 받을 변수
	Image 				img 	= null;					//원본이미지를 받을 이미지변수
	Image 				img2 	= new ImageIcon(FilePath.SrcPath+"profile-basic.png").getImage();	//프로필테두리
	int 				imgX 		= 0;	//이미지가 그려질 x좌표 시작점
	int 				imgY 		= 0;	//이미지가 그려질 y좌표 시작점
	int 				setWidth 	= 0;	//그려지는 가로길이
	int 				setHeight 	= 0;	//그려지는 세로길이
	
	//생성자의 파라미터를 통해 프로필이미지를 원하는 위치와 크기로 만들 수 있다.
	public ProfileImg(String imgName,int imgX,int imgY,int setWidth,int setHeight) {
		this.imgName = imgName;
		this.imgX = imgX;
		this.imgY = imgY;
		this.setWidth = setWidth;
		this.setHeight = setHeight;
		img = new ImageIcon(FilePath.ImgPath+imgName).getImage();
	}
	
	public void paintComponent(Graphics g) {
		int imgWidth = img.getWidth(null);		//원본 이미지의 가로길이
		int imgHeight = img.getHeight(null);	//원본 이미지의 세로길이
		/////////////////////////////원본이미지가 정사각형으로 잘리는 부분//////////////////////////
		try {
			// 이미지를 자르기 위해서는 byte형식인 BufferedImage로 원본을 불러와야함.
			BufferedImage bi = ImageIO.read(new File(FilePath.ImgPath+imgName));
			//정사각형으로 자를때 기준은 더 짧은 변의 길이로 맞춤.
			if(imgWidth>imgHeight) {//가로가 긴 경우
				//파라미터(x좌표시작,y좌표시작,자를 가로길이,자를 세로길이)
				bi = bi.getSubimage((imgWidth-imgHeight)/2, 0, imgHeight, imgHeight);
				//자른 이미지를 원본이미지 변수에 대입(원본이미지는 더이상 필요 없으므로...)
				img = new ImageIcon(bi).getImage();
			}
			else if(imgWidth<imgHeight) {//세로가 긴 경우
				bi = bi.getSubimage(0, (imgHeight-imgWidth)/2, imgWidth, imgWidth);
				img = new ImageIcon(bi).getImage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//////////////////////////////////////////////////////////////////////////////////
		/////////////////////////프로필사진,프로필테두리 사이즈 맞추는 부분//////////////////////////
		//프로필사진
		Image scaledImg = img.getScaledInstance(setWidth, setHeight, Image.SCALE_SMOOTH);
		img = new ImageIcon(scaledImg).getImage();
		//프로필 테두리
		Image scaledImg2 = img2.getScaledInstance(setWidth, setHeight, Image.SCALE_SMOOTH);
		img2 = new ImageIcon(scaledImg2).getImage();
		//////////////////////////////////////////////////////////////////////////////////
		
		//수정된 프로필사진,프로필테두리가 그려지는 부분
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(img, imgX, imgY, null);
		g2.drawImage(img2, imgX, imgY, null);
	}
}
