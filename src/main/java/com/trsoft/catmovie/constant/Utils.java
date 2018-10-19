package com.trsoft.catmovie.constant;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/10/17 0017.
 */

public class Utils {

    /*方法二：推荐，速度最快
  * 判断是否为整数
  * @param str 传入的字符串
  * @return 是整数返回true,否则返回false
*/
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}
