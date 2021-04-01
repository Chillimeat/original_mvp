package indi.ayun.original_mvp.utils;

import indi.ayun.original_mvp.mlog.MLog;
import indi.ayun.original_mvp.utils.network.URIUtil;

/**
 * @Description 文件类型
 * @Author Modify by ayun on 2021/3/24 22:18
 */
public class MIMETypeUtil {
    private static MIMETypeUtil mimeTypeUtil;

    /**
     * 不需要单例，只要链式写法就好
     * @return MediaUtil
     */
    public static MIMETypeUtil getInstance(){
        if (mimeTypeUtil==null)mimeTypeUtil=new MIMETypeUtil();
        return mimeTypeUtil;
    }
    //文档文件类型的
    private String DOCUMENT_AI="ai";        //application/postscript
    private String DOCUMENT_EPS="eps";        //application/postscript
    private String DOCUMENT_EXE="exe";        //application/octet-stream
    private String DOCUMENT_DOC="doc";        //application/vnd.ms-word
    private String DOCUMENT_XLS="xls";        //application/vnd.ms-excel
    private String DOCUMENT_PPT="ppt";        //application/vnd.ms-powerpoint
    private String DOCUMENT_PPS="pps";        //application/vnd.ms-powerpoint
    private String DOCUMENT_PDF="pdf";        //application/pdf
    private String DOCUMENT_XML="xml";       //application/xml
    private String DOCUMENT_ODT="odt";        //application/vnd.oasis.opendocument.text
    private String DOCUMENT_SWF="swf";        //application/x-shockwave-flash

    //压缩文件类型的
    private String COMPRESS_GZ="gz";        //application/x-gzip
    private String COMPRESS_TGZ="tgz";        //application/x-gzip
    private String COMPRESS_BZ="bz";        //application/x-bzip2
    private String COMPRESS_BZ2="bz2";        //application/x-bzip2
    private String COMPRESS_TBZ="tbz";        //application/x-bzip2
    private String COMPRESS_ZIP="zip";       //application/zip
    private String COMPRESS_RAR="rar";        //application/x-rar
    private String COMPRESS_TAR="tar";        //application/x-tar
    private String COMPRESS_7Z="7z";        //application/x-7z-compressed

    //文字类型
    private String WORDS_TXT="txt";        //text/plain
    private String WORDS_PHP="php";        //text/x-php
    private String WORDS_HTML="html";        //text/html
    private String WORDS_HTM="htm";       //text/html
    private String WORDS_JS="js";        //text/javascript
    private String WORDS_CSS="css";        //text/css
    private String WORDS_RTF="rtf";        //text/rtf
    private String WORDS_RTFD="rtfd";        //text/rtfd
    private String WORDS_PY="py";        //text/x-python
    private String WORDS_JAVA="java";        //text/x-java-source
    private String WORDS_RB="rb";       //text/x-ruby
    private String WORDS_SH="sh";        //text/x-shellscript
    private String WORDS_PL="pl";        //text/x-perl
    private String WORDS_SQL="sql";        //text/x-sql

    //图片类型的
    private String PICTURE_BMP="bmp";        //image/x-ms-bmp
    private String PICTURE_JPG="jpg";        //image/jpeg
    private String PICTURE_JPEG="jpeg";        //image/jpeg
    private String PICTURE_GIF="gif";        //image/gif
    private String PICTURE_PNG="png";        //image/png
    private String PICTURE_TIF="tif";       //image/tiff
    private String PICTURE_TIFF="tiff";        //image/tiff
    private String PICTURE_TGA="tga";        //image/x-targa
    private String PICTURE_PSD="psd";        //image/vnd.adobe.photoshop
    private String PICTURE_WEBP="Webp";        //image/vnd.adobe.photoshop"},
    private String PICTURE_BAIBMP="baiBMP";        //image/vnd.adobe.photoshop"},
    private String PICTURE_PCX="PCX";        //image/vnd.adobe.photoshop"},
    private String PICTURE_EXIF="EXIF";        //image/vnd.adobe.photoshop"},
    private String PICTURE_FPX="FPX";        //image/vnd.adobe.photoshop"},
    private String PICTURE_SVG="SVG";        //image/vnd.adobe.photoshop"},
    private String PICTURE_CDR="CDR";        //image/vnd.adobe.photoshop"},
    private String PICTURE_PCD="PCD";        //image/vnd.adobe.photoshop"},
    private String PICTURE_DXF="DXF";        //image/vnd.adobe.photoshop"},
    private String PICTURE_UFO="UFO";        //image/vnd.adobe.photoshop"},
    private String PICTURE_EPS="EPS,";        //image/vnd.adobe.photoshop"},
    private String PICTURE_AI="AI";        //image/vnd.adobe.photoshop"},
    private String PICTURE_RAW="RAW";        //image/vnd.adobe.photoshop"},
    private String PICTURE_WMF="WMF,";        //image/vnd.adobe.photoshop"},
    private String PICTURE_FLIC="FLIC";        //image/vnd.adobe.photoshop"},
    private String PICTURE_EMF="EMF,";        //image/vnd.adobe.photoshop"},
    private String PICTURE_ICO="ICO";        //image/vnd.adobe.photoshop"}

    //音频文件类型的
    private String AUDIO_MP3="mp3";        //audio/mpeg
    private String AUDIO_MID="mid";        //audio/midi
    private String AUDIO_OGG="ogg";        //audio/ogg
    private String AUDIO_MP4A="mp4a";        //audio/mp4
    private String AUDIO_WAV="wav";        //audio/wav
    private String AUDIO_WMA="wma";        //audio/x-ms-wma

    //视频文件类型的
    private String VIDEO_AVI="avi";        //video/x-msvideo
    private String VIDEO_DV="dv";        //video/x-dv
    private String VIDEO_MP4="mp4";        //video/mp4
    private String VIDEO_MPEG="mpeg";        //video/mpeg
    private String VIDEO_MPG="mpg";        //video/mpeg
    private String VIDEO_MOV="mov";        //video/quicktime
    private String VIDEO_WM="wm";        //video/x-ms-wmv
    private String VIDEO_FLV="flv";        //video/x-flv
    private String VIDEO_MKV="mkv";        //video/x-matroska

    //文档文件类型的
    private final String DOCUMENT_AI_EPS_MIME="application/postscript";
    private final String DOCUMENT_EXE_MIME="application/octet-stream";
    private final String DOCUMENT_DOC_MIME="application/vnd.ms-word";
    private final String DOCUMENT_XLS_MIME="application/vnd.ms-excel";
    private final String DOCUMENT_PPT_PPS_MIME="application/vnd.ms-powerpoint";
    private final String DOCUMENT_PDF_MIME="application/pdf";
    private final String DOCUMENT_XML_MIME="application/xml";
    private final String DOCUMENT_ODT_MIME="application/vnd.oasis.opendocument.text";
    private final String DOCUMENT_SWF_MIME="application/x-shockwave-flash";

    //压缩文件类型的
    private final String COMPRESS_GZ_MIME="gzapplication/x-gzip";
    private final String COMPRESS_TGZ_MIME="application/x-gzip";
    private final String COMPRESS_BZ_BZ2_TBZ_MIME="application/x-bzip2";
    private final String COMPRESS_ZIP_MIME="application/zip";
    private final String COMPRESS_RAR_MIME="application/x-rar";
    private final String COMPRESS_TAR_MIME="application/x-tar";
    private final String COMPRESS_7Z_MIME="application/x-7z-compressed";

    //文字类型
    private final String WORDS_TXT_MIME="text/plain";
    private final String WORDS_PHP_MIME="text/x-php";
    private final String WORDS_HTML_HTM_MIME="text/html";
    private final String WORDS_JS_MIME="text/javascript";
    private final String WORDS_CSS_MIME="text/css";
    private final String WORDS_RTF_MIME="text/rtf";
    private final String WORDS_RTFD_MIME="text/rtfd";
    private final String WORDS_PY_MIME="text/x-python";
    private final String WORDS_JAVA_MIME="text/x-java-source";
    private final String WORDS_RB_MIME="text/x-ruby";
    private final String WORDS_SH_MIME="text/x-shellscript";
    private final String WORDS_PL_MIME="text/x-perl";
    private final String WORDS_SQL_MIME="text/x-sql";

    //图片类型的
    private final String PICTURE_BMP_MIME="image/x-ms-bmp";
    private final String PICTURE_JPEG_JPG_MIME="image/jpeg";
    private final String PICTURE_GIF_MIME="image/gif";
    private final String PICTURE_PNG_MIME="image/png";
    private final String PICTURE_TIF_TIFF_MIME="image/tiff";
    private final String PICTURE_TGA_MIME="image/x-targa";
    private final String PICTURE_PSD_MIME="image/vnd.adobe.photoshop";

    //音频文件类型的
    private final String AUDIO_MP3_MIME="audio/mpeg";
    private final String AUDIO_MID_MIME="audio/midi";
    private final String AUDIO_OGG_MIME="audio/ogg";
    private final String AUDIO_MP4A_MIME="audio/mp4";
    private final String AUDIO_WAV_MIME="audio/wav";
    private final String AUDIO_WMA_MIME="audio/x-ms-wma";

    //视频文件类型的
    private final String VIDEO_AVI_MIME="video/x-msvideo";
    private final String VIDEO_DV_MIME="video/x-dv";
    private final String VIDEO_MP4_MIME="video/mp4";
    private final String VIDEO_MPEG_MPG_MIME="video/mpeg";
    private final String VIDEO_MOV_MIME="video/quicktime";
    private final String VIDEO_WM_MIME="video/x-ms-wmv";
    private final String VIDEO_FLV_MIME="video/x-flv";
    private final String VIDEO_MKV_MIME="video/x-matroska";
    public String getType(String mime){
        switch (mime){
            case DOCUMENT_AI_EPS_MIME:
                return DOCUMENT_AI+"*"+DOCUMENT_EPS;
            case DOCUMENT_EXE_MIME:
                return DOCUMENT_EXE;
            case DOCUMENT_DOC_MIME:
                return DOCUMENT_DOC;
            case DOCUMENT_XLS_MIME:
                return DOCUMENT_XLS;
            case DOCUMENT_PPT_PPS_MIME:
                return DOCUMENT_PPT+"*"+DOCUMENT_PPS;
            case DOCUMENT_PDF_MIME:
                return DOCUMENT_PDF;
            case DOCUMENT_XML_MIME:
                return DOCUMENT_XML;
            case DOCUMENT_ODT_MIME:
                return DOCUMENT_ODT;
            case DOCUMENT_SWF_MIME:
                return DOCUMENT_SWF;
            case COMPRESS_GZ_MIME:
                return COMPRESS_GZ;
            case COMPRESS_TGZ_MIME:
                return COMPRESS_TGZ;
            case COMPRESS_BZ_BZ2_TBZ_MIME:
                return COMPRESS_BZ+"*"+COMPRESS_BZ2+"*"+COMPRESS_TBZ;
            case COMPRESS_ZIP_MIME:
                return COMPRESS_ZIP;
            case COMPRESS_RAR_MIME:
                return COMPRESS_RAR;
            case COMPRESS_TAR_MIME:
                return COMPRESS_TAR;
            case COMPRESS_7Z_MIME:
                return COMPRESS_7Z;
            case WORDS_TXT_MIME:
                return WORDS_TXT;
            case WORDS_PHP_MIME:
                return WORDS_PHP;
            case WORDS_HTML_HTM_MIME:
                return WORDS_HTML+"*"+WORDS_HTM;
            case WORDS_JS_MIME:
                return WORDS_JS;
            case WORDS_CSS_MIME:
                return WORDS_CSS;
            case WORDS_RTF_MIME:
                return WORDS_RTF;
            case WORDS_RTFD_MIME:
                return WORDS_RTFD;
            case WORDS_PY_MIME:
                return WORDS_PY;
            case WORDS_JAVA_MIME:
                return WORDS_JAVA;
            case WORDS_RB_MIME:
                return WORDS_RB;
            case WORDS_SH_MIME:
                return WORDS_SH;
            case WORDS_PL_MIME:
                return WORDS_PL;
            case WORDS_SQL_MIME:
                return WORDS_SQL;
            case PICTURE_BMP_MIME:
                return PICTURE_BMP;
            case PICTURE_JPEG_JPG_MIME:
                return PICTURE_JPEG+"*"+PICTURE_JPG;
            case PICTURE_GIF_MIME:
                return PICTURE_GIF;
            case PICTURE_PNG_MIME:
                return PICTURE_PNG;
            case PICTURE_TIF_TIFF_MIME:
                return PICTURE_TIF+"*"+PICTURE_TIFF;
            case PICTURE_TGA_MIME:
                return PICTURE_TGA;
            case PICTURE_PSD_MIME:
                return PICTURE_PSD;
            case AUDIO_MP3_MIME:
                return AUDIO_MP3;
            case AUDIO_MID_MIME:
                return AUDIO_MID;
            case AUDIO_OGG_MIME:
                return AUDIO_OGG;
            case AUDIO_MP4A_MIME:
                return AUDIO_MP4A;
            case AUDIO_WAV_MIME:
                return AUDIO_WAV;
            case AUDIO_WMA_MIME:
                return AUDIO_WMA;
            case VIDEO_AVI_MIME:
                return VIDEO_AVI;
            case VIDEO_DV_MIME:
                return VIDEO_DV;
            case VIDEO_MP4_MIME:
                return VIDEO_MP4;
            case VIDEO_MPEG_MPG_MIME:
                return VIDEO_MPEG+"*"+VIDEO_MPG;
            case VIDEO_MOV_MIME:
                return VIDEO_MOV;
            case VIDEO_WM_MIME:
                return VIDEO_WM;
            case VIDEO_FLV_MIME:
                return VIDEO_FLV;
            case VIDEO_MKV_MIME:
                return VIDEO_MKV;
            default:break;
        }
        return "";
    }

    /**
     * 判断媒体类型
     * @param s 后缀，文件名，本地地址，网路地址
     * @return 0 图片，1视频，2音频
     */
    public int mediaType(String s){
        String ext= URIUtil.calculationSuffix(s,"png");
        for(int i = 0; i< MIMETypeUtil.getInstance().getMusicMiMeType().length; i++){
            if(ext.equals(MIMETypeUtil.getInstance().getMusicMiMeType()[i][0])) {
                return 2;
            }
        }
        for(int i=0;i<MIMETypeUtil.getInstance().getVideoMiMeType().length;i++){
            if(ext.equals(MIMETypeUtil.getInstance().getVideoMiMeType()[i][0])) {
                return 1;
            }
        }

        for(int i=0;i<MIMETypeUtil.getInstance().getImgMiMeType().length;i++){
            MLog.d(MIMETypeUtil.getInstance().getImgMiMeType().length+"; "+MIMETypeUtil.getInstance().getMusicMiMeType()[i][0]);
            if(ext.equals(MIMETypeUtil.getInstance().getImgMiMeType()[i][0])) {
                return 0;
            }
        }
        return -1;
    }

    /**
     * 获取视频MIME_MapTable
     * @return
     */
    public String[][] getVideoMiMeType() {
        String[][] MIME_MapTable = {
                //{后缀名，MIME类型}
                {"3gp", "video/3gpp"},
                {"asf", "video/x-ms-asf"},
                {"avi", "video/x-msvideo"},
                {"m4u", "video/vnd.mpegurl"},
                {"m4v", "video/x-m4v"},
                {"mov", "video/quicktime"},
                {"mp4", "video/mp4"},
                {"mpe", "video/mpeg"},
                {"mpeg", "video/mpeg"},
                {"mpg", "video/mpeg"},
                {"mpg4", "video/mp4"},
                {"flv","video/x-flv"},
                {"mkv","video/x-matroska"},
                {"rm",""}
        };
        return MIME_MapTable;
    }

    /**
     * 获取图片MIME_MapTable
     * "Webp","baiBMP","PCX","EXIF", "FPX","SVG","CDR","PCD","DXF","UFO","EPS", "AI","HDRI","RAW","WMF","FLIC","EMF","ICO"
     * @return
     */
    public String[][] getImgMiMeType() {
        String[][] MIME_MapTable = {
                //{后缀名，MIME类型}
                {"bmp", "image/x-ms-bmp"},
                {"jpeg", "image/jpeg"},
                {"jpg", "image/jpeg"},
                {"gif", "image/gif"},
                {"png", "image/png"},
                {"tiff", "image/tiff"},
                {"tif", "image/tiff"},
                {"tga", "image/x-targa"},
                {"psd", "image/vnd.adobe.photoshop"},
                {"Webp","image/vnd.adobe.photoshop"},
                {"baiBMP","image/vnd.adobe.photoshop"},
                {"PCX","image/vnd.adobe.photoshop"},
                {"EXIF","image/vnd.adobe.photoshop"},
                {"FPX","image/vnd.adobe.photoshop"},
                {"SVG","image/vnd.adobe.photoshop"},
                {"CDR","image/vnd.adobe.photoshop"},
                {"PCD","image/vnd.adobe.photoshop"},
                {"DXF","image/vnd.adobe.photoshop"},
                {"UFO","image/vnd.adobe.photoshop"},
                {"EPS","image/vnd.adobe.photoshop"},
                {"AI","image/vnd.adobe.photoshop"},
                {"RAW","image/vnd.adobe.photoshop"},
                {"WMF","image/vnd.adobe.photoshop"},
                {"FLIC","image/vnd.adobe.photoshop"},
                {"EMF","image/vnd.adobe.photoshop"},
                {"ICO","image/vnd.adobe.photoshop"}
        };
        return MIME_MapTable;
    }


    /**
     * 获取音频的MIME_MapTable
     * @return
     */
    public String[][] getMusicMiMeType() {
        String[][] MIME_MapTable = {
                //{后缀名，MIME类型}
                {"m3u", "audio/x-mpegurl"},
                {"m4a", "audio/mp4a-latm"},
                {"m4b", "audio/mp4a-latm"},
                {"m4p", "audio/mp4a-latm"},
                {"mp2", "audio/x-mpeg"},
                {"mp3", "audio/x-mpeg"},
                {"mpga", "audio/mpeg"},
                {"ogg", "audio/ogg"},
                {"rmvb", "audio/x-pn-realaudio"},
                {"wav", "audio/x-wav"},
                {"wma", "audio/x-ms-wma"},
                {"wmv", "audio/x-ms-wmv"}

        };
        return MIME_MapTable;
    }
}
