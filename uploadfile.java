package Upload;

import java.io.*;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import java.sql.*;
/**
 * Servlet implementation class uploadfile
 */
@WebServlet("/uploadfile")
public class uploadfile extends HttpServlet {

	    private static final long serialVersionUID = 17864986468494864L;


	    // location to store file uploaded
	    private static final String UPLOAD_DIRECTORY = "img";
	    
	    // upload settings

	    public uploadfile() {
	        super();
	        // TODO Auto-generated constructor stub
	    }
	    
	    /**
	     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	     */
	    
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // TODO Auto-generated method stub
	        //doPost(request, response);
	        //throw new ServletException("GET method used with " +  getClass( ).getName( )+": POST method required.");
	    	request.setAttribute("path", "filePath");
	        request.getRequestDispatcher("addGame.jsp").forward(request, response);
	    }
	    
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        System.out.println("demo");
	        
	        
	        if (!ServletFileUpload.isMultipartContent(request)) {
	            // if not, we stop here
	            PrintWriter writer = response.getWriter();
	            writer.println("Error: Form must has enctype=multipart/form-data.");
	            writer.flush();
	            return;
	        }

	        // configures upload settings
	        DiskFileItemFactory factory = new DiskFileItemFactory();
	        // sets temporary location to store files
	        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

	        ServletFileUpload upload = new ServletFileUpload(factory);


	        // constructs the directory path to store upload file
	        // this path is relative to application's directory
	        String uploadPath = getServletContext().getRealPath("")+ File.separator + UPLOAD_DIRECTORY;
	        System.out.println(uploadPath);
	        
			// creates the directory if it does not exist
	        File uploadDir = new File(uploadPath);
	        if (!uploadDir.exists()) {
	            uploadDir.mkdir();
	        }

	        try {
	            // parses the request's content to extract file data

	            List<FileItem> formItems = upload.parseRequest((HttpServletRequest)request);
	            if (formItems != null && formItems.size() > 0) {
	                // iterates over form's fields
	                for (FileItem item : formItems) {
	                    // processes only fields that are not form fields
	                    if (!item.isFormField()) {
	                        String fileName = new File(item.getName()).getName();
	                        String filePath = uploadPath + File.separator + fileName;
	                        System.out.println(fileName);
	                        File storeFile = new File(filePath);
	                        // C:\tomcat\apache-tomcat-7.0.40\webapps\data\
	                        // saves the file on disk
	                        item.write(storeFile);
	                        request.setAttribute("message","Upload has been done successfully!");
	                        System.out.println("demo Success");
	                        this.getServletContext().setAttribute("path","img/"+fileName);
	                        this.getServletContext().setAttribute("name", fileName);
	                        String fileP = "img/"+fileName;
	                        response.sendRedirect("addGame.jsp");
	                  /*      
	                        
	                                    	        	            	        	
	            				Class.forName("com.mysql.jdbc.Driver");
	            				String connURL = "jdbc:mysql://localhost/spstore?user=root&password=root&useSSL=false";
	            				Connection conn = DriverManager.getConnection(connURL);
	            				
	            				PreparedStatement pstmt = conn.prepareStatement("update game set ImagePath = ? where GameID = last_insert_id()");
	            				
	            				pstmt.setString(1, fileP);
	            				
	            				pstmt.executeUpdate();*/
	                    }
	                }
	            }
	        } catch (Exception ex) {
	            request.setAttribute("message","There was an error: " + ex.getMessage());
	            System.out.println("demo Fail: " +   ex.getMessage() );
	        }
	        
	        
	    
	    }

}
