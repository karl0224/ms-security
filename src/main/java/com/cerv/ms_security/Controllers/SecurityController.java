package com.cerv.ms_security.Controllers;

import com.cerv.ms_security.Models.User;
import com.cerv.ms_security.Repositories.UserRepository;
import com.cerv.ms_security.Services.EncryptionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cerv.ms_security.Services.JwtService;

import java.io.IOException;
import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping("/api/public/security")
public class SecurityController {
    @Autowired
    private UserRepository theUserRepository; //hacemos el login
    @Autowired
    private EncryptionService theEncryptionService; //el usuario que quiere autenticarso mando su contraseña, así verificamos que la contraseña que esta mandnado en el login sea igual a la encriptada
    @Autowired
    private JwtService theJwtService; //toda la información del usuario en caso de que el login sea exitoso

    @PostMapping("/login")
    public HashMap<String,Object> login(@RequestBody User theNewUser,
                                        final HttpServletResponse response)throws IOException {
        HashMap<String,Object> theResponse=new HashMap<>();
        String token="";
        User theActualUser=this.theUserRepository.getUserByEmail(theNewUser.getEmail());
        if(theActualUser!=null &&
           theActualUser.getPassword().equals(theEncryptionService.convertSHA256(theNewUser.getPassword()))){
            token=theJwtService.generateToken(theActualUser);
            theActualUser.setPassword("");
            theResponse.put("token",token);
            //theResponse.put("user",theActualUser); //de esta manera al comentarlo solo nos devolvera el token
            return theResponse;
        }else{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return  theResponse;
        }

    }

}
