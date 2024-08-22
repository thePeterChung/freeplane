package org.freeplane.plugin.chat;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.freeplane.core.ui.AFreeplaneAction;
import org.freeplane.features.map.NodeModel;
import org.freeplane.features.map.mindmapmode.MMapController;
import org.freeplane.features.mode.Controller;
import org.freeplane.features.text.mindmapmode.MTextController;

import java.awt.event.ActionEvent;
import java.util.List;

public class ChatOpenAi extends AFreeplaneAction {

    public ChatOpenAi() {
        super("ChatOpenAi");
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        ChatHelper chatHelper = new ChatHelper();
        List<OfField> inTheClass = chatHelper.fromTheClass(OpenAiChatModel.OpenAiChatModelBuilder.class);
        MMapController mapController = (MMapController) Controller.getCurrentModeController().getMapController();
        NodeModel root = mapController.getRootNode();
        String nodeText = "config";
        NodeModel configNode = new NodeModel(nodeText, root.getMap());
        mapController.addNewNode(configNode, root, 0);

        NodeModel found = chatHelper.findNextNodeWithCondition("config");
        if (found != null) {
            chatHelper.addAttributeToNode(found, inTheClass);
            final MTextController textController = MTextController.getController();
            textController.setDetails(found, "openai");
        }
    }
}
