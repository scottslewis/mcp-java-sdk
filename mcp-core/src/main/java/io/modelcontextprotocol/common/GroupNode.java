package io.modelcontextprotocol.common;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import io.modelcontextprotocol.spec.McpSchema;

public class GroupNode extends AbstractNode {

	protected GroupNode parent;

	protected LinkedHashSet<GroupNode> childGroups = new LinkedHashSet<GroupNode>();

	protected LinkedHashSet<ToolNode> childTools = new LinkedHashSet<ToolNode>();

	protected LinkedHashSet<PromptNode> childPrompts = new LinkedHashSet<PromptNode>();

	protected LinkedHashSet<ResourceNode> childResources = new LinkedHashSet<ResourceNode>();

	public GroupNode(String name) {
		super(name);
	}

	public GroupNode getParent() {
		return parent;
	}

	public boolean isRootNode() {
		return parent == null;
	}

	public void setParent(GroupNode parent) {
		this.parent = parent;
	}

	public boolean addChildGroup(GroupNode childGroup) {
		synchronized (childGroups) {
			boolean added = childGroups.add(childGroup);
			if (added) {
				childGroup.setParent(this);
				return true;
			}
			return false;
		}
	}

	public boolean removeChildGroup(GroupNode childGroup) {
		synchronized (childGroups) {
			if (childGroups.remove(childGroup)) {
				childGroup.setParent(null);
				return true;
			}
			return false;
		}
	}

	public Set<GroupNode> getChildrenGroups() {
		return this.childGroups;
	}

	public boolean addChildTool(ToolNode childTool) {
		synchronized (childTools) {
			boolean added = childTools.add(childTool);
			if (added) {
				childTool.addParentGroup(this);
				return true;
			}
			return false;
		}
	}

	public boolean removeChildTool(ToolNode childTool) {
		synchronized (childTools) {
			boolean removed = childTools.remove(childTool);
			if (removed) {
				childTool.removeParentGroup(this);
				return true;
			}
			return false;
		}
	}

	public LinkedHashSet<ToolNode> getChildrenTools() {
		return this.childTools;
	}

	public boolean addChildPrompt(PromptNode childPrompt) {
		synchronized (childPrompts) {
			boolean added = childPrompts.add(childPrompt);
			if (added) {
				childPrompt.addParentGroup(this);
				return true;
			}
			return false;
		}
	}

	public boolean removeChildPrompt(PromptNode childPrompt) {
		synchronized (childPrompts) {
			boolean removed = childPrompts.remove(childPrompt);
			if (removed) {
				childPrompt.removeParentGroup(this);
				return true;
			}
			return false;
		}
	}

	public LinkedHashSet<ResourceNode> getChildrenResources() {
		return this.childResources;
	}

	public boolean addChildResource(ResourceNode childResource) {
		synchronized (childResources) {
			boolean added = childResources.add(childResource);
			if (added) {
				childResource.addParentGroup(this);
				return true;
			}
			return false;
		}
	}

	public boolean removeChildPrompt(ResourceNode childResource) {
		synchronized (childResources) {
			boolean removed = childResources.remove(childResource);
			if (removed) {
				childResource.removeParentGroup(this);
				return true;
			}
			return false;
		}
	}

	public LinkedHashSet<PromptNode> getChildrenPrompts() {
		return this.childPrompts;
	}

	public McpSchema.Group.Builder serialize() {
		McpSchema.Group.Builder builder = new McpSchema.Group.Builder();
		builder.name(getName());
		builder.title(getTitle());
		builder.description(getDescription());
		builder.meta(getMeta());
		GroupNode parent = getParent();
		if (parent != null) {
			builder.parent(parent.serialize().build());
		}
		return builder;
	}

	private static final Map<String, GroupNode> groupNodeCache = new HashMap<String, GroupNode>();

	public static GroupNode deserialize(McpSchema.Group group) {
		String groupName = group.name();
		GroupNode gtn = groupNodeCache.get(groupName);
		if (gtn == null) {
			gtn = new GroupNode(groupName);
			groupNodeCache.put(groupName, gtn);
		}
		gtn.setTitle(group.title());
		gtn.setDescription(group.description());
		gtn.setMeta(group.meta());
		McpSchema.Group parent = group.parent();
		if (parent != null) {
			gtn.setParent(deserialize(parent));
		}
		return gtn;
	}

	@Override
	public String toString() {
		return "GroupNode [name=" + name + ", isRoot=" + isRootNode() + ", title=" + title + ", description="
				+ description + ", meta=" + meta + ", childGroups=" + childGroups + ", childTools=" + childTools
				+ ", childPrompts=" + childPrompts + "]";
	}

}
