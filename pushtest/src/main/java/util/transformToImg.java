package util;

import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TransformToImg {


    public static boolean GenerateImage(String imgData, String imgName, String imgPath) throws IOException {
        //对Base64字符串解码并生成图片
        if (imgData == null) {
            return false;
        } //图像数据为空
        BASE64Decoder decoder = new BASE64Decoder();

        //Base64解码
        byte[] b = decoder.decodeBuffer(imgData);
        File headPath = new File(imgPath);
        if (!headPath.exists()) {
            headPath.mkdirs();
        }
        //定义图片路径
        String imgFilePath = imgPath + File.separator + imgName + ".jpeg";
        //新生成的图片
        OutputStream out = new FileOutputStream(imgFilePath);
        out.write(b);
        out.flush();
        out.close();
        return true;
    }


}
