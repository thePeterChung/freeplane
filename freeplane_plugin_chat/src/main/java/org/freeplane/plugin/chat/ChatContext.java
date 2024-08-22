package org.freeplane.plugin.chat;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import org.freeplane.features.attribute.Attribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ChatContext {
    List<ChatMessage> chatMessages;

    public List<ChatMessage> createContext(Vector <Attribute> vAttribute) {
        chatMessages = new ArrayList<ChatMessage>();
            for (int i = 0; i < vAttribute.size(); i++) {
                Attribute attribute =  vAttribute.get(i);
                String name = attribute.getName();
                String value = attribute.getValue().toString();
                UserMessage userContext = UserMessage.from(TextContent.from(value));
                if (name.equals("UserMessage") | (name.equals("AiMessage"))  ) {
                    chatMessages.add(userContext);
                }
            }
        return chatMessages;
    }
}
