package tk.roydgar;

import org.junit.Test;
import tk.roydgar.constants.SourceFileNames;
import tk.roydgar.generator.CodeGenerator;
import tk.roydgar.generator.GeneratorEmptyTreeException;

import static tk.roydgar.util.CreatorUtil.createCodeGenerator;

public class GeneratorTest {

    @Test
    public void trueTest() {
        CodeGenerator codeGenerator = createCodeGenerator(SourceFileNames.CORRECT);
    }

    @Test(expected = GeneratorEmptyTreeException.class)
    public void falseTest() {
        CodeGenerator codeGenerator = createCodeGenerator(SourceFileNames.WRONG_KEYWORD);
    }
}
