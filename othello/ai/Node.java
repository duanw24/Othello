package othello.ai;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Node {
    @Getter@Setter
    Point p;
    @Getter@Setter
    boolean isMaxPlayer;
    @Getter@Setter
    int score;
    @Getter@Setter
    ArrayList<Node> children;
    @Getter@Setter
    Node parent;

    public Node(Point p,boolean isMax,Node par) {
        this.parent = par;
        this.p = p;
        this.isMaxPlayer = isMax;
    }

    public ArrayList<Node> getChildren(Node n) {
        ArrayList<Node> ar = new ArrayList<>();

        return ar;
    }

}
