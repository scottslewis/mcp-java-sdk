package io.modelcontextprotocol.common;

import io.modelcontextprotocol.spec.McpSchema;

public class PromptArgumentNode extends AbstractNode {

	protected boolean required;

	public PromptArgumentNode(String name) {
		super(name);
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean getRequired() {
		return this.required;
	}

	public McpSchema.PromptArgument serialize() {
		return new McpSchema.PromptArgument(getName(), getTitle(), getDescription(), getRequired());
	}

	public static PromptArgumentNode deserialize(McpSchema.PromptArgument promptArgument) {
		PromptArgumentNode pa = new PromptArgumentNode(promptArgument.name());
		pa.setTitle(promptArgument.title());
		pa.setDescription(promptArgument.description());
		pa.setRequired(promptArgument.required());
		return pa;
	}

	@Override
	public String toString() {
		return "PromptArgumentNode [required=" + required + ", name=" + name + ", title=" + title + ", description="
				+ description + ", meta=" + meta + "]";
	}

}
