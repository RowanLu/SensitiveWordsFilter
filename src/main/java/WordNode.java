import java.util.LinkedList;
import java.util.List;

/**
 * @Auther: Rowan
 * @Date: 2018/8/16 22:55
 * @Description: 敏感词的DFA模型
 */
public class WordNode {
	// 节点值(字符)
	private int value;

	// 子节点
	private List<WordNode> subNodes;

	// 是否为敏感词匹配终节点，默认false，如“中国人”中“人”为匹配终点
	private boolean isEnd;

	public WordNode(int value) {
		this.value = value;
	}

	public WordNode(int value, boolean isEnd) {
		this.value = value;
		this.isEnd = isEnd;
	}

	/**
	 * @Description: 添加子节点
	 * @Param: WordNode
	 * @Return: WordNode
	 */
	private WordNode addSubNode(WordNode subNode) {
		if (subNodes == null)
			subNodes = new LinkedList<>();
		subNodes.add(subNode);
		return subNode;
	}
	
	/**
	 * @Description: 有就直接返回该子节点， 没有就创建添加并返回该子节点
	 * @Param: 节点值value, 节点是否为终节点isEnd
	 * @Return: WordNode
	 */
	public WordNode addIfNotExist(int value, boolean isEnd) {
		//当前子节点为空，为当前子节点添加一个节点
		if (subNodes == null) {
			return addSubNode(new WordNode(value, isEnd));
		}
		//遍历当前子节点的所有值
		for (WordNode subNode : subNodes) {
			//当前子节点已存在该值
			if (subNode.value == value) {
				//已存在的子节点值不是匹配终点且添加的值为匹配终点，将该值设为终点
				//这种情况下，若“中国”和“中国人”均为敏感字，则匹配“中国”，而“中国人”将不在被匹配
				if (!subNode.isEnd && isEnd)
					subNode.isEnd = true;
				return subNode;
			}
		}
		return addSubNode(new WordNode(value, isEnd));
	}

	/**
	 * @Description: 查找子节点
	 * @Param: 节点值value
	 * @Return: WordNode
	 */
	public WordNode querySub(int value) {
		if (subNodes == null) {
			return null;
		}
		//遍历查找
		for (WordNode subNode : subNodes) {
			if (subNode.value == value)
				return subNode;
		}
		return null;
	}

	@Override
	//TODO 重写toString
	public String toString() {
		return super.toString();
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean end) {
		isEnd = end;
	}
}
