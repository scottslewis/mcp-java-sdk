package io.modelcontextprotocol.common;

import java.util.List;

import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpSchema.Annotations;
import io.modelcontextprotocol.spec.McpSchema.Role;

public class AnnotationsNode {

	private List<Role> audience;

	private Double priority;

	private String lastModified;

	public AnnotationsNode(List<Role> audience, Double priority, String lastModified) {
		this.audience = audience;
		this.priority = priority;
		this.lastModified = lastModified;
	}

	public McpSchema.Annotations serialize() {
		return new McpSchema.Annotations(getAudience(), getPriority(), getLastModified());
	}

	public static AnnotationsNode deserialize(Annotations annotations) {
		return new AnnotationsNode(annotations.audience(), annotations.priority(), annotations.lastModified());
	}

	public List<Role> getAudience() {
		return audience;
	}

	public void setAudience(List<Role> audience) {
		this.audience = audience;
	}

	public Double getPriority() {
		return priority;
	}

	public void setPriority(Double priority) {
		this.priority = priority;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

}
