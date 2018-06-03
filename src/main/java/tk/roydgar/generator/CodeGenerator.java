package tk.roydgar.generator;

import tk.roydgar.generator.constants.ListingConstants;
import tk.roydgar.parser.Tree;
import tk.roydgar.scanner.InfoTables;
import tk.roydgar.scanner.constants.Constants;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

import static java.lang.Math.pow;
import static java.lang.Math.tan;

public class CodeGenerator {
    private Tree tree;
    private FileWriter writer;
    private String listingFileName;

    public CodeGenerator(InfoTables infoTables){
        if (infoTables.getParserTree().isEmpty()) {
            throw new IllegalStateException("Parser tree is empty. Can't generate the code");
        }

        this.tree = infoTables.getParserTree();
        this.listingFileName = infoTables.getOutputFileName().replaceAll(Constants.LISTING_FILE_EXT,
                ListingConstants.LST_FILE_EXTENSION);
    }

    public void run() throws IOException{
        try (FileWriter writer = new FileWriter(listingFileName)){
            writer.append(ListingConstants.SEPARATOR);
            writer.append(String.format(ListingConstants.PROG, tree.getProcedureIdentifier()));
            writer.append(ListingConstants.DATA_SEGMENT);

            for (Map.Entry<String, String> declaration : tree.getDeclarations().entrySet()) {
                String converted = convert(declaration.getValue());
                writer.append(String.format(ListingConstants.DECLARATION, declaration.getKey(),
                       getDataType(converted), converted));
            }
            writer.append("\n");

            writer.append(ListingConstants.DATA_ENDS);
            writer.append(ListingConstants.BODY);

            writer.flush();
            writer.close();
        }
    }

    private String convert(String value) {
        double decimal = Double.valueOf(value.replace('#', 'E'));
        DecimalFormat df = new DecimalFormat("#.#");
        df.setMaximumFractionDigits(8);
        return df.format(decimal);
    }

    private String getDataType(String value) {
        double convertedValue = Double.valueOf(value);

        if (convertedValue > - 1 && convertedValue < 1) {
            return "DQ";
        }

        if (convertedValue >= - 128 && convertedValue <= 127) {
            return "DB";
        }

        if (convertedValue >= - 32768 && convertedValue <= 32767) {
            return "DW";
        }

        if (convertedValue >= - 2147483648 && convertedValue <= 2147483647) {
            return "DD";
        }

        if (convertedValue >=  pow(-2 , 47) && convertedValue <= pow(2 , 46)) {
            return "DF";
        }

        if (convertedValue >=  pow(-2 , 63) && convertedValue <= pow(2 , 62)) {
            return "DQ";
        }

        if (convertedValue >=  pow(-2 , 79) && convertedValue <= pow(2 , 78)) {
            return "DT";
        }

        return "";
    }


}
