package br.com.todo.api.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {
  @NotBlank(message = "Name is required")
  private String name;
  @NotBlank(message = "Email is required")
  @Email(message = "Email is invalid")
  private String email;
  @NotBlank(message = "Password is required")
  private String password;
}
