package io.modelcontextprotocol.common;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpSchema.PromptArgument;
import io.modelcontextprotocol.util.Assert;

public class PromptNode extends AbstractLeafNode {

	protected LinkedHashSet<PromptArgumentNode> promptArguments = new LinkedHashSet<PromptArgumentNode>();

	public PromptNode(String name) {
		super(name);
	}

	public LinkedHashSet<PromptArgumentNode> getPromptArguments() {
		return new LinkedHashSet<PromptArgumentNode>(this.promptArguments);
	}

	public boolean addPromptArgument(PromptArgumentNode promptArgument) {
		Assert.notNull(promptArgument, "promptArgument must not be null");
		synchronized (promptArguments) {
			if (promptArguments.add(promptArgument)) {
				return true;
			}
			return false;
		}
	}

	public boolean removeParentGroup(PromptArgumentNode promptArgument) {
		Assert.notNull(promptArgument, "promptArgument must not be null");
		synchronized (promptArguments) {
			if (promptArguments.remove(promptArgument)) {
				return true;
			}
			return false;
		}
	}

	public McpSchema.Prompt serialize() {
		LinkedHashSet<PromptArgumentNode> promptArgumentNodes = getPromptArguments();
		List<PromptArgument> promptArguments = null;
		if (promptArgumentNodes != null) {
			promptArguments = promptArgumentNodes.stream().map(pan -> {
				return pan.serialize();
			}).collect(Collectors.toList());
		}
		LinkedHashSet<GroupNode> groups = getParentGroups();
		List<McpSchema.Group> grps = null;
		if (groups != null) {
			grps = groups.stream().map(grp -> {
				return grp.serialize().build();
			}).collect(Collectors.toList());
		}
		return new McpSchema.Prompt(getName(), grps, getTitle(), getDescription(), promptArguments);
	}

	public static List<PromptNode> deserialize(List<McpSchema.Prompt> prompts) {
		return prompts.stream().map(prompt -> {
			PromptNode pn = new PromptNode(prompt.name());
			pn.setTitle(prompt.title());
			pn.setDescription(prompt.description());
			pn.setMeta(prompt.meta());
			List<PromptArgument> promptArgs = prompt.arguments();
			if (promptArgs != null) {
				promptArgs.forEach(pa -> {
					pn.addPromptArgument(PromptArgumentNode.deserialize(pa));
				});
			}
			List<McpSchema.Group> parentGroups = prompt.groups();
			if (parentGroups != null) {
				parentGroups.forEach(pg -> {
					GroupNode.deserialize(pg).addChildPrompt(pn);
				});
			}
			return pn;
		}).collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return "PromptNode [promptArguments=" + promptArguments + ", name=" + name + ", title=" + title
				+ ", description=" + description + ", meta=" + meta + "]";
	}

}
