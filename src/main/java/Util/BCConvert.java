package Util;

/**
 * @Auther: Rowan
 * @Date: 2018/8/17 21:09
 * @Description: 全角与半角转换
 */
public class BCConvert {
	/**
	 * ASCII表中可见字符从!开始，偏移位值为33(Decimal)
	 */
	static final char DBC_CHAR_START = 33; // 半角!

	/**
	 * ASCII表中可见字符到~结束，偏移位值为126(Decimal)
	 */
	static final char DBC_CHAR_END = 126; // 半角~

	/**
	 * 全角对应于ASCII表的可见字符从！开始，偏移值为65281
	 */
	static final char SBC_CHAR_START = 65281; // 全角！

	/**
	 * 全角对应于ASCII表的可见字符到～结束，偏移值为65374
	 */
	static final char SBC_CHAR_END = 65374; // 全角～

	/**
	 * ASCII表中除空格外的可见字符与对应的全角字符的相对偏移
	 */
	static final int CONVERT_STEP = 65248; // 全角半角转换间隔

	/**
	 * 全角空格的值，它没有遵从与ASCII的相对偏移，必须单独处理
	 */
	static final char SBC_SPACE = 12288; // 全角空格 12288

	/**
	 * 半角空格的值，在ASCII中为32(Decimal)
	 */
	static final char DBC_SPACE = ' '; // 半角空格


	/**
	 * @Description: 单字符，半角字符->全角字符，只处理空格及!到˜之间的字符
	 * @Param: 半角字符 Char
	 * @Return: 全角字符 Int
	 */
	public static int bj2qj(char src) {
		int result = src;
		// 半角空格，直接用全角空格替代
		if (src == DBC_SPACE) {
			result = SBC_SPACE;
		}
		// 字符是!到~之间的可见字符
		else if ((src >= DBC_CHAR_START) && (src <= DBC_CHAR_END)) {
			result = src + CONVERT_STEP;
		}
		//其他字符不做处理，直接返回
		return result;
	}

	/**
	 * @Description: 字符串，半角字符->全角字符，只处理空格及!到˜之间的字符
	 * @Param: 半角字符串 String（char[]）
	 * @Return: 全角字符串 String
	 */
	public static String bj2qj(String src) {
		if (src == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder(src.length());
		char[] chars = src.toCharArray();
		for (char aChar : chars) {
			//调用单字符方法处理
			builder.append((char) bj2qj(aChar));
		}
		return builder.toString();
	}

	/**
	 * @Description: 单字符，全角字符->半角字符，只处理空格及!到˜之间的字符
	 * @Param: 全角字符 Char
	 * @Return: 半角字符 Int
	 */
	public static int qj2bj(char src) {
		int result = src;
		// 全角空格，直接用半角空格替代
		if (src == SBC_SPACE) {
			result = DBC_SPACE;
		}
		// 字符是!到~之间的可见字符
		else if (src >= SBC_CHAR_START && src <= SBC_CHAR_END) {
			result = src - CONVERT_STEP;
		}
		//其他字符不做处理，直接返回
		return result;
	}

	/**
	 * @Description: 字符串，全角字符->半角字符，只处理空格及!到˜之间的字符
	 * @Param: 全角字符串 String（char[]）
	 * @Return: 半角字符串 String
	 */
	public static String qj2bj(String src) {
		if (src == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder(src.length());
		char[] chars = src.toCharArray();
		for (char aChar : chars) {
			//调用单字符方法处理
			builder.append((char) qj2bj(aChar));
		}
		return builder.toString();
	}
}
