package com.smartshop.AgenticAi.agent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartshop.AgenticAi.dto.CustomerIntent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IntentService {

    private final ChatClient.Builder chatClientBuilder;
    private final ObjectMapper objectMapper;

    public CustomerIntent extract(Long customerId, String message) {
        try {
            String prompt = """
                Classify this message. Return ONLY JSON. No explanation.
                
                Message: %s
                CustomerId: %d
                
                Rules:
                "where is order" or "track" = TRACK_ORDER, requiresHuman=false
                "return" or "send back" = RETURN, requiresHuman=false
                "refund" or "money back" = REFUND, requiresHuman=false
                "damaged" or "broken" = DAMAGED, requiresHuman=true
                "wrong product" = WRONG_PRODUCT, requiresHuman=true
                "missing" = MISSING_ITEM, requiresHuman=true
                anything else = GENERAL, requiresHuman=false
                
                Extract orderId if mentioned (format ORD followed by numbers).
                
                Return exactly:
                {"intents":["TRACK_ORDER"],"primaryIntent":"TRACK_ORDER","orderId":"ORD001","customerId":%d,"language":"English","requiresHuman":false,"rawMessage":"%s","extras":{}}
                """.formatted(message, customerId, customerId, message);

            String response = chatClientBuilder.build()
                    .prompt().user(prompt).call().content();

            log.info("Gemini raw response: {}", response);

            // clean response in case Gemini adds markdown
            response = response.replaceAll("```json", "")
                    .replaceAll("```", "")
                    .trim();

            return objectMapper.readValue(response, CustomerIntent.class);

        } catch (Exception e) {
            log.error("Intent extraction failed: {}", e.getMessage());
            CustomerIntent fallback = new CustomerIntent();
            fallback.setPrimaryIntent("GENERAL");
            fallback.setIntents(List.of("GENERAL"));
            fallback.setCustomerId(customerId);
            fallback.setRawMessage(message);
            fallback.setRequiresHuman(false);
            return fallback;
        }
    }
}