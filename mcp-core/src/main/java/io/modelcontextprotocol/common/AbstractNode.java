package io.modelcontextprotocol.common;

import java.util.Map;
import java.util.Objects;

import io.modelcontextprotocol.util.Assert;

public class AbstractNode {

	protected final String name;

	protected String title;

	protected String description;

	protected Map<String, Object> meta;

	protected AbstractNode(String name) {
		Assert.notNull(name, "Group name must not be null");
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, Object> getMeta() {
		return this.meta;
	}

	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractNode other = (AbstractNode) obj;
		return Objects.equals(name, other.name);
	}

}
