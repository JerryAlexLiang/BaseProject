package liang.com.baseproject.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import liang.com.baseproject.retrofit.UrlConstants;

/**
 * 创建日期:2020/8/26 on 10:06 AM
 * 描述:
 * 作者: 杨亮
 */
public class URLEncodeUtils {

    public static String URLEncode(String url) {
        String returnurl = null;
        try {
            returnurl = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return returnurl;
    }

    public static String toURLString(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                try {
                    sb.append(URLEncoder.encode(String.valueOf(c), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static String RetrunUrl(String url, String path) {
        String returnUrl = null;
        if (path.contains("http://")) {
//            returnUrl = URLEncode(URLEncode(path));
            returnUrl = path;
        } else {
//            returnUrl = url + UrlConstants.IMG_FRONT_URL + URLEncode(URLEncode(path));
            returnUrl = url + UrlConstants.IMG_FRONT_URL + URLEncode(toURLString(path));
        }
        return returnUrl;
    }

    /**
     * 加载带中文路径的图片
     * @param serverUrl  服务器地址
     * @param path       图片路径
     * @return
     */
    public static String translateUrl(String serverUrl, String path) {
        String returnUrl = null;
        String encode = null;
        try {
            encode = URLEncoder.encode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String replace = encode.replace("%3A", ":").replace("%2F", "/");  //注意的是后边的斜杠
        if (replace.contains("http://")) {
            returnUrl = replace;
        } else {
            returnUrl = serverUrl + UrlConstants.IMG_FRONT_URL + replace;
        }
        return returnUrl;
    }
}
