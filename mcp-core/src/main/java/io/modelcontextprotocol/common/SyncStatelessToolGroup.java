package io.modelcontextprotocol.common;

import java.util.List;

import io.modelcontextprotocol.server.McpStatelessServerFeatures.SyncToolSpecification;
import io.modelcontextprotocol.spec.McpSchema.ToolGroup;

public class SyncStatelessToolGroup extends AbstractToolGroup {

	private final List<SyncToolSpecification> specifications;

	public SyncStatelessToolGroup(ToolGroup toolGroup, String description, List<SyncToolSpecification> specifications) {
		super(toolGroup);
		this.specifications = specifications;
	}

	public List<SyncToolSpecification> getSpecifications() {
		return this.specifications;
	}

}
