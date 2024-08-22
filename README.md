# LLM for freeplane
=========
## Introduction

Hello

The goal for this fork is to integrate LLMs into freeplane.

Here's how: By implementing the LangChain4j library https://github.com/langchain4j/langchain4j

## Getting Started

Under Tools -> Chat Config choose either anthropic, ollama or openai.  
Enter your api key into apiKey property. If you choose openai and use $ demo as api key you can demo chat tool which uses http://langchain4j.dev/demo/openai/v1

Insert a node to map and name it "Tell me a joke" then select Tools -> Node Single Node to get a response which is added to node as AiMessage and the prompt as UserMessage.

## Usage

1. Tools -> Node Single Node - Requests response from selected node core text
2. Tools -> Chat with Context -> Chat Parent + Node - Parent and selected node chat as added as context
3. Tools -> Chat with Context -> Chat Node - Selected node chat is added as context
4. Tools -> Chat with Context -> Chat Children + Node - Selected node and children chat is added as context

Attributes UserMessage and AiMessage are added to context.
Add `--paste` to node core text to paste response as "Plain text as node hierarchy".

### Default Settings

openai
baseUrl = https://api.openai.com/v1
modelName = gpt-3.5-turbo
temperature = 0.7
maxRetries = 3

anthropic
baseUrl = https://api.anthropic.com/v1/
version = 2023-06-01
beta = tools-2024-04-04
timeout = 60
modelName = claude-3-haiku-20240307
temperature = 0.7
maxRetries = 3

ollama
baseUrl = http://localhost:11434
modelName = llama3
timeout = 60s
maxRetries = 3

### API Properties

Set properties to config your provider below are examples

baseUrl = $ https://api.openai.com/v1/ $ https://openrouter.ai/api/v1/   (Set your providers URL so that directory is pointing to v1.)
apiKey = $ xx-xx-xx-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
organizationId = $ example.com
modelName = $ gpt-3.5-turbo $ meta-llama/llama-3.1-8b-instruct:free
temperature = $ 0.00 - 1.00
topP = $ 0.00 - 1.00
stop = $ word1 , word2      (Stop generating tokens when you specify up to 4 words. Separate words with a comma.)
maxTokens = $ 1 - x
presencePenalty = $ 0.00 - 2.00
frequencyPenalty = $ 0.00 - 2.00
logitBias = $ 2435:-100, 640:-100        (Format is int:int use a comma for multiple.)
responseFormat = $ { \"type\": \"json_schema\", \"json_schema\": {...} }        (Custom JSON model response).
seed = $ 1 - x              (Any integer to activate)
user = $ xxxxxxxx           (Unique identifier to represent end-user.)
timeout = $ P2DT3H4M6S      (Equals 2 Days 3 Hours 4 Minutes 6 Seconds)
maxRetries = $ 1 - x
proxy = $ example.com:8080
logRequests = $ true or $ false
logResponses = $ true or $ false
tokenizer = $ aZ-zZ                         (Any character to activate leave blank to estimate cost)
customHeaders = $ h1:h2, h2:h2, h3:h3       (Format is `String:String` use comma to seperate.)
listeners = $ aZ-zZ                           (Any character to activate)
error = (Returns error message if request failed)

// Specific properties for anthropic
version = $ 2023-06-01
beta = $ tools-2024-04-04
topK = 0.0 - 100.0
stopSequences = $ word1 , word2             (Stop generating tokens when you specify up to 4 words. Separate words with a comma.)

// Specific properties for ollama
repeatPenalty = $ 0.00 - 2.00
numPredict = $ 1 - x
numCtx = $ 1 - x
format = $ aZ-zZ                            (Takes a string)

### Notes
The node created from Tools -> Chat Config do not delete or rename "config" or the provider "anthropic","ollama" or "openai" otherwise response request will not work.


Freeplane
=========

[![SourceForge](https://img.shields.io/sourceforge/dt/freeplane?color=green)](https://sourceforge.net/projects/freeplane/files/stats/timeline)
[![GitHub Repo stars](https://img.shields.io/github/stars/freeplane/freeplane?color=yellow)](https://github.com/freeplane/freeplane/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/freeplane/freeplane)](https://github.com/freeplane/freeplane/network)
[![GitHub commit activity](https://img.shields.io/github/commit-activity/y/freeplane/freeplane?color=red)](https://img.shields.io/github/commit-activity/y/freeplane/freeplane?color=red)
[![GitHub last commit](https://img.shields.io/github/last-commit/freeplane/freeplane?color=orange)](https://github.com/freeplane/freeplane/commits)
[![GitHub closed pull requests](https://img.shields.io/github/issues-pr-closed/freeplane/freeplane)](https://github.com/freeplane/freeplane/pulls)
[![GitHub contributors](https://img.shields.io/github/contributors/freeplane/freeplane?color=purple)](https://github.com/freeplane/freeplane/graphs/contributors)
[![GitHub watchers](https://img.shields.io/github/watchers/freeplane/freeplane?color=yellowgreen)](https://img.shields.io/github/watchers/freeplane/freeplane?color=yellowgreen)


[Freeplane](https://www.freeplane.org) is a free and open source software application that supports thinking, sharing information, getting things done at work, in school and at home. It provides you a set of tools for mind mapping (also known as concept mapping or information mapping) and navigating the mapped information. Freeplane is also a more robust and superior alternative to Xmind, Mindmeister, and similar mind mapping software.

Freeplane is written in Java using OSGi and Java Swing. It runs on any operating system that has a current version of Java installed. It can be installed or can run from removable storage like a USB drive. 

Download and install the latest version over at [Sourceforge](https://sourceforge.net/projects/freeplane/files/). If you would like to report a bug, you can go report it over at [Issues](https://github.com/freeplane/freeplane/issues).

The documentation can be found at [![mdBook Docu](https://img.shields.io/badge/mdBook-Docu-lightblue)](https://docs.freeplane.org/). There, you will find How-To Guides, FAQs, Examples and explanations about the functions of Freeplane.

Hop on to our [Discussions](https://github.com/freeplane/freeplane/discussions) if you have any questions, ideas, or thoughts you'd like to share. Contributors are very much welcome, of course! 


Features Rundown
=====================================

![student](https://user-images.githubusercontent.com/88552647/170373856-7a636373-a783-4fa0-ba27-2ddb39d8ca3c.png)

-------------

![slides](https://user-images.githubusercontent.com/88552647/170373905-107a46ce-b8e6-4d6c-bf19-e711bfeb6a20.png)

-------------

![formatting](https://user-images.githubusercontent.com/88552647/170373875-b2885816-b900-4a2f-9ab4-3293cb148654.png)

-------------

![UI](https://user-images.githubusercontent.com/88552647/170374143-9e65d981-c7ef-456e-8c84-a43abcae3181.png)

-------------

![addons](https://user-images.githubusercontent.com/88552647/170373895-f851ddf8-4bc3-4544-a197-9b101c0d986d.png)

-------------

![command search](https://user-images.githubusercontent.com/88552647/170373890-fdb4ec75-ba95-4a71-ab6e-65f50e72897b.png)

-------------

![styling](https://user-images.githubusercontent.com/88552647/170373913-7337604c-9a08-4a73-8d7b-2d9d73981fa8.png)

-------------

![formulas](https://user-images.githubusercontent.com/88552647/170373932-247effb8-3df4-49a8-9158-192d26a752ec.png)

-------------

![discussions](https://user-images.githubusercontent.com/88552647/170373883-2a34bbeb-5bfe-4544-99bd-435295f46f8f.png)

-------------

How to Start Contributing
=====================================
We're currently looking for contributors for developing the documentation. If you can write simple step-by-step guides, translate existing text into English, transfer text from our old documentation into the new one, then we could use your help. You can start a discussion post saying you want to contribute to the documentation and the Freeplane team will respond and assist you. 

If you have other ways of contributing: developing an add-on, sharing your pre-configured mindmap, or suggestions about future development, please feel free to join us in the [Discussions](https://github.com/freeplane/freeplane/discussions)

Every contributor or team member freely decides what task they are going to work on. However, for making the best decision regarding development, it's advised that we first propose and suggest the idea to the community through a discussion post as to enable early discussion and community feedback.


Where to Download
=====================================
Download and install the latest version over at [Sourceforge](https://sourceforge.net/projects/freeplane/files/).
