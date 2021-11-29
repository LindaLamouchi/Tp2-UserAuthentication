package LL.demo.payload.request;



import java.util.Set;

import javax.validation.constraints.*;
 
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
   
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    

    private String roles;
  
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRole() {
        return this.roles;
      }
      
      public void setRole(String role) {
        this.roles = role;
      }

	public SignupRequest(@NotBlank @Size(min = 3, max = 20) String username,
			@NotBlank @Size(max = 50) @Email String email, @NotBlank @Size(min = 6, max = 40) String password,
			String roles) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}
      
 
}

