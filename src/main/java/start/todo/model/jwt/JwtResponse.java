package start.todo.model.jwt;

public class JwtResponse {

    private final String jwttoken;

    private final Long userId;

    public JwtResponse(String jwttoken, Long userId) {
        this.jwttoken = jwttoken;
        this.userId = userId;
    }

    public String getToken() {
        return this.jwttoken;
    }

    public Long getUserId() {
        return userId;
    }
}
