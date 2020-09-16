
import com.github.houbb.markdown.toc.core.impl.AtxMarkdownToc;

public class MarkdownToc {
    public static void main(String[] args) {
        String mdStr = "JavaInterview.md";
        AtxMarkdownToc.newInstance().genTocFile(mdStr);
    }
}
