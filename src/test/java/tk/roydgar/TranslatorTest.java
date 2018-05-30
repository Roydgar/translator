package tk.roydgar;

import org.junit.Test;
import tk.roydgar.constants.SourceFileNames;

import java.io.IOException;

import static tk.roydgar.util.CreatorUtil.createTranslator;

public class TranslatorTest {

    @Test
    public void trueTest() {
        Translator translator = createTranslator();
        try {
            translator.run(SourceFileNames.CORRECT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(expected = TranslatorException.class)
    public void falseTest() {
        Translator translator = createTranslator();
        try {
            translator.run(SourceFileNames.MISSING_KEYWORD);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
