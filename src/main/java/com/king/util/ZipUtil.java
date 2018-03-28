package com.king.util;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * ZIP 压缩工具类
 * 
 * @author pc_tang
 *
 */
public class ZipUtil {

	/**
	 * 构建压缩文件存放路径,如果不存在将会创建 传入的可能是文件名或者目录,也可能不传,此方法用以转换最终压缩文件的存放路径
	 * 
	 * @param srcFile
	 *            源文件
	 * @param destParam
	 *            压缩目标路径
	 * @return 正确的压缩文件存放路径
	 */
	private static String buildDestinationZipFilePath(File srcFile, String destParam) {
		if (StringUtils.isEmpty(destParam)) {
			if (srcFile.isDirectory()) {
				destParam = srcFile.getParent() + File.separator + srcFile.getName() + ".zip";
			} else {
				String fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
				destParam = srcFile.getParent() + File.separator + fileName + ".zip";
			}
		} else {
			createDestDirectoryIfNecessary(destParam); // 在指定路径不存在的情况下将其创建出来
			if (destParam.endsWith(File.separator)) {
				String fileName = "";
				if (srcFile.isDirectory()) {
					fileName = srcFile.getName();
				} else {
					fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
				}
				destParam += fileName + ".zip";
			}
		}
		return destParam;
	}

	/**
	 * 文件压缩
	 * 
	 * @param src
	 *            文件路径
	 * @param dest
	 *            压缩文件路径
	 * @param isCreateDir
	 *            是否在压缩文件里创建目录,仅在压缩文件为目录时有效. 如果为false,将直接压缩目录下文件到压缩文件.
	 * @param passwd
	 *            压缩文件加密
	 * @return
	 */
	public static String zip(String[] src, String dest, boolean isCreateDir, String passwd) {
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // 压缩方式
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); // 压缩级别
		if (!StringUtils.isEmpty(passwd)) {
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD); // 加密方式
			parameters.setPassword(passwd.toCharArray());
		}
		try {
			ZipFile zipFile = new ZipFile(dest);
			int num = src.length;
			for (int i = 0; i < num; i++) {
				File srcFile = new File(src[i]);
				dest = buildDestinationZipFilePath(srcFile, dest);
				if (srcFile.isDirectory()) {
					// 如果不创建目录的话,将直接把给定目录下的文件压缩到压缩文件,即没有目录结构
					if (!isCreateDir) {
						File[] subFiles = srcFile.listFiles();
						ArrayList<File> temp = new ArrayList<File>();
						Collections.addAll(temp, subFiles);
						zipFile.addFiles(temp, parameters);
						return dest;
					}
					zipFile.addFolder(srcFile, parameters);
				} else {
					zipFile.addFile(srcFile, parameters);
				}
			}
			return dest;
		} catch (ZipException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 在必要的情况下创建压缩文件存放目录,比如指定的存放路径并没有被创建
	 * 
	 * @param destParam
	 *            指定的存放路径,有可能该路径并没有被创建
	 */
	private static void createDestDirectoryIfNecessary(String destParam) {
		File destDir = null;
		if (destParam.endsWith(File.separator)) {
			destDir = new File(destParam);
		} else {
			destDir = new File(destParam.substring(0, destParam.lastIndexOf(File.separator)));
		}
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
	}

	public static void main(String[] args) {
		String[] files = { "C:\\1\\log.log", "C:\\1\\1.txt", "C:\\1\\2.txt", "C:\\1\\3.txt" };
		zip(files, "c:\\1\\log.zip", true, "123");
	}
}
