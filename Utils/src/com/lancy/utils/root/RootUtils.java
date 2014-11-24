package com.lancy.utils.root;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import android.util.Log;

public class RootUtils {

	private static Process process;
	private String cmd_install = "pm install -r +安装apk包路径";//静默安装命令
	private String cmd_uninstall = "pm uninstall +程序包名";//静默卸载命令
	
	/**
	 * root设备静默卸载
	 * @param packageName
	 * @return
	 */
	public static boolean slientUninstall(String packageName){
		boolean result = false;
		Process process = null;
		OutputStream out = null;
		
		try {
			process = Runtime.getRuntime().exec("su");
			out = process.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(out);
			
			dataOutputStream
					.writeBytes("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm uninstall "
							+ packageName);
			// 提交命令
			dataOutputStream.flush();
			// 关闭流操作
			dataOutputStream.close();
			out.close();
			int value = process.waitFor();

			// 代表成功
			if (value == 0) {
				result = true;
			} else if (value == 1) { // 失败
				result = false;
			} else { // 未知情况
				result = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		
		
		return result;
	}
	
	
	/**
	 * root设备静默安装
	 * 
	 * @param file
	 * @return
	 */
	public static boolean slientInstall(File file) {
		boolean result = false;
		Process process = null;
		OutputStream out = null;
		try {
			process = Runtime.getRuntime().exec("su");
			out = process.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(out);
			dataOutputStream.writeBytes("chmod 777 " + file.getPath() + "\n");
			dataOutputStream
					.writeBytes("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r "
							+ file.getPath());
			// 提交命令
			dataOutputStream.flush();
			// 关闭流操作
			dataOutputStream.close();
			out.close();
			int value = process.waitFor();

			// 代表成功
			if (value == 0) {
				result = true;
			} else if (value == 1) { // 失败
				result = false;
			} else { // 未知情况
				result = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 关闭指定应用
	 */
	public static void kill(String packageName) {
		initProcess();
		killProcess(packageName);
		close();
	}

	/**
	 * 初始化进程
	 */
	private static void initProcess() {
		if (process == null)
			try {
				process = Runtime.getRuntime().exec("su");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * 结束进程
	 */
	private static void killProcess(String packageName) {
		DataOutputStream out = new DataOutputStream(process.getOutputStream());
//		String cmd = "am force-stop " + packageName + " \n";
		String cmd = "am kill all \n" ;
		try {
			out.write(cmd.getBytes());
			out.writeBytes("exit\n");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭输出流
	 */
	private static void close() {
		if (process != null)
			try {
				process.getOutputStream().close();
				process = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public static synchronized boolean getRootAhth()
    {
        Process process = null;
        DataOutputStream os = null;
        try
        {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("exit\n");
            os.flush();
            int exitValue = process.waitFor();
            if (exitValue == 0)
            {
                return true;
            } else
            {
                return false;
            }
        } catch (Exception e)
        {
            Log.d("*** DEBUG ***", "Unexpected error - Here is what I know: "
                    + e.getMessage());
            return false;
        } finally
        {
            try
            {
                if (os != null)
                {
                    os.close();
                }
                process.destroy();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
	
	
	
	// 判断是否具有ROOT权限
	public static boolean is_root() {

		boolean res = false;
		
	

		try {
			if ((!new File("/system/bin/su").exists())
					&& (!new File("/system/xbin/su").exists())) {
				res = false;
			} else {
				res = true;
			}
			;
		} catch (Exception e) {

		}
		return res;
	}

	public static void getroot() {
		Process process;
		try {
			process = Runtime.getRuntime().exec("su");
			DataOutputStream os = new DataOutputStream(
					process.getOutputStream());
			// 这里执行命令
			os.writeBytes("exit\n");
			os.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
