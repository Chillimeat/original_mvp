package indi.ayun.original_mvp.office;

import android.content.Context;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import indi.ayun.original_mvp.OriginalMVP;
import indi.ayun.original_mvp.mlog.MLog;
import jxl.Sheet;
import jxl.Workbook;

/**
 * @Description TODO
 * @Author Created by ayun on 2021/4/1 18:33.
 */
public class ExcelUtil {

    /**
     * 打包会出现报错，Workbook book = Workbook.getWorkbook(is);报错NoClassDefFoundError，关掉混淆就不会报错，开启混淆并排除jxl包得混淆仍然会报错。
     * @param context
     */
    public static void setExcelAlarmExcel(Context context) {
        try {
            InputStream is = context.getAssets().open("excel/user_clock.xls");//jxl包只能使用excel03版本，也就是。xls文件
            Workbook book = Workbook.getWorkbook(is);
            book.getNumberOfSheets();
            // 获得第一个工作表对象
            Sheet sheet = book.getSheet(0);
            int Rows = sheet.getRows();

            //AlarmsEntity alarmEntity = new AlarmsEntity();
            for (int i = 2; i < Rows; ++i) {
                //第二行开始是数据
//                alarmEntity.setCode((sheet.getCell(1, i)).getContents());
//                alarmEntity.setTime((sheet.getCell(2, i)).getContents());
//                alarmEntity.setDate((sheet.getCell(3, i)).getContents());
//                alarmEntity.setFrequency(Integer.parseInt((sheet.getCell(4, i)).getContents()));
//                alarmEntity.setIsOpen(Integer.parseInt((sheet.getCell(5, i)).getContents()));
//                alarmEntity.setName((sheet.getCell(6, i)).getContents());
//                alarmEntity.setVideo(MediaUtil.injectionDefaultVideo((sheet.getCell(7, i)).getContents(), Integer.parseInt((sheet.getCell(21, i)).getContents())));
//                alarmEntity.setMusic(MediaUtil.injectionDefaultAudio((sheet.getCell(8, i)).getContents(), Integer.parseInt((sheet.getCell(21, i)).getContents())));
//                alarmEntity.setVolume((sheet.getCell(9, i)).getContents());
//                alarmEntity.setPlayType(Integer.parseInt((sheet.getCell(10, i)).getContents()));
//                alarmEntity.setMediaType(Integer.parseInt((sheet.getCell(11, i)).getContents()));
//                alarmEntity.setIsShock(Integer.parseInt((sheet.getCell(12, i)).getContents()));
//                alarmEntity.setNoteTitle((sheet.getCell(13, i)).getContents());
//                alarmEntity.setNoteCon((sheet.getCell(14, i)).getContents());
//                alarmEntity.setCirculate(Integer.parseInt((sheet.getCell(15, i)).getContents()));
//                alarmEntity.setSleep(Integer.parseInt((sheet.getCell(16, i)).getContents()));
//                alarmEntity.setIsOpenAll(Integer.parseInt((sheet.getCell(17, i)).getContents()));
//                alarmEntity.setMediaName((sheet.getCell(18, i)).getContents());
//                alarmEntity.setIsCloud(Integer.parseInt((sheet.getCell(19, i)).getContents()));
//                alarmEntity.setIsIgnore(Integer.parseInt((sheet.getCell(20, i)).getContents()));
//                alarmEntity.setType(Integer.parseInt((sheet.getCell(21, i)).getContents()));
//                alarmEntity.setIsShow(Integer.parseInt((sheet.getCell(22, i)).getContents()));
//                alarmEntity.setScene(Integer.parseInt((sheet.getCell(23, i)).getContents()));
            }
            book.close();
        } catch (Exception e) {
            MLog.e(e);
        }
    }
}
