package tk.roydgar.parser;


import tk.roydgar.parser.constants.TreeNodeNames;
import tk.roydgar.scanner.constants.Delimiters;
import tk.roydgar.scanner.constants.Keywords;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Tree {

    private List<Node> tree = new ArrayList<>();
    private int declarationCounter;
    private Map<String, String> declarations = new LinkedHashMap<>();
    private String outputFileName;

    class Node {
        int prefix;
        String name;

        private Node(int prefix, String name) {
            this.prefix = prefix;
            this.name = name;
        }
    }

    public String getProcedureIdentifier() { return tree.get(5).name; }

    public Map<String , String> getDeclarations() { return declarations; }

    public Tree() {};

    public Tree(String outputFileName) {
        tree.add(new Node(0, TreeNodeNames.SIGNAL_PROGRAM));
        tree.add(new Node(4, TreeNodeNames.PROGRAM));
        tree.add(new Node(8, Keywords.PROGRAM.toUpperCase()));
        tree.add(new Node(8, TreeNodeNames.PROCEDURE_IDENTIFIER));
        tree.add(new Node(12, TreeNodeNames.IDENTIFIER));
        tree.add(new Node(16, ""));
        tree.add(new Node(8, Delimiters.SEMICOLON));
        tree.add(new Node(8, TreeNodeNames.BLOCK));

        tree.add(new Node(12, TreeNodeNames.DECLARATIONS));
        tree.add(new Node(16, TreeNodeNames.CONSTANT_DECLARATION));
        this.outputFileName = outputFileName;
    }

    public void setProgramIdentifier(String identifier) {
        tree.set(5, new Node(16, identifier));
    }

    public void addDeclaration(String identifier, String value) {
        tree.add(new Node(20, Keywords.CONST.toUpperCase()));
        tree.add(new Node(20, TreeNodeNames.CONSTANT_DECLAR_LIST));
        tree.add(new Node(24, TreeNodeNames.CONSTANT_DECLARATION));
        tree.add(new Node(28, TreeNodeNames.CONSTANT_IDENTIFIER));
        tree.add(new Node(32, TreeNodeNames.IDENTIFIER));
        tree.add(new Node(36, identifier));
        tree.add(new Node(28, TreeNodeNames.PROCEDURE_IDENTIFIER));
        tree.add(new Node(28, Delimiters.EQUAL));
        tree.add(new Node(28, TreeNodeNames.CONSTANT));
        tree.add(new Node(32, value));
        tree.add(new Node(28, Delimiters.SEMICOLON));

        declarationCounter++;
        declarations.put(identifier, value);

    }

    public void addTail() {
        if (declarationCounter == 0) {
            tree.add(new Node(20, TreeNodeNames.EMPTY));

        }
        tree.add(new Node(12, Keywords.BEGIN.toUpperCase()));
        tree.add(new Node(12, TreeNodeNames.STATEMENT_LIST));
        tree.add(new Node(16, TreeNodeNames.EMPTY));
        tree.add(new Node(12, Keywords.END.toUpperCase()));
        tree.add(new Node(8, Delimiters.DOT));

        try {
            FileWriter writer = new FileWriter(outputFileName, true);
            writer.append("\n").append("Tree:").append("\n");

            for (Node node : tree) {
                writer.append(formSpaces(node.prefix)).append(node.name).append("\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print() {
        for (Node node : tree) {
            System.out.println(formSpaces(node.prefix) + node.name);
        }
    }

    private String formSpaces(int count) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < count; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public boolean isEmpty() { return tree.isEmpty(); }
};


