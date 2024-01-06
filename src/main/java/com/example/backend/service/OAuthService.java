package com.example.backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OAuthService {
   public ResponseEntity<String> getGoogleAccessToken(String code){
       System.out.println("code:"+code);
       return ResponseEntity.status(HttpStatus.OK).body(code);
   }
}
