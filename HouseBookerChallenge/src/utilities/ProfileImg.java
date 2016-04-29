package utilities;

import java.awt.image.BufferedImage;

public class ProfileImg {
	public String img;
	public ProfileImg(String img){
		if (img==null)
			this.img = ImageTypes.DEFAULT_PROFILE;
			else this.img = img;
	}
	public String getProfileImg() {
		return this.img;
	}
	public String GetDefaultImage(){
		return ImageTypes.DEFAULT_PROFILE;
	}
	public void setImage(String img) {
		this.img=img;
		
	}
	public void setImage(BufferedImage image, String filetype) {
		this.img=ImageUtils.encodeToString(image, filetype);
		
	}
}

