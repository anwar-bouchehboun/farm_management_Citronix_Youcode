package com.projet.citronix.utilitaire;

import java.util.HashMap;
import java.util.Map;

public class ResponseMessage {
    
    public static Map<String, String> deleteSuccess(String entityName, Long id) {
        Map<String, String> response = new HashMap<>();
        response.put("message", entityName + " avec l'ID " + id + " a été supprimé(e) avec succès");
        return response;
    }
    
    public static Map<String, String> deleteSuccess(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return response;
    }
}
