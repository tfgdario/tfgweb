package com.uniovi.services;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class PasswordService {

	public String generarPassword(int longitud){
         final char[] caracteres =
                {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P',
                        'Q','R','S','T','U','V','W','X', 'Y','Z',
                        'a','b','c','d','e','f','g','e','h','i','j','l','k','m',
                        'n','o','p','r','s','t','u','v','w','x','y','z',
                        '1','2','3','4','5','6','7','8','9','0',
                        '@','#','!','$','€','&','[',']'};

        String pass = "";

        Random aleatorio = new Random();

        for(int i = 0; i < longitud ; i++){
        	pass += caracteres[aleatorio.nextInt(caracteres.length)];
        }

        return pass;
    }
	
}
