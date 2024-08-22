package org.freeplane.plugin.chat;

import org.freeplane.features.mode.ModeController;

public class ChatRegistration {

	public ChatRegistration(ModeController modeController) {
		modeController.addAction(new ChatModelBuilder());
		modeController.addAction(new ChatOpenAi());
		modeController.addAction(new ChatAnthropic());
		modeController.addAction(new ChatOllama());
		modeController.addAction(new ChatWithContext());
		modeController.addAction(new ChatParentNode());
		modeController.addAction(new ChatNode());
		modeController.addAction(new ChatChildNode());
		modeController.addAction(new ChatSingleNode());
	}

}
