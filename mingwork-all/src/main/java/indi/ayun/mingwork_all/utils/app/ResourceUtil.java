package indi.ayun.mingwork_all.utils.app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import indi.ayun.mingwork_all.MingWork_All;
import indi.ayun.mingwork_all.base.UtilBase;
import indi.ayun.mingwork_all.mlog.MLog;
import indi.ayun.mingwork_all.utils.verification.IsNothing;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 主要功能：该工具类用于获取本地指定资源信息
 */
public class ResourceUtil extends UtilBase {

	public static final String LAYOUT="layout";
	public static final String DRAWABLE="drawable";
	public static final String MIPMAP="mipmap";
	public static final String MENU="menu";
	public static final String RAW="raw";
	public static final String ANIM="anim";
	public static final String STRING="string";
	public static final String STYLE="style";
	public static final String STYLEABLE="styleable";
	public static final String INTEGER="integer";
	public static final String ID="ids";
	public static final String DIMEN="dimen";
	public static final String COLOR="color";
	public static final String BOOL="bool";
	public static final String ATTR="attr";

	public static final String ASSETS="assets";

	public static List<String> readResList(String type,Object value){
		List<String> fileContent = new ArrayList<String>();
		switch (type){
			case RAW://根据本地Raw目录下资源标识，获取List集合信息
				int resId=Integer.parseInt(value.toString());
				BufferedReader reader = null;
				try {
					InputStreamReader in = new InputStreamReader(MingWork_All.getContext().getResources().openRawResource(resId));
					reader = new BufferedReader(in);
					String line = null;
					while ((line = reader.readLine()) != null) {
						fileContent.add(line);
					}
					reader.close();
					return fileContent;
				} catch (IOException e) {
					e.printStackTrace();
					MLog.e("根据本地Raw目录下资源标识，获取List集合信息失败！" + e.getMessage());
					return null;
				}

			case ASSETS://根据本地Assets目录下资源名称，获取List集合信息
				String fileName=value.toString();
				if ( !IsNothing.onAnything(fileName)) {
					return null;
				}
				try {
					InputStreamReader in = new InputStreamReader(MingWork_All.getContext().getResources().getAssets().open(fileName));
					BufferedReader br = new BufferedReader(in);
					String line;
					while ((line = br.readLine()) != null) {
						fileContent.add(line);
					}
					br.close();
					return fileContent;
				} catch (IOException e) {
					e.printStackTrace();
					MLog.e("根据本地Assets目录下资源名称，获取List集合信息失败！" + e.getMessage());
					return null;
				}
			default:
				return null;
		}
	}

	/**
	 *
	 * @param type 资源类型
	 * @param value 对应的key
	 * @return
	 */
	public static byte[] readResBytes(String type,Object value){
		InputStream is = null;
		byte[] buffer = null;
		switch (type){
			case RAW://根据本地Raw目录下资源标识，获取List集合信息
				int rawId=Integer.parseInt(value.toString());
				try {
					is = MingWork_All.getContext().getResources().openRawResource(rawId);
					int size = is.available();
					buffer = new byte[size];
					is.read(buffer);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (is != null) {
						try {
							is.close();
							is = null;
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				}
				return buffer;

			case ASSETS://根据本地Assets目录下资源名称，获取List集合信息
				String fileName=value.toString();
				try {
					is = MingWork_All.getContext().getAssets().open(fileName);
					int size = is.available();
					buffer = new byte[size];
					is.read(buffer);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (is != null) {
						try {
							is.close();
							is = null;
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				}
				return buffer;
			default:
				return null;
		}
	}

	/**
	 * 根据本地目录下资源，获取String数据信息
	 * @param type
	 * @param value
	 * @return
	 */
	public static String readResString(String type,Object value){

		switch (type){
			case RAW://根据本地Raw目录下资源标识，获取List集合信息
				int resId=Integer.parseInt(value.toString());
				try {
					StringBuilder s = new StringBuilder();
					InputStreamReader in = new InputStreamReader(MingWork_All.getContext().getResources().openRawResource(resId));
					BufferedReader br = new BufferedReader(in);
					String line;
					while ((line = br.readLine()) != null) {
						s.append(line);
					}
					return s.toString();
				} catch (IOException e) {
					e.printStackTrace();
					MLog.e("根据本地Raw目录下资源标识，获取String数据信息失败！" + e.getMessage());
					return null;
				}
			case ASSETS://根据本地Assets目录下资源名称，获取List集合信息
				String fileName=value.toString();
				if (!IsNothing.onAnything(fileName)) {
					return null;
				}
				try {
					StringBuilder s = new StringBuilder("");
					InputStreamReader in = new InputStreamReader(MingWork_All.getContext().getResources().getAssets().open(fileName));
					BufferedReader br = new BufferedReader(in);
					String line;
					while ((line = br.readLine()) != null) {
						s.append(line);
					}
					return s.toString();
				} catch (IOException e) {
					e.printStackTrace();
					MLog.e("根据本地Assets目录下资源名称，获取String数据信息失败！" + e.getMessage());
					return null;
				}
			default:
				return null;
		}
	}

	/**
	 * 根据资源名获得资源id
	 * @param context 上下文
	 * @param name 资源名 如ic_img.png
	 * @param type 资源类型 如drawable
	 * @return 资源id，找不到返回0
	 */
	public static int getResourceId(Context context,String name,String type){
		Resources resources=null;
		PackageManager pm=context.getPackageManager();
		try {
			resources=context.getResources();
			return resources.getIdentifier(name, type, context.getPackageName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}


	/**
	 * 获得字符串
	 * @param strId 字符串id
	 * @return 字符串
	 */
	public static String getStringById(int strId){
		return MingWork_All.getContext().getResources().getString(strId);
	}

	/**
	 * 获得颜色
	 * @param colorId 颜色id
	 * @return 颜色
	 */
	public static int getColorById(int colorId){
		return MingWork_All.getContext().getResources().getColor(colorId);
	}

	/**
	 * 获得Drawable
	 * @param drawableId Drawable的id
	 * @return Drawable
	 */
	public static Drawable getDrawableById(int drawableId){
		return MingWork_All.getContext().getResources().getDrawable(drawableId);
	}



}