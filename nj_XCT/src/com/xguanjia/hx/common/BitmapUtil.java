package com.xguanjia.hx.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;

public class BitmapUtil {

	 /**
     * 
     * @param b Bitmap
     * <a href="\"http://www.eoeandroid.com/home.php?mod=space&uid=7300\"" target="\"_blank\"">@return</a> 图片存储的位置
     * @throws FileNotFoundException 
     */
    public static String saveImg(Bitmap b,String name,String type) throws Exception{
            String path = Environment.getExternalStorageDirectory().getPath()+File.separator+"HaoXin/zipImg/";
            File mediaFile=null;
            if(!"".equals(type)){
            	mediaFile = new File(path + File.separator + name + "."+type);
            }else {
            	mediaFile = new File(path + File.separator + name + ".jpg");
			}
            if(mediaFile.exists()){
                    mediaFile.delete();
            }
            if(!new File(path).exists()){
                    new File(path).mkdirs();
            }
            mediaFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(mediaFile);
            b.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            b.recycle();
            b = null;
            System.gc();
            return mediaFile.getPath();
    }
	
	/**
	 * 旋转图片
	 * @param angle
	 * @param bitmap
	 * @return Bitmap
	 */
	public static Bitmap rotaingImageView(File file) {
		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inSampleSize = 8;

		Bitmap bitmap = BitmapFactory.decodeFile(file.toString(), bitmapOptions);
		
		int angle = readPictureDegree(file.toString());
		
		
        //旋转图片 动作
		Matrix matrix = new Matrix();;
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
        		bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
		}
	
	
	/**
	 * 读取图片属性：旋转的角度
	 * @param path 图片绝对路径
	 * @return degree旋转的角度
	 */
    public static int readPictureDegree(String path) {
        int degree  = 0;
        try {
                ExifInterface exifInterface = new ExifInterface(path);
                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
        } catch (IOException e) {
                e.printStackTrace();
        }
        return degree;
    }
}
