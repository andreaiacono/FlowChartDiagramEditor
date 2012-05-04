package org.aitek.fcde.diagram;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.aitek.fcde.diagram.Block.Type;
import org.aitek.fcde.utils.Constants;

public class FlowChart {

	private Map<String, Block> blocks = new HashMap<String, Block>();

	public String parseDiagram(String diagram) throws Exception {

		BufferedReader reader = new BufferedReader(new StringReader(diagram));
		String line;
		Block currentBlock = null;
		Type type = null;
		int lineCounter = 1;
		blocks.clear();

		while (true) {
			line = reader.readLine();
			if (line == null) {
				break;
			}
			line = line.trim();

			// if it's a comment or blank
			if (line.startsWith("#") || line.startsWith("//") || line.length() == 0) {
				continue;
			}

			// it's the type of block
			if (line.startsWith("[")) {

				// gets it
				int end = line.indexOf(",");
				if (end < 0) {
					throw new Exception("Not existing type descriptor. Allowed types are: [START|PROCESS|CONTROL|IO|END].");
				}
				try {

					type = Type.valueOf(line.substring(1, end).toUpperCase());
				}
				catch (Exception ex) {
					throw new Exception("Not existing block [" + line.substring(1, end).toUpperCase() + "] at line " + lineCounter);
				}

				// gets the ID of the block
				if (line.length() > 1 && line.length() > end + 1) {

					String id = line.substring(end + 1, line.length() - 1).trim();

					if (type == Type.START && getStartingBlock() != null) {
						throw new Exception("Only one [START] block is allowed.");
					}

					currentBlock = new Block(id, type, "", this);
					add(currentBlock);
				}
			}
			else if (line.startsWith(Constants.SWITCH_KEYWORD + "=")) {

				String switchKey = line.substring(Constants.SWITCH_KEYWORD.length() + 1);
				if (currentBlock == null) {
					throw new Exception("The block with id [" + switchKey + "] does not exist.");
				}

				String[] values = switchKey.split(",");
				String id = values[0];
				String text = values.length > 1 ? values[1] : null;

				Connection connectingBlock = new Connection(id, text, currentBlock);
				currentBlock.getConnections().add(connectingBlock);
			}
			else if (line.startsWith(Constants.NEXT_KEYWORD + "=")) {

				String next = line.substring(Constants.NEXT_KEYWORD.length() + 1);
				if (currentBlock == null) {
					throw new Exception("The block with id [" + next + "] does not exist.");
				}
				String[] values = next.split(",");
				String id = values[0];
				String text = values.length > 1 ? values[1] : null;

				Connection connectingBlock = new Connection(id, text, currentBlock);
				currentBlock.getConnections().add(connectingBlock);
			}

			else {

				if (currentBlock == null) {
					throw new Exception("No block found for text [" + line + "]");
				}
				currentBlock.appendLine(line);
			}

			lineCounter++;
		}

		return "Flowchart has " + blocks.size() + " blocks.";
	}

	public Block getStartingBlock() {

		for (String key : blocks.keySet()) {

			Block block = blocks.get(key);
			if (block.getType() == Type.START) {
				return block;
			}
		}

		return null;
	}

	public void add(Block block) {

		blocks.put(block.getId(), block);
	}

	public Set<String> getBlockIds() {

		return blocks.keySet();
	}

	public Block getBlock(String id) {

		return blocks.get(id);
	}

	public int getBlocksNumber() {

		return blocks.keySet().size();
	}
}
