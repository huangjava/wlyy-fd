package com.yihu.wlyy.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

public class CommonUtil {

	/**
	 * 获取图片全路径
	 * 
	 * @return
	 */
	public static String getPhoneUrl(String url) {
		if (StringUtils.isEmpty(url)) {
			return "";
		} else {
			if (url.indexOf("http") > -1) {
				return url;
			} else {
				return SystemConf.getInstance().getServerUrl() + url;
			}
		}
	}

	public static String getMobileEncode(String mobile) {
		if (StringUtils.isNotEmpty(mobile) && mobile.length() == 11) {
			return mobile.substring(0, 4) + "****" + mobile.substring(8, 11);
		}
		return mobile;
	}

	public static String getIdcardEncode(String idcard) {
		if (idcard != null) {
			if (idcard.length() == 18) {
				return idcard.substring(0, 9) + "*******" + idcard.substring(16, 18);
			} else if (idcard.length() == 15) {
				return idcard.substring(0, 8) + "***" + idcard.substring(11, 15);
			}
		}
		return idcard;
	}

	/** 
	 * 对象转数组 
	 * @param obj 
	 * @return 
	 */
	public static byte[] toByteArray(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
			oos.close();
			bos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return bytes;
	}

	/** 
	 * 数组转对象 
	 * @param bytes 
	 * @return 
	 */
	public static Object toObject(byte[] bytes) {
		Object obj = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			obj = ois.readObject();
			ois.close();
			bis.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return obj;
	}

	public static final InputStream byte2Input(byte[] buf) {
		return new ByteArrayInputStream(buf);
	}

	public static final byte[] input2byte(InputStream inStream) throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = inStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}

	/**
	 * 返回：张三为张*
	 * @param name 姓名
	 * @return
	 */
	public static String getEncryptName(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		if (name.length() == 1) {
			return name;
		}
		// 获取姓
		String temp = name.substring(0, 1);
		for (int i = 1; i < name.length(); i++) {
			temp += "*";
		}
		return temp;
	}

	/**
	 * 拷贝临时图片到存储目录
	 * @param images
	 * @return
	 * @throws IOException
	 */
	public static String copyTempImage(String images) throws IOException {
		// 文件保存的临时路径
		String tempPath = SystemConf.getInstance().getTempPath() + File.separator;
		// 图片保存路径
		String imagePath = SystemConf.getInstance().getImagePath() + File.separator;
		String[] tempImages = images.split(",");
		String temp = "";
		for (String image : tempImages) {
			File file = new File(tempPath + image);
			File smallFile = new File(tempPath + image + "_small");
			if (file.exists() && smallFile.exists()) {
				// 原图拷贝路径
				File targetFile = new File(imagePath + image);
				// 缩略图拷贝路径
				File targetSmallFile = new File(imagePath + image + "_small");
				// 拷贝原图
				FileUtils.copyFile(file, targetFile);
				// 拷贝缩略图
				FileUtils.copyFile(smallFile, targetSmallFile);
				// 删除临时文件
				FileUtils.forceDelete(file);
				FileUtils.forceDelete(smallFile);
				if (temp.length() == 0) {
					temp = SystemConf.getInstance().getImageServer() + image;
				} else {
					temp += "," + SystemConf.getInstance().getImageServer() + image;
				}
			}
		}
		return temp;
	}

	/**
	 * 拷贝临时语音文件到存储目录
	 * @param images
	 * @return
	 * @throws IOException
	 */
	public static String copyTempVoice(String voices) throws IOException {
		// 文件保存的临时路径
		String tempPath = SystemConf.getInstance().getTempPath() + File.separator;
		// 语音保存路径
		String voicePath = SystemConf.getInstance().getVoicePath() + File.separator;
		String[] tempVoices = voices.split(",");
		String temp = "";
		for (String voice : tempVoices) {
			File file = new File(tempPath + voice);
			if (file.exists()) {
				// 目标文件
				File targetFile = new File(voicePath + voice);
				// 拷贝到指定路径
				FileUtils.copyFile(file, targetFile);
				// 删除临时文件
				FileUtils.forceDelete(file);
				if (temp.length() == 0) {
					temp = SystemConf.getInstance().getVoiceServer() + voice;
				} else {
					temp += "," + SystemConf.getInstance().getVoiceServer() + voice;
				}
			}
		}
		return temp;
	}

	/**
	 * 校验健康指标是否正常
	 * @param curValue 当前值
	 * @param standardMax 最大标准值
	 * @param standardMin 最小标准值
	 * @return 0正常，1高，-1低
	 */
	public static double checkHealthIndex(double curValue, double standardMax, double standardMin) {
		if (curValue <= 0) {
			return 0;
		}
		if (standardMax > 0 && curValue > standardMax) {
			return curValue;
		}
		// 低于标准值，暂不推
		// if (standardMin > 0 && curValue < standardMin) {
		// return -curValue;
		// }
		return 0;
	}
}
