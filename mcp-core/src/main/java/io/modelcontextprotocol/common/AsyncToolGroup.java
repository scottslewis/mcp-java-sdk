package io.modelcontextprotocol.common;

import java.util.List;

import io.modelcontextprotocol.server.McpServerFeatures.AsyncToolSpecification;
import io.modelcontextprotocol.spec.McpSchema.ToolGroup;

public class AsyncToolGroup extends AbstractToolGroup {

	private final List<AsyncToolSpecification> specifications;

	public AsyncToolGroup(ToolGroup toolGroup, List<AsyncToolSpecification> specifications) {
		super(toolGroup);
		this.specifications = specifications;
	}

	public List<AsyncToolSpecification> getSpecifications() {
		return this.specifications;
	}

}
