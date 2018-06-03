package tk.roydgar.generator;

import tk.roydgar.generator.constants.Directives;
import tk.roydgar.generator.constants.ErrorMessages;
import tk.roydgar.generator.constants.ListingConstants;
import tk.roydgar.parser.Tree;
import tk.roydgar.scanner.InfoTables;
import tk.roydgar.scanner.constants.Constants;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;

import static java.lang.Math.pow;

public class CodeGenerator {
    private Tree tree;
    private FileWriter writer;
    private String listingFileName;
    private InfoTables infoTables;
    private StringBuilder errors = new StringBuilder();

    public CodeGenerator(InfoTables infoTables){
        this.tree = infoTables.getParserTree();
        this.listingFileName = infoTables.getOutputFileName().replaceAll(Constants.LISTING_FILE_EXT,
                ListingConstants.LST_FILE_EXTENSION);
        this.infoTables = infoTables;
    }

    public InfoTables run() throws IOException{
        try (FileWriter writer = new FileWriter(listingFileName)){
            writer.append(ListingConstants.VERSION);
            writer.append(String.format(ListingConstants.PROG, tree.getProcedureIdentifier()));
            writer.append(ListingConstants.DATA_SEGMENT);

            for (Map.Entry<String, String> declaration : tree.getDeclarations().entrySet()) {
                try {
                    String converted = convert(declaration.getValue());
                    writer.append(String.format(ListingConstants.CONSTANT_DECLARATION, declaration.getKey(),
                            getDataType(converted), converted));
                } catch (UnsupportedNumberRangeException numberIsTooBig) {
                    error(declaration.getKey(), declaration.getValue());
                }
            }
            writer.append("\n");

            writer.append(ListingConstants.DATA_ENDS);
            writer.append(ListingConstants.BODY);

            writer.close();
        }

        if (!errors.toString().isEmpty()) {
            try (FileWriter writer = new FileWriter(listingFileName)){
                writer.close();
            }
            infoTables.setGeneratorErrors(errors.toString());
        }


        return infoTables;
    }

    private String convert(String value) throws UnsupportedNumberRangeException {
        double decimal = Double.valueOf(value.replace('#', 'E'));

        checkRange(decimal);
        DecimalFormat df = new DecimalFormat(ListingConstants.NUMBER_FORMAT_PATTERN);
        df.setMaximumFractionDigits(8);
        return df.format(decimal);
    }

    private void checkRange(double decimal) throws UnsupportedNumberRangeException{
        if (Double.isInfinite(decimal) || Double.isNaN(decimal)){
            throw new UnsupportedNumberRangeException();
        }

        String doubleValue = Double.valueOf(decimal).toString();
        if (doubleValue.contains("E")) {
            int fractional = Integer.valueOf(doubleValue.substring(doubleValue.indexOf('E') + 2));

            if (fractional > 7) {
                throw new UnsupportedNumberRangeException();
            }
        }
    }

    private String getDataType(String value) throws UnsupportedNumberRangeException {
        double convertedValue = Double.valueOf(value);

        if (convertedValue > - 1 && convertedValue < 1) {
            return Directives.DQ;
        }

        if (convertedValue >= - 128 && convertedValue <= 127) {
            return Directives.DB;
        }

        if (convertedValue >= - 32768 && convertedValue <= 32767) {
            return Directives.DW;
        }

        if (convertedValue >= - 2147483648 && convertedValue <= 2147483647) {
            return Directives.DD;
        }

        if (convertedValue >=  pow(-2 , 47) && convertedValue <= pow(2 , 46)) {
            return Directives.DF;
        }

        if (convertedValue >=  pow(-2 , 63) && convertedValue <= pow(2 , 62)) {
            return Directives.DQ;
        }

        throw new UnsupportedNumberRangeException();
    }

    private void error(String identifier, String value) {

        errors.append(String.format(ErrorMessages.UNSUPPORTED_NUMBER_RANGE, value, identifier));
    }

    private class UnsupportedNumberRangeException extends Exception{}

}
