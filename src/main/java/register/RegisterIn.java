package register;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterIn {
    private String email;
    private String password;
    private String name;
    private String accessToken;

    public RegisterIn(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
