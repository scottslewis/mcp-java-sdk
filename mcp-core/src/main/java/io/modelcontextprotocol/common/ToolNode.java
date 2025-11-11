package io.modelcontextprotocol.common;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpSchema.JsonSchema;

public class ToolNode extends AbstractLeafNode {

	protected JsonSchema inputSchema;

	protected Map<String, Object> outputSchema;

	protected ToolAnnotationNode toolAnnotation;

	public ToolNode(String name) {
		super(name);
	}

	public JsonSchema getInputSchema() {
		return inputSchema;
	}

	public void setInputSchema(JsonSchema inputSchema) {
		this.inputSchema = inputSchema;
	}

	public Map<String, Object> getOutputSchema() {
		return outputSchema;
	}

	public void setOutputSchema(Map<String, Object> outputSchema) {
		this.outputSchema = outputSchema;
	}

	public ToolAnnotationNode getToolAnnotation() {
		return toolAnnotation;
	}

	public void setToolAnnotation(ToolAnnotationNode toolAnnotation) {
		this.toolAnnotation = toolAnnotation;
	}

	public McpSchema.Tool.Builder serialize() {
		McpSchema.Tool.Builder builder = new McpSchema.Tool.Builder();
		builder.name(getName());
		builder.title(getTitle());
		builder.description(getDescription());
		builder.inputSchema(getInputSchema());
		builder.outputSchema(getOutputSchema());
		builder.meta(getMeta());
		ToolAnnotationNode an = getToolAnnotation();
		builder.annotations((an != null) ? an.serialize() : null);
		LinkedHashSet<GroupNode> parentGroupNodes = getParentGroups();
		if (parentGroupNodes != null) {
			List<McpSchema.Group> parentGroups = parentGroupNodes.stream().map(pgn -> {
				return pgn.serialize().build();
			}).collect(Collectors.toList());
			if (parentGroups.size() > 0) {
				builder.groups(parentGroups);
			}
		}
		return builder;
	}

	public static List<ToolNode> deserialize(List<McpSchema.Tool> tools) {
		return tools.stream().map(tool -> {
			ToolNode tn = new ToolNode(tool.name());
			tn.setTitle(tool.title());
			tn.setDescription(tool.description());
			tn.setMeta(tool.meta());
			tn.setInputSchema(tool.inputSchema());
			tn.setOutputSchema(tool.outputSchema());
			McpSchema.ToolAnnotations a = tool.annotations();
			if (a != null) {
				tn.setToolAnnotation(ToolAnnotationNode.deserialize(a));
			}
			List<McpSchema.Group> parentGroups = tool.groups();
			if (parentGroups != null) {
				parentGroups.forEach(pg -> {
					GroupNode.deserialize(pg).addChildTool(tn);
				});
			}
			return tn;

		}).collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return "ToolNode [name=" + name + ", title=" + title + ", description=" + description + ", meta=" + meta
				+ ", inputSchema=" + inputSchema + ", outputSchema=" + outputSchema + ", toolAnnotation=" + "]";
	}

}
