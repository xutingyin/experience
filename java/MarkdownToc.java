
import com.github.houbb.markdown.toc.core.impl.AtxMarkdownToc;

public class MarkdownToc {
    // javac -cp markdown-toc-1.0.8.jar MarkdownToc.java
    // java -cp .;markdown-toc-1.0.8.jar MarkdownToc
    public static void main(String[] args) {
        String mdStr = "JavaInterview.md";
        AtxMarkdownToc.newInstance().genTocFile(mdStr);
    }
}
