package io.modelcontextprotocol.common;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import io.modelcontextprotocol.util.Assert;

public class AbstractLeafNode extends AbstractNode {

	protected AbstractLeafNode(String name) {
		super(name);
	}

	protected LinkedHashSet<GroupNode> parentGroups = new LinkedHashSet<GroupNode>();

	public boolean addParentGroup(GroupNode parentGroup) {
		Assert.notNull(parentGroup, "parentGroup must not be null");
		synchronized (parentGroups) {
			if (parentGroups.add(parentGroup)) {
				return true;
			}
			return false;
		}
	}

	public boolean removeParentGroup(GroupNode parentGroup) {
		synchronized (parentGroups) {
			if (parentGroups.remove(parentGroup)) {
				return true;
			}
			return false;
		}
	}

	public LinkedHashSet<GroupNode> getParentGroups() {
		return new LinkedHashSet<GroupNode>(this.parentGroups);
	}

	protected GroupNode getTopGroupNode(GroupNode current) {
		GroupNode parent = current.getParent();
		if (parent == null) {
			return current;
		}
		else
			return getTopGroupNode(parent);
	}

	public List<GroupNode> getRoots() {
		return getParentGroups().stream().map(pgn -> getTopGroupNode(pgn)).collect(Collectors.toList());
	}

}
