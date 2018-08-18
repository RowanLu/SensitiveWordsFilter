import org.junit.jupiter.api.Test;

/**
 * @Auther: Rowan
 * @Date: 2018/8/18 13:20
 * @Description:
 */
public class WordFilterTest {

	@Test
	void doFilterTest() {
		System.out.println(WordFilter.doFilter("你是逗比吗？fUcK,你竟然用法轮功，法@!轮!%%%功"));
	}

	@Test
	void isContainsTest() {
		System.out.println(WordFilter.isContains("java"));
	}
}
