## Date:31-01-2026
### Prompt template
- prompt template can be given as ha givena as as a tring iput and in placeholder we can provide user given keyword
- Best practice is to create a separate file for system prompt and user prompt as template.
  - This template can be reused
  - Customizable
  - Prompt stuffing: We can provide some personalized info to the user upto 1000 char. Example: Provide a product return policy template as system template. Using that AI will give response as per the give policy

## Date:1-02-2026
### Spring AI advisor
- AI advisor is something like how we use spring AOP
- We can specify a advisor before request or after response to the AI model
- We can add multiple advisor and specify order of execution using ORDER
- 

  
## Date: 07-02-2026
### Chat option & Streaming Response
- chat option is to instruct LLM how it should react
- chat option objects are different based on the LLM, like OpenAPI, Gemini, ollama etc.
- specify 
  - model
  - temperature: range 0-2. Value will be based on task - like if you want SQL query, define a API etc.
  - maxToken: max token can be used for request-response
  - frequencyPenalty
  - presencePenalty
  - topK
  - topP
- stream response using spring webflux 
  - Flux<String> as response
  - configure mediatype text event sent i.e. produces server sent evt

## Date: 08-02-2026
### Structured output & Type safety using chat
- building a trip planner service
- chat client is used
  - in entity method we can pass our DTO and spring AI will provide the structured output
  - for list - need to use ListOutputConverter<DTO> to inform spring AI we need list output
  - map will work same as list
  - BeanOutputConverter

## Date: 28-02-2026
### Stateful Ai with chat memory
- ChatMemory interface
- ChatMemoryRepository it hat one implementation InMemoryChatMemoryRepository.
  - It implements methods something like JpaRepository
- Advisor is responsible to store the message in memory
  - MessageChatMemoryAdvisor
  - Due to advisor every request goes with previous request also, in that way LLM know about the chat memory
  - based on the CONVERSATION_ID it keeps the chatmemory context
  - we  can use different CONVERSATION_ID for different chat
 
## Date: 01-03-2026
### Stateful Ai with chat memory using database(postgres/mysql etc.)
- configure datasource in application.yml
- spring AI will create a table by its own using this datasource
- table name: SPRING_AI_CHAT_MEMORY

## Date: 07-03-2026
### RAG Fundamentals
- RAG: Retrieval Augmentation Generation
- We can use prompt template to pass info to LLM
- but in every request if we need to send tempalte that take more token
- we can  not use large info like  pdf etc.
- For this we use RAG
- to store info which called knowledge base for RAG we use Vector database
- embedding vector
- document loader store data for vector db
- qudrant db for vector db

## Date: 08-03-2026
### RAG with vector db and pdf input
- read data from pdf input
- store it into vector db as chunk
- chunk concept

## Date: 14-03-2026
### RAG with vector db and advisor
- add advisor to overcome same code
- pre and post retrieval 

## Date: 28-03-2026
### MCP fundamentals
- MCP is collection of tools
- like 
- check MCP github
  - File System MCP server
- download claude dektop
- https://modelcontextprotocol.io/docs/develop/build-server#java
- https://modelcontextprotocol.org/docs/learn/architecture
