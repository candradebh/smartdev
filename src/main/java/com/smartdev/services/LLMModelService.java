package com.smartdev.services;

import com.smartdev.dto.LLMModelResponse;
import com.smartdev.dto.MessageRequestDTO;
import com.smartdev.entity.LLMModelEntity;
import com.smartdev.entity.MessageEntity;
import com.smartdev.entity.ProjectEntity;
import com.smartdev.repository.LLMModelRepository;
import com.smartdev.repository.MessageRepository;
import com.smartdev.repository.ProjectRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;

@Service
public class LLMModelService {

    @Value("${llm.endpoint}")
    private String llmEndpoint;

    @Autowired
    private LLMModelRepository llmModelRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void fetchAndStoreModels() {
        String url = llmEndpoint + "/api/tags";
        try {
            LLMModelResponse response = restTemplate.getForObject(url, LLMModelResponse.class);
            List<LLMModelEntity> v_listaModels = llmModelRepository.findAll();

            if (response != null && response.getModels() != null) {

                LLMModelEntity v_modelDefault = llmModelRepository.findByDefaultModelTrue();

                for (LLMModelEntity llmModelEntity : response.getModels()) {
                    try {
                        Optional<LLMModelEntity> v_llmOpt = llmModelRepository.findByName(llmModelEntity.getName());
                        if (v_llmOpt.isEmpty()) {

                            if (v_modelDefault == null) {
                                llmModelEntity.setDefaultModel(true);
                            }
                            llmModelRepository.save(llmModelEntity);
                        }


                    } catch (Exception e) {
                        //throw new RuntimeException(e);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar modelos do endpoint: " + e.getMessage());

        }
    }

    public LLMModelEntity create(LLMModelEntity entity) {
        return llmModelRepository.save(entity);
    }

    public List<MessageEntity> getHistoryChat(MessageRequestDTO messageRequest) {

        if (messageRequest.getProjectId() != null) {

            ProjectEntity project = projectRepository.findById(messageRequest.getProjectId()).orElseThrow(new Supplier<RuntimeException>() {
                @Override
                public RuntimeException get() {
                    return new RuntimeException("Projeto não encontrado");
                }
            });

            return messageRepository.findByProject(project);
        }

        return messageRepository.findByProjectIsNull();
    }

    public String processMessage(String message, String model) {


        return "";
    }

    // Método que envia a requisição HTTP para o LLM
    public String sendToLlmApi(String userMessage, Long modelId) {
        // Define a URL da API de LLM;
        String apiUrl = llmEndpoint + "/api/chat"; // Ajuste para a URL correta

        // Define o corpo da requisição
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("stream", false);

        Optional<LLMModelEntity> v_modelOpt = llmModelRepository.findById(modelId);
        if (v_modelOpt.isPresent()) {
            requestBody.put("model", v_modelOpt.get().getName());
        }

        // Cria a lista de mensagens manualmente
        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMessageMap = new HashMap<>();
        userMessageMap.put("role", "user");
        userMessageMap.put("content", userMessage);
        messages.add(userMessageMap);
        requestBody.put("messages", messages);

        // Configura os headers (caso seja necessário enviar content-type como JSON)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Cria a entidade com o corpo e os headers
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // Faz a requisição POST
        ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, Map.class);

        // Verifica se a resposta é bem-sucedida e retorna o conteúdo
        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("message")) {
                Map<String, String> messageMap = (Map<String, String>) responseBody.get("message");
                return messageMap.get("content"); // Retorna o conteúdo da mensagem

            }
        }
        return "Erro ao processar a mensagem";
    }

    public String processMessageRequest(MessageRequestDTO messageRequest) {

        Long projectId = messageRequest.getProjectId();
        Long modelId = messageRequest.getModelId();
        String message = messageRequest.getMessage();

        ProjectEntity project = null;

        // Busca o projeto
        if (projectId != null) {
            project = projectRepository.findById(projectId).orElse(null);
        }


        // Salva a mensagem do usuário no banco
        MessageEntity userMessageEntity = new MessageEntity();
        userMessageEntity.setProject(project);
        userMessageEntity.setMessage(message);
        userMessageEntity.setSender("user");
        userMessageEntity.setTimestamp(LocalDateTime.now());
        messageRepository.save(userMessageEntity);

        //tratando a pergunta se possuir um projeto associado
        String v_perguntaTratada = "";
        if (project != null) {
            v_perguntaTratada = project.getDefaultIntro() + "Minha pergunta para voce é: " + userMessageEntity.getMessage();
        } else {
            v_perguntaTratada = userMessageEntity.getMessage();
        }

        // Faz a requisição para o modelo de LLM
        String botResponse = this.sendToLlmApi(v_perguntaTratada, modelId);

        // Salva a resposta da IA
        MessageEntity botMessageEntity = new MessageEntity();
        botMessageEntity.setProject(project);
        botMessageEntity.setMessage(botResponse);
        botMessageEntity.setSender("bot");
        botMessageEntity.setTimestamp(LocalDateTime.now());
        messageRepository.save(botMessageEntity);

        return botResponse;
    }
}
