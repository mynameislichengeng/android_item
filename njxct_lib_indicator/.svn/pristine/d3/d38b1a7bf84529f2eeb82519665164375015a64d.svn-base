package com.haoqee.chatsdk.net;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.haoqee.chatsdk.DB.TCDBHelper;
import com.haoqee.chatsdk.DB.TCMessageTable;
import com.haoqee.chatsdk.Interface.FileDownloadListener;
import com.haoqee.chatsdk.Interface.TCBaseListener;

/**
 * 上传文件到服务器
 * 
 * @author Administrator
 *
 */
public class SocketHttpRequester {
    /**
     * 直接通过HTTP协议提交数据到服务器,实现如下面表单提交功能:
     *   <FORM METHOD=POST ACTION="http://192.168.1.101:8083/upload/servlet/UploadServlet" enctype="multipart/form-data">
            <INPUT TYPE="text" NAME="name">
            <INPUT TYPE="text" NAME="id">
            <input type="file" name="imagefile"/>
            <input type="file" name="zip"/>
         </FORM>
     * @param path 上传路径(注：避免使用localhost或127.0.0.1这样的路径测试，因为它会指向手机模拟器，你可以使用http://www.iteye.cn或http://192.168.1.101:8083这样的路径测试)
     * @param params 请求参数 key为参数名,value为参数值
     * @param file 上传文件
     */
    public static String post(Context context, String path, BaseParameters params, FormFile[] files, TCBaseListener listener) throws Exception{     
        final String BOUNDARY = "---------------------------7da2137580612"; //数据分隔线
        final String endline = "--" + BOUNDARY + "--\r\n";//数据结束标志
        
        String rlt = "";
        
        int fileDataLength = 0;
        for(FormFile uploadFile : files){//得到文件类型数据的总长度
            StringBuilder fileExplain = new StringBuilder();
             fileExplain.append("--");
             fileExplain.append(BOUNDARY);
             fileExplain.append("\r\n");
             fileExplain.append("Content-Disposition: form-data;name=\""+ uploadFile.getParameterName()+"\";filename=\""+ uploadFile.getFilname() + "\"\r\n");
             fileExplain.append("Content-Type: "+ uploadFile.getContentType()+"\r\n\r\n");
             fileExplain.append("\r\n");
             fileDataLength += fileExplain.length();
            if(uploadFile.getInStream()!=null){
                fileDataLength += uploadFile.getFile().length();
             }else{
                 fileDataLength += uploadFile.getData().length;
             }
        }
        StringBuilder textEntity = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {//构造文本类型参数的实体数据
        	textEntity.append("--");
            textEntity.append(BOUNDARY);
            textEntity.append("\r\n");
            textEntity.append("Content-Disposition: form-data; name=\""+ params.getKey(i) + "\"\r\n\r\n");
            textEntity.append(params.getValue(i));
            textEntity.append("\r\n");
		}
        //计算传输给服务器的实体数据总长度
        int dataLength = textEntity.toString().getBytes().length + fileDataLength +  endline.getBytes().length;
        
        URL url = new URL(path);
        int port = url.getPort()==-1 ? 80 : url.getPort();
        Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);           
        OutputStream outStream = socket.getOutputStream();
        //下面完成HTTP请求头的发送
        String requestmethod = "POST "+ url.getPath()+" HTTP/1.1\r\n";
        outStream.write(requestmethod.getBytes());
        String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
        outStream.write(accept.getBytes());
        String language = "Accept-Language: zh-CN\r\n";
        outStream.write(language.getBytes());
        String contenttype = "Content-Type: multipart/form-data; boundary="+ BOUNDARY+ "\r\n";
        outStream.write(contenttype.getBytes());
        String contentlength = "Content-Length: "+ dataLength + "\r\n";
        outStream.write(contentlength.getBytes());
        String alive = "Connection: Keep-Alive\r\n";
        outStream.write(alive.getBytes());
        String host = "Host: "+ url.getHost() +":"+ port +"\r\n";
        outStream.write(host.getBytes());
        //写完HTTP请求头后根据HTTP协议再写一个回车换行
        outStream.write("\r\n".getBytes());
        //把所有文本类型的实体数据发送出来
        outStream.write(textEntity.toString().getBytes());           
        //把所有文件类型的实体数据发送出来
        for(FormFile uploadFile : files){
            StringBuilder fileEntity = new StringBuilder();
             fileEntity.append("--");
             fileEntity.append(BOUNDARY);
             fileEntity.append("\r\n");
             fileEntity.append("Content-Disposition: form-data;name=\""+ uploadFile.getParameterName()+"\";filename=\""+ uploadFile.getFilname() + "\"\r\n");
             fileEntity.append("Content-Type: "+ uploadFile.getContentType()+"\r\n\r\n");
             outStream.write(fileEntity.toString().getBytes());
             if(uploadFile.getInStream()!=null){
                 byte[] buffer = new byte[1024 * 2];
                 int len = 0;
                 
               //写入OutputStream的偏移量
                 int offset = 0;
                 //写入进度
                 int progress = 0;
                 
                 while((len = uploadFile.getInStream().read(buffer, 0, 1024))!=-1){
                     outStream.write(buffer, 0, len);
                     offset += len;
                     
                     if(offset >= uploadFile.getFile().length()){ 	 			//偏移量与文件总大小相等，表示写入完成
                 		//listener.onProgress(100);		//设置上传进度为100
                    	progress = 100;
              			updateProgress(context, params.getValue("tag"), progress);
              			listener.onProgress(100);
                 	}else {
                 		int uploadPro = (int) (offset * 100 / uploadFile.getFile().length());		//计算上传进度
                     	
                     	if(uploadPro - progress >= Utility.UPDATE_PROGRESS){		//如果两次进度差距为2, 更新上传进度
                     		progress = uploadPro;
                     		updateProgress(context, params.getValue("tag"), progress);
                     		listener.onProgress(progress);
                     	}
     				}
                 }
                 
                 uploadFile.getInStream().close();
             }else{
                 outStream.write(uploadFile.getData(), 0, uploadFile.getData().length);
             }
             outStream.write("\r\n".getBytes());
        }
        //下面发送数据结束标志，表示数据已经结束
        outStream.write(endline.getBytes());
        
       /* BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        if(reader.readLine().indexOf("200")==-1){//读取web服务器返回的数据，判断请求码是否为200，如果不是200，代表请求失败
            return false;
        }*/
        
        rlt = read(socket.getInputStream());
        outStream.flush();
        outStream.close();
        //reader.close();
        socket.close();
        //return true;
        
        return rlt;
    }
    
    private static void updateProgress(Context context, String tag, int progress){
    	SQLiteDatabase database = TCDBHelper.getInstance(context).getWritableDatabase();
		TCMessageTable messageTable = new TCMessageTable(database);
		messageTable.updateUploadProgress(tag, progress);
    }
    
    /**
     * 提交数据到服务器
     * @param path 上传路径(注：避免使用localhost或127.0.0.1这样的路径测试，因为它会指向手机模拟器，你可以使用http://www.itcast.cn或http://192.168.1.10:8080这样的路径测试)
     * @param params 请求参数 key为参数名,value为参数值
     * @param file 上传文件
     */
    public static String post(Context context, String path, BaseParameters params, FormFile file, TCBaseListener listener) throws Exception{
       return post(context, path, params, new FormFile[]{file}, listener);
    }
    
    private static String read(InputStream in) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);
        String rlt = "";
        String line;
        while((line = r.readLine()) != null){
        	rlt = line;
        }
        
        in.close();
        return rlt;
    }
    
    /**
     * 下载文件
     * @param context
     * @param downloadUrl				文件URL
     * @param path						文件存储路径
     * @param listener					文件下载进度监听对象
     * @throws Exception
     */
    public static void downloadFile(Context context, String downloadUrl, String path, FileDownloadListener listener) throws Exception{
    	int index = downloadUrl.lastIndexOf("/");
    	String beforeUrl = downloadUrl.substring(0, index + 1);
    	String name = downloadUrl.substring(index + 1, downloadUrl.length());
    	URL url = new URL(beforeUrl + URLEncoder.encode(name, "UTF-8").replace("+", "%20"));
        //int port = url.getPort()==-1 ? 80 : url.getPort();
        //ServerSocket serverSocket = new ServerSocket(port);
        //Socket socket = serverSocket.accept();
        
        //Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);           
        
        //InputStream inputStream = socket.getInputStream();
        
        HttpURLConnection conn = null;
		InputStream inputStream = null;
        
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET") ;  
        conn.setRequestProperty(  
                "Accept",  
                "image/gif, image/jpeg, image/pjpeg, image/pjpeg, " +  
                "application/x-shockwave-flash, application/xaml+xml, " +  
                "application/vnd.ms-xpsdocument, application/x-ms-xbap, " +  
                "application/x-ms-application, application/vnd.ms-excel, " +  
                "application/vnd.ms-powerpoint, application/msword, */*");  
        conn.setRequestProperty("Charset", "UTF-8");  
        //设置浏览器类型和版本、操作系统，<strong>使用</strong>语言等信息  
        conn.setRequestProperty(  
                "User-Agent",  
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.2; Trident/4.0; " +  
                ".NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; " +  
                ".NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");  
		inputStream = conn.getInputStream();
		// Get the length
		//int length = (int) conn.getContentLength();
        
        if(inputStream != null){
        	
        	byte[] buffer = new byte[1024 * 2];
            int len = 0;
            
          //写入OutputStream的偏移量
            int offset = 0;
            //写入进度
            int progress = 0;
            
            /*String directory = path.substring(0, path.lastIndexOf("/"));
    		
    		File directoryFile = new File(directory);
    		
    		if(!directoryFile.exists()){
    			directoryFile.mkdirs();
    		}*/
            
            File file = new File(path);  
            FileOutputStream outStream = new FileOutputStream(file);
            
            int totalSize = (int) conn.getContentLength();
        	while((len = inputStream.read(buffer, 0, 1024))!=-1){
        		outStream.write(buffer, 0, len);
                offset += len;
                
                if(offset >= totalSize){ 	 			//偏移量与文件总大小相等，表示写入完成
                	progress = 100;
                	listener.downloadProgress(100);
            	}else {
            		int uploadPro = (int) (offset * 100 / totalSize);		//计算上传进度
                	
                	if(uploadPro - progress >= Utility.UPDATE_PROGRESS){		//如果两次进度差距为2, 更新上传进度
                		progress = uploadPro;
                		listener.downloadProgress(progress);
                	}
				}
        	}
        	
        	outStream.flush();
        	outStream.close();
        	if (inputStream != null) {
        		inputStream.close();
			}
        	//socket.close();
        	//serverSocket.close();
        }
    }
}