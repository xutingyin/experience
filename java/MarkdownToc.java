
import com.github.houbb.markdown.toc.core.impl.AtxMarkdownToc;

/**
 * @description:解决Github 不支持markdown [TOC] 生成目录
 * @author: xuty
 * @date: 2020/9/16 12:02
 */

public class MarkdownToc {
    // 注：使用 javac 编译此类时，需要添加markdown-toc-1.0.8.jar 依赖,如果需要依赖多个jar,则使用分号分隔
    // 具体命令： javac -cp markdown-toc-1.0.8.jar MarkdownToc.java
    // java -cp .;markdown-toc-1.0.8.jar MarkdownToc
    public static void main(String[] args) {
        String mdStr = "JavaInterview.md";
        AtxMarkdownToc.newInstance().genTocFile(mdStr);
    }
}
