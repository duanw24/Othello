package othello.ai;

import lombok.Getter;
import lombok.Setter;

public class Tree {
    @Setter@Getter
    Node root;

    public Tree(Node ro) {
        this.root = ro;
    }
}
