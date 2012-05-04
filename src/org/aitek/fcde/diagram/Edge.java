package org.aitek.fcde.diagram;


public class Edge {

	private Block sourceBlock;
	private String destinationBlockId;

	private String label;

	public Edge(String destinationBlockId, String description, Block sourceBlock) {

		this.destinationBlockId = destinationBlockId;
		this.label = description;
		this.setSourceBlock(sourceBlock);
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
}
