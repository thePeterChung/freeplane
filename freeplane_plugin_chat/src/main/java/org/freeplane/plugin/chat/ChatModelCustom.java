package org.freeplane.plugin.chat;

import dev.langchain4j.internal.Utils;
import dev.langchain4j.model.anthropic.AnthropicChatModel;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

public class ChatModelCustom {
    ChatModel model;

    public ChatModel createModel(String modelType, ChatModelProp prop) {
        if (modelType.equals("openai")) {
            model = OpenAiChatModel.builder()
                    .baseUrl(prop.baseUrl)
                    .apiKey(prop.apiKey)
                    .organizationId(prop.organizationId)
                    .projectId(prop.projectId)
                    .modelName(prop.modelName)
                    .temperature(prop.temperature)
                    .topP(prop.topP)
                    .stop(prop.stop)
                    .maxTokens(prop.maxTokens)
                    .maxCompletionTokens(prop.maxCompletionTokens)
                    .presencePenalty(prop.presencePenalty)
                    .frequencyPenalty(prop.frequencyPenalty)
                    .logitBias(prop.logitBias)
                    .responseFormat(prop.responseFormat)
                    .strictJsonSchema(prop.strictJsonSchema)
                    .seed(prop.seed)
                    .user(prop.user)
                    .strictTools(prop.strictTools)
                    .parallelToolCalls(prop.parallelToolCalls)
                    .store(prop.store)
                    .metadata(prop.metadata)
                    .serviceTier(prop.serviceTier)
                    .timeout(prop.timeout)
                    .maxRetries(prop.maxRetries)
                    .logRequests(prop.logRequests)
                    .logResponses(prop.logResponses)
                    .customHeaders(prop.customHeaders)
                    .listeners(prop.listeners)
                    .build();
        }
        if (modelType.equals("anthropic")) {
            model = AnthropicChatModel.builder()
                    .baseUrl(prop.baseUrl)
                    .apiKey(prop.apiKey)
                    .version(prop.version)
                    .beta(prop.beta)
                    .modelName(prop.modelName)
                    .temperature(prop.temperature)
                    .topP(prop.topP)
                    .topK(prop.topK)
                    .maxTokens(prop.maxTokens)
                    .stopSequences(prop.stopSequences)
                    .cacheSystemMessages(prop.cacheSystemMessages)
                    .cacheTools(prop.cacheTools)
                    .thinkingType(prop.thinkingType)
                    .thinkingBudgetTokens(prop.thinkingBudgetTokens)
                    .timeout(prop.timeout)
                    .maxRetries(prop.maxRetries)
                    .logRequests(prop.logRequests)
                    .logResponses(prop.logResponses)
                    .listeners(prop.listeners)
                    .build();
        }
        if (modelType.equals("ollama")) {
            model = OllamaChatModel.builder()
                    .baseUrl((String) Utils.getOrDefault(prop.baseUrl, "http://localhost:11434" ))
                    .modelName((String) Utils.getOrDefault(prop.modelName, "llama3"))
                    .temperature(prop.temperature)
                    .topP(prop.topP)
                    .topK(prop.topK)
                    .repeatPenalty(prop.repeatPenalty)
                    .seed(prop.seed)
                    .numPredict(prop.numPredict)
                    .numCtx(prop.numCtx)
                    .stop(prop.stop)
                    .timeout(prop.timeout)
                    .maxRetries(prop.maxRetries)
                    .customHeaders(prop.customHeaders)
                    .logRequests(prop.logRequests)
                    .logResponses(prop.logResponses)
                    .build();
        }
    return model;
    }
}
