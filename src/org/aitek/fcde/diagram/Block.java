package org.aitek.fcde.diagram;

import java.util.ArrayList;

public class Block {

    public enum Type {

        START, PROCESS, IO, CONTROL, END
    }
    protected FlowChart flowChart;
    private String id;
    private Type type;
    private ArrayList<Connection> edges;
    private String innerText;

    public Block(String id, Type type, String innerText, FlowChart flowChart) {

        this.id = id;
        this.type = type;
        this.innerText = innerText;
        this.flowChart = flowChart;
        edges = new ArrayList<Connection>();
    }

    public void appendLine(String innerText) {

        if (this.innerText == null || this.innerText.length() == 0) {
            this.innerText = innerText;
        }
        else {
            this.innerText = this.innerText + "\n" + innerText;
        }
    }

    public void setType(Type type) {

        this.type = type;
    }

    public Type getType() {

        return type;
    }

    public ArrayList<Connection> getConnections() {

        return edges;
    }

    public void setInnerText(String innerText) {

        this.innerText = innerText;
    }

    public String getInnerText() {

        return innerText;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public FlowChart getFlowChart() {

        return flowChart;
    }

    @Override
    public String toString() {

        StringBuffer connected = new StringBuffer();
        if (edges != null) {
            for (Connection connection : edges) {
                connected.append(connection.getDestinationBlockId()).append(" - ");
            }
        }

        return "Block{" + "id='" + id + '\'' + ", type=" + type + ", connectedBlocks=" + connected + ", innerText='" + (innerText.length() > 20 ? innerText.substring(0, 20) : innerText) + "... \'" + '}';
    }
}
