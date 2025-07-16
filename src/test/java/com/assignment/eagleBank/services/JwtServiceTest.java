//package com.assignment.eagleBank.services;
//
//import com.assignment.eagleBank.TestUtils;
//import com.assignment.eagleBank.controllers.UserController;
//import com.assignment.eagleBank.entity.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtValidationException;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@ExtendWith(MockitoExtension.class)
//
//public class JwtServiceTest {
//    @Mock
//    private JwtDecoder jwtDecoder;
//
//    @Autowired
//    JwtService jwtService;
//
//    @InjectMocks
//    private UserController userController;
//
//    @BeforeEach
//    void setUp() {
//        SecurityContextHolder.clearContext();
//    }
//
//    @Test
//    void whenValidToken_thenReturnsUserInfo() {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("sub", "john.doe");
//
//        User user = TestUtils.createNewUser(TestUtils.createNewAddress());
//
//        String jwtToken = jwtService.generateToken(claims, user);
//
////        Jwt jwt = Jwt.withTokenValue("token")
////                .header("alg", "none")
////                .claims(existingClaims -> existingClaims.putAll(claims))
////                .build();
//
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, userDetailsService.loadUserByUsername(user.getEmail()));
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//
//        ResponseEntity<User> response = userController.fetchUserDetails("test user id");
//
//        assertEquals("Hello, john.doe", response.getBody());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//    @Test
//    void whenTokenHasCustomClaims_thenProcessesCorrectly() {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("sub", "john.doe");
//        claims.put("roles", Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
//        claims.put("email", "john.doe@example.com");
//
//        Jwt jwt = Jwt.withTokenValue("token")
//                .header("alg", "none")
//                .claims(existingClaims -> existingClaims.putAll(claims))
//                .build();
//
//        List authorities = ((List) jwt.getClaim("roles"))
//                .stream()
//                .map(role -> new SimpleGrantedAuthority(role))
//                .collect(Collectors.toList());
//
//        JwtAuthenticationToken authentication = new JwtAuthenticationToken(
//                jwt,
//                authorities,
//                jwt.getClaim("sub")
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        ResponseEntity response = userController.getUserInfo(jwt);
//
//        assertEquals("Hello, john.doe", response.getBody());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        assertTrue(authentication.getAuthorities().stream()
//                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
//    }
//
//    @Test
//    void whenInvalidToken_thenThrowsException() {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("sub", null);
//
//        Jwt invalidJwt = Jwt.withTokenValue("invalid_token")
//                .header("alg", "none")
//                .claims(existingClaims -> existingClaims.putAll(claims))
//                .build();
//
//        JwtAuthenticationToken authentication = new JwtAuthenticationToken(invalidJwt);
//        SecurityContextHolder.getContext()
//                .setAuthentication(authentication);
//
//        JwtValidationException exception = assertThrows(JwtValidationException.class, () -> {
//            userController.getUserInfo(invalidJwt);
//        });
//
//        assertEquals("Invalid token", exception.getMessage());
//    }
//
//}
