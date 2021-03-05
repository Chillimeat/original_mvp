package indi.ayun.original_mvp.crash;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionPrompt {
    /**
     * 将异常栈信息转为字符串
     * @param e 字符串
     * @return 异常栈
     */
    public static String throwable2String(Throwable e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }
}
