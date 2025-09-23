package io.modelcontextprotocol.common;

import java.util.List;
import io.modelcontextprotocol.spec.McpSchema.ToolGroup;
import io.modelcontextprotocol.util.Assert;
import io.modelcontextprotocol.server.McpStatelessServerFeatures.AsyncToolSpecification;

public class AsyncStatelessToolGroup extends AbstractToolGroup {

	private final List<AsyncToolSpecification> specifications;

	public AsyncStatelessToolGroup(ToolGroup toolGroup, List<AsyncToolSpecification> specifications) {
		super(toolGroup);
		Assert.notNull(specifications,  "specifications cannot be null");
		this.specifications = specifications;
	}

	public List<AsyncToolSpecification> specifications() {
		return this.specifications;
	}
}
