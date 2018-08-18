import Util.BCConvert;
import Util.LoadWords;

import java.util.*;

/**
 * @Auther: Rowan
 * @Date: 2018/8/17 22:31
 * @Description:
 */
public class WordFilter {
	// 存储首字
	private static final FilterSet set = new FilterSet();

	// 存储节点
	private static final Map<Integer, WordNode> nodes = new HashMap<>(1024, 1);

	// 间隔停顿词
	private static final Set<Integer> intervalWords = new HashSet<>();

	// 敏感词过滤替换
	private static final char REPLACE_SIGN = '*';

	//初始化，可在此类中直接进行初始化，也可通过在外部调用add方法添加
	static {
		try {
			addSensitiveWord(LoadWords.readWordFromFile("sensitiveWords.txt"));
			addIntervalWord(LoadWords.readWordFromFile("intervalWords.txt"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * @Description: 添加一个间隔词
	 * @Param: String
	 * @Return:
	 */
	public static void addIntervalWord(String word) {
		if (word != null) {
			char[] chars;
			chars = word.toCharArray();
			for (char c : chars) {
				intervalWords.add(charConvert(c));
			}
		}
	}

	/**
	 * @Description: 添加多个间隔词
	 * @Param: List<String>
	 * @Return:
	 */
	public static void addIntervalWord(List<String> words) {
		if (words != null && !words.isEmpty()) {
			for (String word : words) {
				addIntervalWord(word);
			}
		}
	}

	/**
	 * @Description: 添加一个敏感词
	 * @Param: String
	 * @Return:
	 */
	public static void addSensitiveWord(String word) {
		if (word != null) {
			//字符串数组
			char[] chars;
			//第一个字符
			int firstChar;
			//字符串中最后一个字符位置
			int lastIndex;
			//首字母节点
			WordNode firstNode;

			chars = word.toCharArray();
			firstChar = charConvert(chars[0]);
			// 首字没有在set中
			if (!set.contains(firstChar)) {
				//首字存入set
				set.add(firstChar);
				//创建首节点
				firstNode = new WordNode(firstChar, chars.length == 1);
				//加入存储节点
				nodes.put(firstChar, firstNode);
			}
			//首字已存在set中
			else {
				//取出首节点
				firstNode = nodes.get(firstChar);
				//如果当前首节点不为终点且新添加的敏感词为单个字符，则改为匹配终点，如存在“日你妹”再添加“日”
				if (!firstNode.isEnd() && chars.length == 1)
					firstNode.setEnd(true);
			}
			//敏感词字符串中最后一个字符位置
			lastIndex = chars.length - 1;
			//将剩余字符作为子节点加入
			for (int i = 1; i < chars.length; i++) {
				firstNode = firstNode.addIfNotExist(charConvert(chars[i]), i == lastIndex);
			}
		}
	}

	/**
	 * @Description: 添加多个敏感词
	 * @Param: List<String>
	 * @Return:
	 */
	public static void addSensitiveWord(List<String> words) {
		if (words != null && !words.isEmpty()) {
			for (String word : words) {
				addSensitiveWord(word);
			}
		}
	}

	/**
	 * @Description: 删除一个敏感词
	 * @Param: String
	 * @Return:
	 */
	//TODO
	public static void deleteSentiveWord(String word) {
		if (word != null) {
			//字符串数组
			char[] chars = word.toCharArray();
			//第一个字符
			int firstChar = charConvert(chars[0]);
			//字符串中最后一个字符位置
			int lastIndex = chars.length - 1;
			//首字母节点
			WordNode firstNode = nodes.get(firstChar);
			if (set.contains(firstChar)){

			}
		}
	}

	/**
	 * @Description: 将敏感词转化为成屏蔽词
	 * @Param: String
	 * @Return: String
	 */
	public static String doFilter(String src) {
		//检查文本字符串
		char[] chars = src.toCharArray();
		//检查文本字符串长度
		int length = chars.length;
		//当前检查的字符
		int currentChar;
		//当前检查字符的备份
		int currentCharCpy;
		int k;
		WordNode node;
		for (int i = 0; i < length; i++) {
			currentChar = charConvert(chars[i]);
			//set中不包含该字符，即非敏感字
			if (!set.contains(currentChar)) {
				continue;
			}
			//当前字符为敏感字首字,获取以该字符为首字的敏感字Node
			node = nodes.get(currentChar);
			//敏感词结束匹配标记
			boolean endMark = false;
			//敏感词长度 - 1
			int endLength = -1;
			//单敏感字匹配，如“日”
			if (node.isEnd()) {
				endMark = true;
				endLength = 0;
			}
			//继续匹配，匹配最长，如“日”和“日你妹”,将匹配最长的“日你妹”
			k = i;
			//拷贝当前字符
			currentCharCpy = currentChar;
			//在文本中查找一个敏感词
			while (++k < length) {
				//当前字符
				int temp = charConvert(chars[k]);
				//当前字符与前一字符相同，则跳过
				if (temp == currentCharCpy)
					continue;
				//当前字符在间隔词中，则跳过
				if (intervalWords.contains(temp))
					continue;
				//在敏感词Node中查找子节点
				node = node.querySub(temp);
				//敏感词Node不再存在子节点，即该敏感词匹配结束
				if (node == null)
					break;
				//当前节点为终节点
				if (node.isEnd()) {
					endMark = true;
					endLength = k - i;
				}
				//记录当前字符，即成为下一趟循环的前一个字符
				currentCharCpy = temp;
			}
			//将敏感词替换
			if (endMark) {
				for (k = 0; k <= endLength; k++) {
					chars[k + i] = REPLACE_SIGN;
				}
				//将i移到处理过的敏感词后
				i = i + endLength + 1;
			}
		}
		return new String(chars);
	}

	/**
	 * @Description: 判断是否包含敏感词
	 * @Param: String
	 * @Return: Boolean
	 */
	public static boolean isContains(final String src) {
		//检查文本字符串
		char[] chars = src.toCharArray();
		//检查文本字符串长度
		int length = chars.length;
		//当前检查的字符
		int currentChar;
		//当前检查字符的备份
		int currentCharCpy;
		int k;
		WordNode node;
		for (int i = 0; i < length; i++) {
			currentChar = charConvert(chars[i]);
			//set中不包含该字符，即非敏感字
			if (!set.contains(currentChar)) {
				continue;
			}
			//当前字符为敏感字首字,获取以该字符为首字的敏感字Node
			node = nodes.get(currentChar);
			//当前节点为终节点
			if (node.isEnd()) {
				return true;
			}
			// 非终节点，继续匹配
			k = i;
			currentCharCpy = currentChar;
			while (++k < length) {
				//当前字符
				int temp = charConvert(chars[k]);
				//当前字符与前一字符相同，则跳过
				if (temp == currentCharCpy)
					continue;
				//当前字符在间隔词中，则跳过
				if (intervalWords.contains(temp))
					continue;
				//在敏感词Node中查找子节点
				node = node.querySub(temp);
				//敏感词Node不再存在子节点，即该敏感词匹配结束
				if (node == null)
					break;
				//当前节点为终节点
				if (node.isEnd()) {
					return true;
				}
				//记录当前字符，即成为下一趟循环的前一个字符
				currentCharCpy = temp;
			}
		}
		return false;
	}

	/**
	 * @Description: 将字符大写转化为小写，全角转化为半角
	 * @Param: char
	 * @Return: int
	 */
	private static int charConvert(char src) {
		int r = BCConvert.qj2bj(src);
		return (r >= 'A' && r <= 'Z') ? r + 32 : r;
	}
}
