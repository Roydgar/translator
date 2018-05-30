package tk.roydgar.generator;

import tk.roydgar.generator.constants.ListingConstants;
import tk.roydgar.parser.Tree;
import tk.roydgar.scanner.InfoTables;
import tk.roydgar.scanner.constants.Constants;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

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
                writer.append(String.format(ListingConstants.DECLARATION, declaration.getKey(),
                       declaration.getValue()));
            }
            writer.append("\n");

            writer.append(ListingConstants.DATA_ENDS);
            writer.append(ListingConstants.BODY);

            writer.flush();
            writer.close();
        }
    }

}
