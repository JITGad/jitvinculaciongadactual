
package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Utilities.ResponseValidateToken;

/**
 *
 * @author jorge
 */
public class AuthorizationController {
    
    private final UserController uc;

    public AuthorizationController() {
        uc = new UserController();
    }
    
    public ResponseValidateToken VToken(String Authorization){
        String[] clains = Methods.getDataToJwt(Authorization.split(" ")[1]);
        return uc.ValidateToken(clains[0], clains[1], clains[2]);
    }
    
}
