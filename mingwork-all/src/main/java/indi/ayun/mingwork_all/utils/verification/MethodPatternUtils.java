package indi.ayun.mingwork_all.utils.verification;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class MethodPatternUtils {

    /**
     * 编译后的正则表达式缓存
     */
    private static final Map<String, Pattern> PATTERN_CACHE = new ConcurrentHashMap<>();

    /**
     * 编译一个正则表达式，并且进行缓存,如果缓存已存在则使用缓存
     *
     * @param regex 表达式
     * @return 编译后的Pattern
     */
    public static final Pattern compileRegex(String regex) {
        Pattern pattern = PATTERN_CACHE.get(regex);
        if (pattern == null) {
            pattern = Pattern.compile(regex);
            PATTERN_CACHE.put(regex, pattern);
        }
        return pattern;
    }


    //匹配HTML标签,通过下面的表达式可以匹配出HTML中的标签属性。
    public final static Pattern html_patter = Pattern.compile("<\\/?\\w+((\\s+\\w+(\\s*=\\s*(?:\".*?\"|'.*?'|[\\^'\">\\s]+))?)+\\s*|\\s*)\\/?>");

    //抽取注释,如果你需要移除HMTL中的注释，可以使用如下的表达式。
    public final static Pattern notes_patter = Pattern.compile("<!--(.*?)-->");

    //查找CSS属性,通过下面的表达式，可以搜索到相匹配的CSS属性。
    public final static Pattern css_patter = Pattern.compile("^\\s*[a-zA-Z\\-]+\\s*[:]{1}\\s[a-zA-Z0-9\\s.#]+[;]{1}");

    //提取页面超链接,提取html中的超链接。
    public final static Pattern hyperlink_patter = Pattern.compile("(<a\\s*(?!.*\\brel=)[^>]*)(href=\"https?:\\/\\/)((?!(?:(?:www\\.)?'.implode('|(?:www\\.)?', $follow_list).'))[^\"]+)\"((?!.*\\brel=)[^>]*)(?:[^>]*)>");

    //提取网页图片,假若你想提取网页中所有图片信息，可以利用下面的表达式。
    public final static Pattern image_patter = Pattern.compile("\\< *[img][^\\\\>]*[src] *= *[\\\"\\']{0,1}([^\\\"\\'\\ >]*)");

    //提取Color Hex Codes,有时需要抽取网页中的颜色代码，可以使用下面的表达式。
    public final static Pattern color_patter = Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");

    //文件路径及扩展名校验,验证windows下文件路径和扩展名（下面的例子中为.txt文件）
    public final static Pattern route_patter = Pattern.compile("^([a-zA-Z]\\:|\\\\)\\\\([^\\\\]+\\\\)*[^\\/:*?\"<>|]+\\.txt(l)?$");

    //判断IE的版本
    public final static Pattern IE_patter = Pattern.compile("^.*MSIE [5-8](?:\\.[0-9]+)?(?!.*Trident\\/[5-9]\\.0).*$");

    //校验金额
    public final static Pattern money_patter = Pattern.compile("^[0-9]+(.[0-9]{2})?$");

    //校验密码强度
    public final static Pattern password_patter = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$");

    //提取URL链接,下面的这个表达式可以筛选出一段文本中的URL
    public final static Pattern RUL_patter = Pattern.compile("^(f|ht){1}(tp|tps):\\/\\/([\\w-]+\\.)+[\\w-]+(\\/[\\w- ./?%&=]*)?");

    //检查URL的前缀,应用开发中很多时候需要区分请求是HTTPS还是HTTP，通过下面的表达式可以取出一个url的前缀然后再逻辑判断。
    public final static Pattern RUL_refix_patter = Pattern.compile("/^[a-zA-Z]+:\\/\\//");
}
