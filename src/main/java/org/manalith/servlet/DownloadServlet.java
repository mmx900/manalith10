/*
 * Created on 2005. 5. 14
 */
package org.manalith.servlet;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


/**
 * @author setzer
 */
public class DownloadServlet extends HttpServlet {
    Logger logger = Logger.getLogger(DownloadServlet.class);
    private static final String UPLOAD_PATH = "/blog/upload/"; //FIXME UPLOAD_PATH를 property로
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException{
        ServletOutputStream outputStream = null;
        DataInputStream inputStream = null;
        
        String requested[] = request.getRequestURI().split("/upload/");
        String requestedFilename = getServletContext().getRealPath(
	                UPLOAD_PATH + requested[requested.length - 1]);
        
        try{
	        String filename = URLDecoder.decode(requestedFilename,"UTF-8");
	        File file = new File(filename);
	        
	        if(! file.exists()){
	            //filename을 CP949로 다시 인코딩 시도
	            filename = URLDecoder.decode(requestedFilename,"MS949");
	            //logger.error(filename);
	            file = new File(filename);
	        }
	        
	        if(file.exists()){
		        String mimetype = getServletContext().getMimeType(filename);
		        if(mimetype==null) mimetype="application/octet-stream";
		        
		        response.setContentType(mimetype);
		        response.setContentLength((int)file.length());
		        response.setHeader( "Content-Disposition", "attachement; filename=\"" + filename + "\"" );
		
		        byte[] buffer = new byte[8192];//FIXME Unknown
		        inputStream = new DataInputStream(new FileInputStream(file));
		
		        int length = 0;
		        
		        outputStream = response.getOutputStream();
		        while ((inputStream != null) && ((length = inputStream.read(buffer)) != -1))
		        {
		            outputStream.write(buffer,0,length);
		        }
		        
		        outputStream.flush();
	        }else{
	            throw new ServletException("파일을 찾을 수 없습니다.");
	        }
        }catch(IOException e){
            logger.error(e);
        }finally{
            try{
	            if(inputStream != null) inputStream.close();
	            if(outputStream != null) outputStream.close();
            }catch(IOException e){
                logger.error(e);
            }
        }
    }
}
