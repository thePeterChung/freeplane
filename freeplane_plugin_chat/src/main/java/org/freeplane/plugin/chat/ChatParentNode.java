/*
 *  Freeplane - mind map editor
 *  Copyright (C) 2008 Joerg Mueller, Daniel Polansky, Christian Foltin, Dimitry Polivaev
 *
 *  This file is modified by Dimitry Polivaev in 2008.
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.freeplane.plugin.chat;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import org.freeplane.core.ui.AFreeplaneAction;
import org.freeplane.features.attribute.Attribute;
import org.freeplane.features.map.IMapSelection;
import org.freeplane.features.map.NodeModel;
import org.freeplane.features.map.clipboard.MapClipboardController;
import org.freeplane.features.map.mindmapmode.clipboard.MMapClipboardController;
import org.freeplane.features.mode.Controller;
import org.freeplane.features.text.DetailModel;
import org.freeplane.features.text.mindmapmode.MTextController;

import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

class ChatParentNode extends AFreeplaneAction {
	Controller controller;
	ChatHelper chatHelper;
	ChatLanguageModel model;
	MTextController textController;
	ChatModel chatModel;
	ChatContext chatContext;
	List<ChatMessage> context;

	public ChatParentNode() {
		super("ChatParentNode");
		controller = Controller.getCurrentController();
		textController = MTextController.getController();
		chatHelper = new ChatHelper();
		chatModel = new ChatModel();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		chatContext = new ChatContext();
		context = new ArrayList<>();
		IMapSelection iMapSelection =  controller.getSelection();
		NodeModel nodeModel = iMapSelection.getSelected();
		String text = nodeModel.getText();
		NodeModel found = chatHelper.findNextNodeWithCondition("config");


		if (found != null) {
			DetailModel detail = DetailModel.getDetail(found);
			ChatModelProp prop = new ChatModelProp();
			prop.populateFromNode(found);
			if ((detail.getText().contains("openai"))) {
				model = chatModel.createModel("openai", prop);
			}
			if ((detail.getText().contains("anthropic"))) {
				model = chatModel.createModel("anthropic", prop);
			}
			if ((detail.getText().contains("ollama"))) {
				model = chatModel.createModel("ollama", prop);
			}
			UserMessage userMessage = UserMessage.from(
					TextContent.from(String.valueOf(text).replaceAll("--paste",""))
			);
			// Get attributes from parent node
            NodeModel parentNodeSelected = nodeModel.getParentNode();
			if (parentNodeSelected != null) {
				Vector <Attribute> parentNodeAttributes = chatHelper.getAttributeTableFromNode(parentNodeSelected);
				if (!parentNodeAttributes.isEmpty()) {
					context.addAll(chatContext.createContext(parentNodeAttributes));
				}
			}
			// Get attributes from node
			final NodeModel nodeSelectedMap = controller.getSelection().getSelected();
			Vector <Attribute> attributes = chatHelper.getAttributeTableFromNode(nodeSelectedMap);
			if (!attributes.isEmpty()) {
				context.addAll(chatContext.createContext(attributes));
			}
			if (context.size() >= 1) {
				UserMessage instruct = UserMessage.from(TextContent.from("Based on our previous conversation."));
				context.add(instruct);
			}
			context.add(userMessage);
			try {
				AiMessage finalResponse = model.generate(context).content();
				System.out.println(finalResponse.text());
				final NodeModel nodeSelected = controller.getSelection().getSelected();
				chatHelper.addAttributeToNode(nodeSelected, userMessage);
				chatHelper.addAttributeToNode(nodeSelected, finalResponse);
				String coreText = nodeSelected.getText();
				int pasteIndex = coreText.indexOf("--paste");
				if (pasteIndex >= 0) {
					MMapClipboardController clipboardController = (MMapClipboardController) MapClipboardController.getController();
					clipboardController.paste(new StringSelection(finalResponse.text()),nodeSelected);
				}
			} catch(Exception exception) {
				System.out.println(exception.getMessage());
				this.chatHelper.setAttributeValue(found, EChatModelProp.ERROR, exception.getMessage());
			}
			System.out.println(context);

		}

	}
}
