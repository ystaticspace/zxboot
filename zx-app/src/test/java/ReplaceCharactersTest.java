import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ReplaceCharactersTest {

    /**
     * 替换除中文字符、空格、"-"和","之外的其它字符
     * 
     * @param input 输入字符串
     * @return 处理后的字符串
     */
    public String replaceCharacters(String input) {
        // 定义正则表达式，匹配非中文字符、空格、"-"和","
        String regex = "[^\\u4e00-\\u9fa5\\s\\-,]";
        // 使用正则表达式替换匹配的字符
        return input.replaceAll(regex, "");
    }

    @Test
    public void testReplaceCharacters() {
        // 测试用例
        String input = "Hello, 你好 - 世界!";
        String expectedOutput = ", 你好 - 世界";
        String actualOutput = replaceCharacters(input);

        // 验证结果
        assertEquals(expectedOutput, actualOutput);
    }
}