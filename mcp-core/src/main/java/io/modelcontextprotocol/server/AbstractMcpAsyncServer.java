package io.modelcontextprotocol.server;

import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.modelcontextprotocol.common.AsyncToolGroup;
import io.modelcontextprotocol.server.McpServerFeatures.AsyncToolSpecification;
import io.modelcontextprotocol.spec.McpError;
import io.modelcontextprotocol.spec.McpSchema;
import io.modelcontextprotocol.spec.McpSchema.Tool;
import io.modelcontextprotocol.spec.McpSchema.ToolGroup;
import io.modelcontextprotocol.spec.McpSchema.ToolGroupName;

public abstract class AbstractMcpAsyncServer {

	private static final Logger logger = LoggerFactory.getLogger(AbstractMcpAsyncServer.class);

	private final List<AsyncToolGroup> toolGroups = Collections
			.synchronizedList(new CopyOnWriteArrayList<AsyncToolGroup>());

	Mono<Void> addToolGroup(AsyncToolGroup asyncToolGroup) {
		return Mono.defer(() -> {
			return addOrRemoveToolGroup(asyncToolGroup, true);
		});
	}

	protected Mono<Void> addOrRemoveToolGroup(AsyncToolGroup asyncToolGroup, boolean add) {
		List<AsyncToolSpecification> specifications = asyncToolGroup.getSpecifications();
		if (add) {
			// If no specifications then error
			if (specifications.size() == 0) {
				return Mono.error(McpError.builder(McpSchema.ErrorCodes.INVALID_PARAMS)
						.message("toolGroup does not contain any tools").build());
			}
		} else {
			// If given toolGroup is not in added tool groups then error
			if (!this.toolGroups.contains(asyncToolGroup)) {
				return Mono.error(McpError.builder(McpSchema.ErrorCodes.INVALID_PARAMS)
						.message("toolGroup has not been added to this server").build());
			}
		}

		ToolGroup toolGroup = asyncToolGroup.getToolGroup();

		List<AsyncToolSpecification> successfullyAdded = add ? new ArrayList<AsyncToolSpecification>() : null;

		for (AsyncToolSpecification specification : specifications) {

			AsyncToolSpecification wrappedSpecification = getSpecWithFullyQualifiedName(toolGroup, specification);

			try {
				// Block so that we are sure every tool in group actually gets added
				if (add) {
					addTool(wrappedSpecification).block();
					successfullyAdded.add(wrappedSpecification);
				} else {
					removeTool(wrappedSpecification.tool().name()).block();
				}
			} catch (McpError mcpError) {
				// remove any specifications that have been successfully added before failure
				successfullyAdded.forEach(s -> {
					try {
						removeTool(s.tool().name()).block();
					} catch (Exception e) {
						logger.error("Error removing tool=" + s.tool().name() + " from server");
					}
				});
				return Mono.error(mcpError);
			}
		}
		if (add) {
			toolGroups.add(asyncToolGroup);
			logger.debug("Added toolGroup=" + toolGroup.name().getFullyQualifiedName() + " to server");
		} else {
			toolGroups.remove(asyncToolGroup);
			logger.debug("Removed toolGroup=" + toolGroup.name().getFullyQualifiedName() + " to server");
		}
		return Mono.empty();
	}

	Mono<Void> removeToolGroup(AsyncToolGroup toolGroup) {
		return Mono.defer(() -> {
			return addOrRemoveToolGroup(toolGroup, false);
		});
	}

	private AsyncToolSpecification getSpecWithFullyQualifiedName(ToolGroup toolGroup,
			AsyncToolSpecification specification) {
		Tool tool = specification.tool();
		String fqToolGroupName = toolGroup.name().getFullyQualifiedName();
		String toolName = tool.name();
		if (!toolName.startsWith(fqToolGroupName)) {
			toolName = fqToolGroupName + ToolGroupName.NAME_DELIMITER + tool.name();
		}
		tool = Tool.builder().name(toolName).title(tool.title()).group(toolGroup).description(tool.description())
				.annotations(tool.annotations()).inputSchema(tool.inputSchema()).meta(tool.meta())
				.outputSchema(tool.outputSchema()).build();
		return AsyncToolSpecification.builder().tool(tool).callHandler(specification.callHandler()).build();
	}

	// Abstract methods for the existing API
	abstract Mono<Void> addTool(AsyncToolSpecification toolSpecification);

	// Abstract methods for the existing API
	abstract Mono<Void> removeTool(String toolName);

}
