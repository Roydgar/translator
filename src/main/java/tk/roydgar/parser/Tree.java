package tk.roydgar.parser;


import java.util.ArrayList;
import java.util.List;

class Tree {
    private Tree root;
    private String name;
    private List<Tree> nodes;

    Tree(Tree root, String name) {
        this.root = root;
        this.name = name;
        this.nodes = new ArrayList<>();
    }

    void PrintTree(Tree root, int prefix) {
        System.out.println(String.format("%" + prefix +"c%s", '\0', root.name));

        for (Tree node : root.nodes) {
            PrintTree(node, prefix + 4);
        }
    }
};


