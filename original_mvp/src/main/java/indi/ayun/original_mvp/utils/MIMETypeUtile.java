package indi.ayun.original_mvp.utils;

public class MIMETypeUtile {
    //文档文件类型的
    private static String DOCUMENT_AI="ai";        //application/postscript
    private static String DOCUMENT_EPS="eps";        //application/postscript
    private static String DOCUMENT_EXE="exe";        //application/octet-stream
    private static String DOCUMENT_DOC="doc";        //application/vnd.ms-word
    private static String DOCUMENT_XLS="xls";        //application/vnd.ms-excel
    private static String DOCUMENT_PPT="ppt";        //application/vnd.ms-powerpoint
    private static String DOCUMENT_PPS="pps";        //application/vnd.ms-powerpoint
    private static String DOCUMENT_PDF="pdf";        //application/pdf
    private static String DOCUMENT_XML="xml";       //application/xml
    private static String DOCUMENT_ODT="odt";        //application/vnd.oasis.opendocument.text
    private static String DOCUMENT_SWF="swf";        //application/x-shockwave-flash

    //压缩文件类型的
    private static String COMPRESS_GZ="gz";        //application/x-gzip
    private static String COMPRESS_TGZ="tgz";        //application/x-gzip
    private static String COMPRESS_BZ="bz";        //application/x-bzip2
    private static String COMPRESS_BZ2="bz2";        //application/x-bzip2
    private static String COMPRESS_TBZ="tbz";        //application/x-bzip2
    private static String COMPRESS_ZIP="zip";       //application/zip
    private static String COMPRESS_RAR="rar";        //application/x-rar
    private static String COMPRESS_TAR="tar";        //application/x-tar
    private static String COMPRESS_7Z="7z";        //application/x-7z-compressed

    //文字类型
    private static String WORDS_TXT="txt";        //text/plain
    private static String WORDS_PHP="php";        //text/x-php
    private static String WORDS_HTML="html";        //text/html
    private static String WORDS_HTM="htm";       //text/html
    private static String WORDS_JS="js";        //text/javascript
    private static String WORDS_CSS="css";        //text/css
    private static String WORDS_RTF="rtf";        //text/rtf
    private static String WORDS_RTFD="rtfd";        //text/rtfd
    private static String WORDS_PY="py";        //text/x-python
    private static String WORDS_JAVA="java";        //text/x-java-source
    private static String WORDS_RB="rb";       //text/x-ruby
    private static String WORDS_SH="sh";        //text/x-shellscript
    private static String WORDS_PL="pl";        //text/x-perl
    private static String WORDS_SQL="sql";        //text/x-sql

    //图片类型的
    private static String PICTURE_BMP="bmp";        //image/x-ms-bmp
    private static String PICTURE_JPG="jpg";        //image/jpeg
    private static String PICTURE_JPEG="jpeg";        //image/jpeg
    private static String PICTURE_GIF="gif";        //image/gif
    private static String PICTURE_PNG="png";        //image/png
    private static String PICTURE_TIF="tif";       //image/tiff
    private static String PICTURE_TIFF="tiff";        //image/tiff
    private static String PICTURE_TGA="tga";        //image/x-targa
    private static String PICTURE_PSD="psd";        //image/vnd.adobe.photoshop

    //音频文件类型的
    private static String AUDIO_MP3="mp3";        //audio/mpeg
    private static String AUDIO_MID="mid";        //audio/midi
    private static String AUDIO_OGG="ogg";        //audio/ogg
    private static String AUDIO_MP4A="mp4a";        //audio/mp4
    private static String AUDIO_WAV="wav";        //audio/wav
    private static String AUDIO_WMA="wma";        //audio/x-ms-wma

    //视频文件类型的
    private static String VIDEO_AVI="avi";        //video/x-msvideo
    private static String VIDEO_DV="dv";        //video/x-dv
    private static String VIDEO_MP4="mp4";        //video/mp4
    private static String VIDEO_MPEG="mpeg";        //video/mpeg
    private static String VIDEO_MPG="mpg";        //video/mpeg
    private static String VIDEO_MOV="mov";        //video/quicktime
    private static String VIDEO_WM="wm";        //video/x-ms-wmv
    private static String VIDEO_FLV="flv";        //video/x-flv
    private static String VIDEO_MKV="mkv";        //video/x-matroska

    //文档文件类型的
    private final static String DOCUMENT_AI_EPS_MIME="application/postscript";
    private final static String DOCUMENT_EXE_MIME="application/octet-stream";
    private final static String DOCUMENT_DOC_MIME="application/vnd.ms-word";
    private final static String DOCUMENT_XLS_MIME="application/vnd.ms-excel";
    private final static String DOCUMENT_PPT_PPS_MIME="application/vnd.ms-powerpoint";
    private final static String DOCUMENT_PDF_MIME="application/pdf";
    private final static String DOCUMENT_XML_MIME="application/xml";
    private final static String DOCUMENT_ODT_MIME="application/vnd.oasis.opendocument.text";
    private final static String DOCUMENT_SWF_MIME="application/x-shockwave-flash";

    //压缩文件类型的
    private final static String COMPRESS_GZ_MIME="gzapplication/x-gzip";
    private final static String COMPRESS_TGZ_MIME="application/x-gzip";
    private final static String COMPRESS_BZ_BZ2_TBZ_MIME="application/x-bzip2";
    private final static String COMPRESS_ZIP_MIME="application/zip";
    private final static String COMPRESS_RAR_MIME="application/x-rar";
    private final static String COMPRESS_TAR_MIME="application/x-tar";
    private final static String COMPRESS_7Z_MIME="application/x-7z-compressed";

    //文字类型
    private final static String WORDS_TXT_MIME="text/plain";
    private final static String WORDS_PHP_MIME="text/x-php";
    private final static String WORDS_HTML_HTM_MIME="text/html";
    private final static String WORDS_JS_MIME="text/javascript";
    private final static String WORDS_CSS_MIME="text/css";
    private final static String WORDS_RTF_MIME="text/rtf";
    private final static String WORDS_RTFD_MIME="text/rtfd";
    private final static String WORDS_PY_MIME="text/x-python";
    private final static String WORDS_JAVA_MIME="text/x-java-source";
    private final static String WORDS_RB_MIME="text/x-ruby";
    private final static String WORDS_SH_MIME="text/x-shellscript";
    private final static String WORDS_PL_MIME="text/x-perl";
    private final static String WORDS_SQL_MIME="text/x-sql";

    //图片类型的
    private final static String PICTURE_BMP_MIME="image/x-ms-bmp";
    private final static String PICTURE_JPEG_JPG_MIME="image/jpeg";
    private final static String PICTURE_GIF_MIME="image/gif";
    private final static String PICTURE_PNG_MIME="image/png";
    private final static String PICTURE_TIF_TIFF_MIME="image/tiff";
    private final static String PICTURE_TGA_MIME="image/x-targa";
    private final static String PICTURE_PSD_MIME="image/vnd.adobe.photoshop";

    //音频文件类型的
    private final static String AUDIO_MP3_MIME="audio/mpeg";
    private final static String AUDIO_MID_MIME="audio/midi";
    private final static String AUDIO_OGG_MIME="audio/ogg";
    private final static String AUDIO_MP4A_MIME="audio/mp4";
    private final static String AUDIO_WAV_MIME="audio/wav";
    private final static String AUDIO_WMA_MIME="audio/x-ms-wma";

    //视频文件类型的
    private final static String VIDEO_AVI_MIME="video/x-msvideo";
    private final static String VIDEO_DV_MIME="video/x-dv";
    private final static String VIDEO_MP4_MIME="video/mp4";
    private final static String VIDEO_MPEG_MPG_MIME="video/mpeg";
    private final static String VIDEO_MOV_MIME="video/quicktime";
    private final static String VIDEO_WM_MIME="video/x-ms-wmv";
    private final static String VIDEO_FLV_MIME="video/x-flv";
    private final static String VIDEO_MKV_MIME="video/x-matroska";
    public static String getType(String mime){
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
}
