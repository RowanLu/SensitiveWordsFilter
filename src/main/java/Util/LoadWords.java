package Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Rowan
 * @Date: 2018/8/17 22:43
 * @Description: 从文件中获取Words
 */
public class LoadWords {

	/**
	 * @Description: 从文件读取Words
	 * @Param: 文件路径
	 * @Return: List<String>
	 */
	public static List<String> readWordFromFile(String path) {
		List<String> words;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(LoadWords.class.getClassLoader().getResourceAsStream(path)))) {
			words = new ArrayList<>(1200);
			for (String buf; (buf = br.readLine()) != null; ) {
				if (buf.trim().equals(""))
					continue;
				words.add(buf);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return words;
	}
}
