package indi.ayun.mingwork_all.utils.java;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import indi.ayun.mingwork_all.config.CommonResult;

/**
 * 主要功能：Shell批处理命令管理类
 */
public class ShellUtils {
	public static final String COMMAND_SU = "su";
	public static final String COMMAND_SH = "sh";
	public static final String COMMAND_EXIT = "exit\n";
	public static final String COMMAND_LINE_END = "\n";

	/**
	 * 检查是否有root权限
	 * @return
	 */
	public static boolean checkRootPermission() {
		return execCommand(new String[]{"echo root"}, true, false).result == 0;
	}




	/**
	 * execute shell commands
	 * 
	 * @param commands
	 *            command array：字符串时可以写成new String[] { command }
	 *            command array：list时可以写成commands == null ? null : commands.toArray(new String[] {}
	 * @param isRoot
	 *            whether need to run with root
	 * @param isNeedResultMsg
	 *            whether need result msg
	 * @return <ul>
	 *         <li>if isNeedResultMsg is false, {@link CommonResult#}
	 *         is null and {@link CommonResult#} is null.</li>
	 *         <li>if {@link CommonResult#} is -1, there maybe some
	 *         excepiton.</li>
	 *         </ul>
	 */
	public static CommonResult execCommand(String[] commands, boolean isRoot, boolean isNeedResultMsg) {
		int result=-1;
		if (commands == null || commands.length == 0) {
			return new CommonResult(0, "","无效命令");
		}
		Process process = null;
		BufferedReader successResult = null;
		BufferedReader errorResult = null;
		StringBuilder successMsg = null;
		StringBuilder errorMsg = null;
		DataOutputStream os = null;
		try {
			process = Runtime.getRuntime().exec(
					isRoot ? COMMAND_SU : COMMAND_SH);
			os = new DataOutputStream(process.getOutputStream());
			for (String command : commands) {
				if (command == null) {
					continue;
				}
				// donnot use os.writeBytes(commmand), avoid chinese charset
				// error
				os.write(command.getBytes());
				os.writeBytes(COMMAND_LINE_END);
				os.flush();
			}
			os.writeBytes(COMMAND_EXIT);
			os.flush();
			result = process.waitFor();
			// get command result
			if (isNeedResultMsg) {
				successMsg = new StringBuilder();
				errorMsg = new StringBuilder();
				successResult = new BufferedReader(new InputStreamReader(
						process.getInputStream()));
				errorResult = new BufferedReader(new InputStreamReader(
						process.getErrorStream()));
				String s;
				while ((s = successResult.readLine()) != null) {
					successMsg.append(s);
				}
				while ((s = errorResult.readLine()) != null) {
					errorMsg.append(s);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (successResult != null) {
					successResult.close();
				}
				if (errorResult != null) {
					errorResult.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (process != null) {
				process.destroy();
			}
		}
		return new CommonResult(result, successMsg == null ? null
				: successMsg.toString(), errorMsg == null ? null
				: errorMsg.toString());
	}


}