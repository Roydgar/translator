package tk.roydgar.parser;


import tk.roydgar.parser.constants.TreeNodeNames;

import java.util.*;

class Tree {

    private List<Node> tree = new ArrayList<>();
    private int declarationCounter;

    class Node {
        int prefix;
        String name;

        private Node(int prefix, String name) {
            this.prefix = prefix;
            this.name = name;
        }
    }

    Tree() {
        tree.add(new Node(0, TreeNodeNames.SIGNAL_PROGRAM));
        tree.add(new Node(4, TreeNodeNames.PROGRAM));
        tree.add(new Node(8, "PROGRAM"));
        tree.add(new Node(8, TreeNodeNames.PROCEDURE_IDENTIFIER));
        tree.add(new Node(12, TreeNodeNames.IDENTIFIER));
        tree.add(new Node(16, ""));
        tree.add(new Node(8, ";"));
        tree.add(new Node(8, TreeNodeNames.BLOCK));

        tree.add(new Node(12, TreeNodeNames.DECLARATIONS));
        tree.add(new Node(16, TreeNodeNames.CONSTANT_DECLARATION));
    }

    public void setProgramIdentifier(String identifier) {
        tree.set(5, new Node(16, identifier));
    }

    public void addDeclaration(String identifier, String value) {
        tree.add(new Node(20, "CONST"));
        tree.add(new Node(20, TreeNodeNames.CONSTANT_DECLAR_LIST));
        tree.add(new Node(24, TreeNodeNames.CONSTANT_DECLARATION));
        tree.add(new Node(28, TreeNodeNames.CONSTANT_IDENTIFIER));
        tree.add(new Node(32, TreeNodeNames.IDENTIFIER));
        tree.add(new Node(36, identifier));
        tree.add(new Node(28, TreeNodeNames.PROCEDURE_IDENTIFIER));
        tree.add(new Node(28, "="));
        tree.add(new Node(28, TreeNodeNames.CONSTANT));
        tree.add(new Node(32, value));
        tree.add(new Node(28, ";"));

        declarationCounter++;

    }


    public void print() {
        if (declarationCounter == 0) {
            tree.add(new Node(20, TreeNodeNames.EMPTY));

        }
        tree.add(new Node(12, "BEGIN"));
        tree.add(new Node(12, TreeNodeNames.STATEMENT_LIST));
        tree.add(new Node(16, TreeNodeNames.EMPTY));
        tree.add(new Node(12, "END"));
        tree.add(new Node(8, "."));

        for (Node node : tree) {
            printSpaces(node.prefix);
            System.out.println(node.name);
        }

    }

    private void printSpaces(int count) {
        for(int i = 0; i < count; i++) {
            System.out.print(" ");
        }
    }
};


