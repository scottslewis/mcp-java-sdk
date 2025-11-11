package io.modelcontextprotocol.common;

import io.modelcontextprotocol.spec.McpSchema.ToolAnnotations;

public class ToolAnnotationNode {

	protected String title;

	protected Boolean readOnlyHint;

	protected Boolean destructiveHint;

	protected Boolean idempotentHint;

	protected Boolean openWorldHint;

	protected Boolean returnDirect;

	public ToolAnnotationNode() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getReadOnlyHint() {
		return readOnlyHint;
	}

	public void setReadOnlyHint(Boolean readOnlyHint) {
		this.readOnlyHint = readOnlyHint;
	}

	public Boolean getDestructiveHint() {
		return destructiveHint;
	}

	public void setDestructiveHint(Boolean destructiveHint) {
		this.destructiveHint = destructiveHint;
	}

	public Boolean getIdempotentHint() {
		return idempotentHint;
	}

	public void setIdempotentHint(Boolean idempotentHint) {
		this.idempotentHint = idempotentHint;
	}

	public Boolean getOpenWorldHint() {
		return openWorldHint;
	}

	public void setOpenWorldHint(Boolean openWorldHint) {
		this.openWorldHint = openWorldHint;
	}

	public Boolean getReturnDirect() {
		return returnDirect;
	}

	public void setReturnDirect(Boolean returnDirect) {
		this.returnDirect = returnDirect;
	}

	public ToolAnnotations serialize() {
		return new ToolAnnotations(getTitle(), getReadOnlyHint(), getDestructiveHint(), getIdempotentHint(),
				getOpenWorldHint(), getReturnDirect());
	}

	public static ToolAnnotationNode deserialize(ToolAnnotations annotations) {
		ToolAnnotationNode tn = new ToolAnnotationNode();
		tn.setTitle(annotations.title());
		tn.setReadOnlyHint(annotations.readOnlyHint());
		tn.setDestructiveHint(annotations.destructiveHint());
		tn.setOpenWorldHint(annotations.openWorldHint());
		tn.setReturnDirect(annotations.returnDirect());
		return tn;
	}

	@Override
	public String toString() {
		return "ToolAnnotationNode [title=" + title + ", readOnlyHint=" + readOnlyHint + ", destructiveHint="
				+ destructiveHint + ", idempotentHint=" + idempotentHint + ", openWorldHint=" + openWorldHint
				+ ", returnDirect=" + returnDirect + "]";
	}

}
