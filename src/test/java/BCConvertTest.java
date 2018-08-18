import Util.BCConvert;
import org.junit.jupiter.api.Test;

/**
 * @Auther: Rowan
 * @Date: 2018/8/17 21:48
 * @Description: 半角全角转换测试
 */
class BCConvertTest {

	@Test
	void bj2qjTest() {
		System.out.println(BCConvert.bj2qj("!!!"));
	}

	@Test
	void qj2bjTest() {
		System.out.println(BCConvert.qj2bj("！！！"));
	}
}
