import Util.LoadWords;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @Auther: Rowan
 * @Date: 2018/8/17 22:50
 * @Description:
 */
class LoadWordsTest {

	@Test
	void readWordFromFileTest() {
		List<String> words = LoadWords.readWordFromFile("sensitiveWords.txt");
		System.out.println(words.size());
	}
}
