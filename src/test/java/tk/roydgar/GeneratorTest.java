package tk.roydgar;

import org.junit.Test;
import tk.roydgar.constants.SourceFileNames;
import tk.roydgar.generator.CodeGenerator;
import tk.roydgar.scanner.InfoTables;

import static org.junit.Assert.assertTrue;
import static tk.roydgar.util.CreatorUtil.createCodeGenerator;

public class GeneratorTest {

    @Test
    public void trueTest() {
        InfoTables infoTables = createCodeGenerator(SourceFileNames.CORRECT);
        assertTrue(infoTables.getGeneratorErrors().isEmpty());
    }

    @Test
    public void UnsupportedNumberRangeTest() {
        InfoTables infoTables = createCodeGenerator(SourceFileNames.UNSUPPORTED_NUMBER_RANGE);
        assertTrue(!infoTables.getGeneratorErrors().isEmpty());
    }
}
