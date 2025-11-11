package io.modelcontextprotocol.common;

import java.util.List;
import java.util.stream.Collectors;

import io.modelcontextprotocol.spec.McpSchema;

public class ResourceNode extends AbstractLeafNode {

	private String uri;

	private Long size;

	private String mimeType;

	private AnnotationsNode annotations;

	private String lastModified;

	public ResourceNode(String name) {
		super(name);
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public AnnotationsNode getAnnotations() {
		return annotations;
	}

	public void setAnnotations(AnnotationsNode annotations) {
		this.annotations = annotations;
	}

	public McpSchema.Resource.Builder serialize() {
		McpSchema.Resource.Builder resourceBuilder = new McpSchema.Resource.Builder();
		resourceBuilder.name(getName());
		resourceBuilder.title(getTitle());
		resourceBuilder.description(getDescription());
		resourceBuilder.uri(getUri());
		resourceBuilder.size(getSize());
		resourceBuilder.mimeType(getMimeType());
		AnnotationsNode an = getAnnotations();
		if (an != null) {
			resourceBuilder.annotations(an.serialize());
		}
		return resourceBuilder;
	}

	public static List<ResourceNode> deserialize(List<McpSchema.Resource> resources) {
		return resources.stream().map(resource -> {
			ResourceNode pn = new ResourceNode(resource.name());
			pn.setTitle(resource.title());
			pn.setDescription(resource.description());
			pn.setMeta(resource.meta());
			pn.setUri(resource.uri());
			List<McpSchema.Group> parentGroups = resource.groups();
			if (parentGroups != null) {
				parentGroups.forEach(pg -> {
					GroupNode.deserialize(pg).addChildResource(pn);
				});
			}
			return pn;
		}).collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return "ResourceNode [name=" + name + ", title=" + title + ", description=" + description + ", meta=" + meta
				+ ", uri=" + uri + ", size=" + size + ", mimeType=" + mimeType + ", annotations=" + annotations
				+ ", lastModified=" + lastModified + "]";
	}

}
