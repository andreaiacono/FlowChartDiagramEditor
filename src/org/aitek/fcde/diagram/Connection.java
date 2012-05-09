package org.aitek.fcde.diagram;

public class Connection {

    private Block sourceBlock;
    private String destinationBlockId;
    private String label;
    private final int index;

    public Connection(String destinationBlockId, String description, Block sourceBlock, int index) {

        this.destinationBlockId = destinationBlockId;
        this.label = description;
        this.setSourceBlock(sourceBlock);
        this.index = index;
    }

    public String getDestinationBlockId() {

        return destinationBlockId;
    }

    public void setDestinationBlockId(String destinationBlockId) {

        this.destinationBlockId = destinationBlockId;
    }

    public String getLabel() {

        return label;
    }

    public void setLabel(String description) {

        this.label = description;
    }

    public void setSourceBlock(Block sourceBlock) {

        this.sourceBlock = sourceBlock;
    }

    public Block getSourceBlock() {

        return sourceBlock;
    }

    public int getIndex() {
        return index;
    }
}
