package one;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");

        BASE64Encoder encoder = new BASE64Encoder();

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> list = upload.parseRequest(req);
            for (FileItem fileItem : list){
                if (!fileItem.isFormField()){
                    InputStream inputStream = fileItem.getInputStream();

                    byte[] bytes = new byte[inputStream.available()];
                    inputStream.read(bytes);
                    String result = "data:image/jpg;base64," + encoder.encode(bytes);
                    resp.getWriter().write("<img src='" + result + "'/>");
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

    }
}
