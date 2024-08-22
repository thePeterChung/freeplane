package org.freeplane.plugin.chat;

import dev.langchain4j.model.Tokenizer;
import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import org.freeplane.features.map.NodeModel;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.time.Duration;
import java.util.*;

enum EChatModelProp
{
    // This will call enum constructor with one
    // String argument
    CLASS("java.lang.Class"), BASEURL("baseUrl"), APIKEY("apiKey"), ORGANIZATIONID("organizationId"), CLIENT("client"), MODELNAME("modelName"), TEMPERATURE("temperature"), TOPP("topP"), STOP("stop"), MAXTOKENS("maxTokens"), PRESENCEPENALTY("presencePenalty"), FREQUENCYPENALTY("frequencyPenalty"), LOGITBIAS("logitBias"), RESPONSEFORMAT("responseFormat"), SEED("seed"), USER("user"), TIMEOUT("timeout"), MAXRETRIES("maxRetries"), PROXY("proxy"), LOGREQUESTS("logRequests"), LOGRESPONSES("logResponses"), TOKENIZER("tokenizer"), CUSTOMHEADERS("customHeaders"), LISTENERS("listeners"), ERROR("error"),
    VERSION("version"), BETA("beta"), TOPK("topK"), STOPSEQUENCES("stopSequences"), REPEATPENALTY("repeatPenalty"), NUMPREDICT("numPredict"), NUMCTX("numCtx"), FORMAT("format");
    // declaring private variable for getting values
    private String action;

    // getter method
    public String getAction()
    {
        return this.action;
    }

    // enum constructor - cannot be public or protected
    private EChatModelProp(String action)
    {
        this.action = action;
    }
}

public class ChatModelProp {
    String baseUrl;
    String apiKey;
    String organizationId;
    String modelName;
    Double temperature;
    Double topP;
    List<String> stop;
    Integer maxTokens;
    Double presencePenalty;
    Double frequencyPenalty;
    Map<String, Integer> logitBias;
    String responseFormat;
    Integer seed;
    String user;
    Duration timeout;
    Integer maxRetries;
    Proxy proxy;
    Boolean logRequests;
    Boolean logResponses;
    Tokenizer tokenizer;
    Map<String, String> customHeaders;
    List<ChatModelListener> listeners;

    // Specific prop for anthropic
    String version;
    String beta;
    Integer topK;
    List<String> stopSequences;

    // Specific prop for ollama
    Double repeatPenalty;
    Integer numPredict;
    Integer numCtx;
    String format;

    ChatHelper chatHelper;

    public ChatModelProp() {
        chatHelper = new ChatHelper();
    }

    public void populateFromNode(NodeModel found) {
        String baseUrlStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.BASEURL);
        if (!baseUrlStr.isEmpty()){ baseUrl = baseUrlStr; }

        String apiKeyStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.APIKEY);
        if (!apiKeyStr.isEmpty()){ apiKey = apiKeyStr; }

        String organizationIdStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.ORGANIZATIONID);
        if (!organizationIdStr.isEmpty()) { organizationId = organizationIdStr; }

        String modelNameStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.MODELNAME);
        if (!modelNameStr.isEmpty()) { modelName = modelNameStr; }

        String temperatureStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.TEMPERATURE);
        if (!temperatureStr.isEmpty()) { temperature = Double.valueOf(temperatureStr); }

        String topPStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.TOPP);
        if (!topPStr.isEmpty()) { topP = Double.valueOf(topPStr); }

        String stopStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.STOP);
        if (!stopStr.isEmpty()) {
            String[] strSplitStop = stopStr.split(",");
            stop = new ArrayList<String>(Arrays.asList(strSplitStop));
        }

        String maxTokensStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.MAXTOKENS);
        if (!maxTokensStr.isEmpty()) { maxTokens = Integer.valueOf(maxTokensStr); }

        String presencePenaltyStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.PRESENCEPENALTY);
        if (!presencePenaltyStr.isEmpty()) { presencePenalty = Double.valueOf(presencePenaltyStr); }

        String frequencyPenaltyStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.FREQUENCYPENALTY);
        if (!frequencyPenaltyStr.isEmpty()) { frequencyPenalty = Double.valueOf(frequencyPenaltyStr); }

        // logitBias format should be "2435:-100, 640:-100";
        String logitBiasStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.LOGITBIAS);
        if (!logitBiasStr.isEmpty()) {
            logitBias = new HashMap<String, Integer>();
            String logitBiasParts[] = logitBiasStr.split(",");

            for (String part : logitBiasParts) {

                String logitBiasData[] = part.split(":");
                String logitBiasDataA = logitBiasData[0].trim();
                Integer logitBiasDataB = Integer.parseInt( logitBiasData[1].trim());
                logitBias.put(logitBiasDataA, logitBiasDataB);
            }
        }

        String responseFormatStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.RESPONSEFORMAT);
        if (!responseFormatStr.isEmpty()) { responseFormat = responseFormatStr; }

        String seedStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.SEED);
        if (!seedStr.isEmpty()) { seed = Integer.valueOf(seedStr); }

        String userStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.USER);
        if (!userStr.isEmpty()) { user = userStr; }

        String timeoutStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.TIMEOUT);
        if (!timeoutStr.isEmpty()) { timeout = Duration.parse(timeoutStr); }

        String maxRetriesStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.MAXRETRIES);
        if (!maxRetriesStr.isEmpty()) { maxRetries = Integer.valueOf(maxRetriesStr); }

        String proxyStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.PROXY);
        if (!proxyStr.isEmpty()) {
            String[] strSplitProxy = proxyStr.split(":");
            // Address format should be "example.com:8080"
            SocketAddress addr = new InetSocketAddress(strSplitProxy[0], Integer.parseInt(strSplitProxy[1]));
            proxy = new Proxy(Proxy.Type.HTTP, addr);
        }

        String logRequestsStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.LOGREQUESTS);
        if (!logRequestsStr.isEmpty()) { logRequests = Boolean.parseBoolean(logRequestsStr); }

        String logResponsesStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.LOGRESPONSES);
        if (!logResponsesStr.isEmpty()) { logResponses = Boolean.parseBoolean(logResponsesStr); }

        String tokenizerStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.TOKENIZER);
        if (!tokenizerStr.isEmpty()) { tokenizer = new OpenAiTokenizer(tokenizerStr); }

        // customHeaders format should be "h1:h1, h2:h2, h3: h3";
        String customHeadersStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.CUSTOMHEADERS);
        if (!customHeadersStr.isEmpty()) {
            customHeaders = new HashMap<String, String>();
            String customHeadersParts[] = customHeadersStr.split(",");

            for (String part : customHeadersParts) {

                String customHeadersData[] = part.split(":");
                String customHeaderA = customHeadersData[0].trim();
                String customHeaderB = customHeadersData[1].trim();
                customHeaders.put(customHeaderA, customHeaderB);
            }
        }

        String listenersStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.LISTENERS);
        if  (!listenersStr.isEmpty() )  {
            ChatModelListener chatModelListener = new ChatModelListener() {

                @Override
                public void onRequest(ChatModelRequestContext requestContext) {
                    System.out.println("Request: " + requestContext.request().messages());
                }

                @Override
                public void onResponse(ChatModelResponseContext responseContext) {
                    System.out.println("Response: " + responseContext.response().aiMessage());
                }

                @Override
                public void onError(ChatModelErrorContext errorContext) {
                    errorContext.error().printStackTrace();
                }
            };
            listeners = new ArrayList<ChatModelListener>();
            listeners.add(chatModelListener);
            }

        String versionStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.VERSION);
        if (!versionStr.isEmpty()){ version = versionStr; }

        String betaStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.BETA);
        if (!betaStr.isEmpty()){ beta = betaStr; }

        String topKStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.TOPK);
        if (!topKStr.isEmpty()) { topK = Integer.valueOf(topKStr); }

        String stopSequencesStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.STOPSEQUENCES);
        if (!stopSequencesStr.isEmpty()) {
            String[] strSplitStop = stopSequencesStr.split(",");
            stopSequences = new ArrayList<String>(Arrays.asList(strSplitStop));
        }

        String repeatPenaltyStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.REPEATPENALTY);
        if (!repeatPenaltyStr.isEmpty()) { repeatPenalty = Double.valueOf(repeatPenaltyStr); }

        String numPredictStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.NUMPREDICT);
        if (!numPredictStr.isEmpty()) { numPredict = Integer.valueOf(numPredictStr); }

        String numCtxStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.NUMCTX);
        if (!numCtxStr.isEmpty()) { numCtx = Integer.valueOf(numCtxStr); }

        String formatStr = chatHelper.getAttributeValueFromNode(found, EChatModelProp.FORMAT);
        if (!formatStr.isEmpty()){ format = formatStr; }
    }
}
