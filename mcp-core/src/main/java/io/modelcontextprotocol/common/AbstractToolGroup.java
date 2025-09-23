package io.modelcontextprotocol.common;

import io.modelcontextprotocol.spec.McpSchema.ToolGroup;
import io.modelcontextprotocol.util.Assert;

public class AbstractToolGroup {

	protected final ToolGroup toolGroup;
	
	public AbstractToolGroup(ToolGroup toolGroup) {
		Assert.notNull(toolGroup, "toolGroup cannot be null");
		this.toolGroup = toolGroup;
	}
	
	public ToolGroup getToolGroup() {
		return this.toolGroup;
	}
}
