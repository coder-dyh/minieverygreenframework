package org.framework.beans;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * class扫描工具
 * Created by wangl on 2017/7/6.
 */
public class ScanUtils {

	private static final List<String> classNames = new ArrayList<>();
	private static String packagePath;


	public static List<String> scan() {

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		packagePath = loader.getResource("").getPath();
		try {
			packagePath=URLDecoder.decode(packagePath,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			Enumeration<URL> urls = loader.getResources("");
			while(urls.hasMoreElements()){
				URL url= urls.nextElement();
				//返回URL的协议类型（比如是一个文件，一个文件夹），即文件的后缀名
				if("file".equals(url.getProtocol())){
					scanFromDir(URLDecoder.decode(url.getPath(), "utf-8"));
				}
				if("jar".equals(url.getProtocol())){
					//打开一个URL连接，并运行客户端访问资源
					JarURLConnection connection = (JarURLConnection)url.openConnection();
					scanFromJar(connection.getJarFile());
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Resolve path error.", e);
		}

		return classNames;
	}

	private static void scanFromDir(String filePath) throws UnsupportedEncodingException{
		filePath = URLDecoder.decode(filePath, "utf-8");
		File[] files = new File(filePath).listFiles();
		for (File childFile : files) {
			if (childFile.isDirectory()) {
				scanFromDir(childFile.getPath());
			} else {
				String fileName = childFile.getName();
				if (fileName.endsWith(".class")) {
					String packageName=childFile.getPath().substring(packagePath.length());
					packageName=packageName.replace(".class","").replace("/",".");
					classNames.add(packageName);
				}
			}
		}
	}

	/**
	 * 扫描jar文件
	 * @param jarFile
	 */
	private static void scanFromJar(JarFile jarFile) {
		//为指定的JAR文件条目名称创建一个新的 JarEntry ,就类似于创建一个File对象
		Enumeration<JarEntry> files = jarFile.entries();
		while (files.hasMoreElements()) {
			JarEntry entry = files.nextElement();
			if (entry.getName().endsWith(".class")){
				String className = entry.getName().replace("/", ".").replace(".class", "");
				classNames.add(className);
			}
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException{

		List<String> list =scan();
		System.out.println(list.size());

	}

}
