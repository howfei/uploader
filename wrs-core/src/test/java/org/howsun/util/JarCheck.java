/**
 * 
 */
package org.howsun.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * 说明:Jar包检测工具
 * 1、接受用户给予路径
 * 2、从该路径下递归出所有jar包
 * 3、用JarFile对象读取MANIFEST.MF
 * 4、如果读取成功表示为[正常]包，反之为损坏
 * 5、最后打印结果
 * 
 * @author howfei[13720056627@163.com]
 * 
 * 2017年2月14日 上午8:21:02
 */
public class JarCheck {

	/**
	 * @param args
	 * void
	 */
	public static void main(String[] args) {
		String maven_repository_path = getString("请输入Jar包目录：");
		File maven_repository_file = new File(maven_repository_path);
		if(!maven_repository_file.exists()){
			System.out.println("路径不存在...");
			return;
		}
		
		System.out.println(String.format("开始在“%s”目录中检测...", maven_repository_path));
		
		LinkedHashSet<File> files = new LinkedHashSet<File>();
		readingJars(maven_repository_file, files);
		
		int size = files.size();
		System.out.println(String.format("已发现[%s]个jar包，开始检测...", size));
		
		int index = 0;
		for(File file : files){
			index++;
			JarFile jarFile = null;
			try {
				jarFile = new JarFile(file);
				Manifest manifest = jarFile.getManifest();
				if(manifest != null){
					System.out.println(String.format("%s/%s\t[正常]\t%s", index, size, file.getAbsolutePath()));
				}
			} catch (Exception e) {
				System.out.println(String.format("%s/%s\t[损坏]\t 原因：%s\t%s", index, size, e.getMessage(), file.getAbsolutePath()));
			} finally{
				if(jarFile != null){
					try {
						jarFile.close();
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			}
		}
	}

	/**
	 * 读取Jar包文件，将结果存入files
	 * @param file
	 * @param files
	 * void
	 */
	public static void readingJars(File file, LinkedHashSet<File> files){
		if(file.isDirectory()){
			for(File subFile : file.listFiles()){
				readingJars(subFile, files);
			}
		}
		
		if(file.getName().toLowerCase().endsWith(".jar")){
			files.add(file);
		}
	}
	
	/**
	 * 从输入流获取用户输入
	 * @param tip
	 * @return
	 * String
	 */
	public static String getString(String tip){
		System.out.println(tip);
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		String inputContent = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			inputContent = bufferedReader.readLine();
		} catch (Exception e) {
			System.out.println("出错，请重试！\n log:" + e.getMessage());
		} finally {
			if(inputStreamReader != null){
				try {
					inputStreamReader.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			if(bufferedReader != null){
				try {
					bufferedReader.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		
		return inputContent;
	}
}
