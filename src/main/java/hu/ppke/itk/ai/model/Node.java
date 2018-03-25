package hu.ppke.itk.ai.model;

public class Node {

    private int xPos;
    private int yPos;
    private Category category;
    private Node northNeighb;
    private Node eastNeighb;
    private Node southNeighb;
    private Node westNeighb;

    public Node(int xPos, int yPos, Category category) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.category = category;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public Category getCategory() {
        return category;
    }
}
